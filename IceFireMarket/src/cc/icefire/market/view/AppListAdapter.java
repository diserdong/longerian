package cc.icefire.market.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.icefire.market.BuildConfig;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.api.request.AppListRequest;
import cc.icefire.market.api.response.AppListResponse;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.model.BasicDownloadInfo;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.util.BusinessTextUtil;
import cc.icefire.market.util.ILog;
import cc.icefire.providers.DownloadManager;
import cc.icefire.providers.DownloadManager.Request;
import cc.icefire.providers.downloads.Downloads;
import crow.api.ApiCallback;
import crow.api.ApiException;

public class AppListAdapter extends PaginationListAdapter<BasicAppItem> {

	private AppListType type;
	private AppOrGame appOrGame;
	private int categoryId;
	private String query;

	public AppListAdapter(Context context, AppListType type, AppOrGame appOrGame) {
		super(context);
		this.type = type;
		this.appOrGame = appOrGame;
	}

	public AppListAdapter(Context context, AppListType type,
			AppOrGame appOrGame, int categoryId) {
		super(context);
		this.type = type;
		this.appOrGame = appOrGame;
		this.categoryId = categoryId;
	}

	public AppListAdapter(Context context, AppListType type,
			AppOrGame appOrGame, String query) {
		super(context);
		this.type = type;
		this.appOrGame = appOrGame;
		this.query = query;
	}
	
	@Override
	protected void onNextPageRequested(int page) {
		switch (type) {
		case SEARCH:
			requestSearchedApp(page, type, appOrGame, query);
			break;
		case CATEGORY:
			requestCategoryApp(page, type, appOrGame, categoryId);
			break;
		default:
			requestCommonApp(page, type, appOrGame);
			break;
		}
	}

	@Override
	public View getAmazingView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_app, null);
			holder = new ViewHolder();
			holder.appIcon = (ImageView) convertView.findViewById(R.id.icon);
			holder.appName = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.size = (TextView) convertView.findViewById(R.id.size);
			holder.action = (TextView) convertView.findViewById(R.id.action);
			holder.action.setOnClickListener(onActionClicked);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BasicAppItem item = (BasicAppItem) getItem(position);
		if (BuildConfig.DEBUG) {
			switch (type) {
			case SEARCH:
				holder.appIcon.setImageResource(R.drawable.ic_sky_map);
				break;
			case CATEGORY:
				holder.appIcon.setImageResource(R.drawable.ic_foursquare);
				break;
			case POPULAR:
				holder.appIcon.setImageResource(R.drawable.ic_android);
				break;
			case SELECTED:
				holder.appIcon.setImageResource(R.drawable.ic_lovedsgn);
				break;
			case NEW_RELEASES:
				holder.appIcon.setImageResource(R.drawable.ic_evernote);
				break;
			case TOP_CHARTS:
				holder.appIcon.setImageResource(R.drawable.ic_score);
				break;
			case RECOMMENDEDED:
				holder.appIcon.setImageResource(R.drawable.ic_bump);
				break;
			}
		} else {
			Bitmap icon = IceFireApplication.sharedInstance().getNetImgLoader()
					.load(item.getIconUrl(), holder.appIcon);
			if (icon != null) {
				holder.appIcon.setImageBitmap(icon);
			}
		}
		holder.appName.setText(item.getApkName());
		holder.count.setText(BusinessTextUtil.getDownloadCountTxt(context, item.getDownloadCount()));
		holder.size.setText(BusinessTextUtil.getSizeTxt(context, item.getSize()));
		BasicDownloadInfo isDownloading = IceFireApplication.sharedInstance().getDownloadingAppManager().isDownloading(item.getDownloadUrl());
		if(isDownloading != null) {
			switch (isDownloading.getStatus()) {
			case DownloadManager.STATUS_PENDING:
			case DownloadManager.STATUS_RUNNING:
			case DownloadManager.STATUS_PAUSED:
			case DownloadManager.STATUS_FAILED:
				holder.action.setClickable(false);
				holder.action.setText(R.string.downloading);
				holder.action.setTag(null);
				break;
			case DownloadManager.STATUS_SUCCESSFUL:
				holder.action.setClickable(true);
				holder.action.setText(R.string.install);
				holder.action.setTag(isDownloading);
				break;
			}
		} else {
			holder.action.setClickable(true);
			BasicAppItem ifInstalled = IceFireApplication.sharedInstance().getInstalledAppManager().ifInstalled(item.getPkgName());
			if(ifInstalled != null) {
				if(item.getVersionCode() > ifInstalled.getVersionCode()) {
					holder.action.setText(R.string.upgrade);
				} else {
					holder.action.setText(R.string.launch);
				}
			} else {
				holder.action.setText(R.string.download);
			}
			holder.action.setTag(item);
		}
		return convertView;
	}
	
	private OnClickListener onActionClicked = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Object item = v.getTag();
			if(item == null) {
				return ;
			}
			if(item instanceof BasicAppItem) {
				BasicAppItem appItem = (BasicAppItem) item;
				BasicAppItem ifInstalled = IceFireApplication.sharedInstance().getInstalledAppManager().ifInstalled(appItem.getPkgName());
				if(ifInstalled != null) {
					if(appItem.getVersionCode() > ifInstalled.getVersionCode()) {
						download(appItem);
					} else {
						ActivityUtil.launchApplication(context, appItem.getPkgName());
					}
				} else {
					download(appItem);
				}
			}
			if(item instanceof BasicDownloadInfo) {
				BasicDownloadInfo downloadItem = (BasicDownloadInfo) item;
				if(downloadItem.getStatus() == DownloadManager.STATUS_SUCCESSFUL) {
					IceFireApplication.sharedInstance().getInstalledAppManager().installApp(downloadItem.getDestination());
				}
			}
		}
		
		private void download(BasicAppItem item) {
			if(item != null && item.getDownloadUrl() != null) {
				Uri srcUri = Uri.parse(item.getDownloadUrl());
				DownloadManager.Request request = new Request(srcUri);
				Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), item.getPkgName() + "_" + System.currentTimeMillis() + ".apk"));
				request.setDestinationUri(uri);
				ILog.d(TAG, "uri " + uri.toString());
				request.setTitle(item.getApkName());
				request.setDescription(item.getPkgName());
				IceFireApplication.sharedInstance().getDownloadManager().enqueue(request);
			}
		}
		
	};
	
	public class ViewHolder {

		private ImageView appIcon;
		private TextView appName;
		private TextView count;
		private TextView size;
		private TextView action;

	}

	public void requestCommonApp(int page, AppListType type, AppOrGame appOrGame) {
		AppListRequest request = new AppListRequest(type);
		request.setPageIndex(page);
		request.setAppOrGame(appOrGame);
		doRequest(page, request);
	}

	public void requestSearchedApp(int page, AppListType type,
			AppOrGame appOrGame, String query) {
		AppListRequest request = new AppListRequest(type);
		request.setPageIndex(page);
		if (appOrGame != AppOrGame.ANY) {
			appOrGame = AppOrGame.ANY;
		}
		request.setAppOrGame(appOrGame);
		request.setQuery(query);
		doRequest(page, request);
	}

	public void requestCategoryApp(int page, AppListType type,
			AppOrGame appOrGame, int categoryId) {
		AppListRequest request = new AppListRequest(type);
		request.setPageIndex(page);
		request.setAppOrGame(appOrGame);
		request.setCategoryId(categoryId);
		doRequest(page, request);
	}

	private void doRequest(int page, AppListRequest request) {
		if (page == 1) {
			onRequestFirstPage();
		}
		IceFireApplication.sharedInstance().getHttpEngine()
				.request(request, new AppListCallback());
	}

	private class AppListCallback implements ApiCallback<AppListResponse> {

		@Override
		public void onSuccess(AppListResponse response) {
			onRecvDataSuccess();
			addItems(response.getAppList());
			nextPage();
			if(BuildConfig.DEBUG) {
				if (getCount() > 20) {
					notifyNoMorePages();
				} else {
					notifyMayHaveMorePages();
				}
			} else {
				if(response.getAppList().isEmpty()) {
					notifyNoMorePages();
				} else {
					notifyMayHaveMorePages();
				}
			}
		}

		@Override
		public void onFail(AppListResponse response) {
			onRecvDataFail();
			Toast.makeText(context, R.string.hint_loading_data_failed,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onException(ApiException e) {
			e.printStackTrace();
			onRecvDataFail();
			Toast.makeText(context, R.string.hint_network_error,
					Toast.LENGTH_SHORT).show();
		}

	}

}
