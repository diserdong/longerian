package cc.icefire.market.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.api.request.AppListRequest;
import cc.icefire.market.api.response.AppListResponse;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.BasicAppItem;
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
	
	public AppListAdapter(Context context, AppListType type, AppOrGame appOrGame, int categoryId) {
		super(context);
		this.type = type;
		this.appOrGame = appOrGame;
		this.categoryId = categoryId;
	}
	
	public AppListAdapter(Context context, AppListType type, AppOrGame appOrGame, String query) {
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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BasicAppItem item = (BasicAppItem) getItem(position);
		Bitmap icon = IceFireApplication.sharedInstance().getImgLoader()
				.load(item.getIconUrl(), holder.appIcon);
		if (icon != null) {
			holder.appIcon.setImageBitmap(icon);
		}
		holder.appName.setText(item.getApkName());
		holder.count.setText(item.getDownloadCount() + "time");
		holder.size.setText(item.getSize() + "B");
		return convertView;
	}

	public class ViewHolder {

		private ImageView appIcon;
		private TextView appName;
		private TextView count;
		private TextView size;

	}

	public void requestCommonApp(int page, AppListType type, AppOrGame appOrGame) {
		AppListRequest request = new AppListRequest(type);
		request.setPageIndex(page);
		request.setAppOrGame(appOrGame);
		doRequest(page, request);
	}
	
	public void requestSearchedApp(int page, AppListType type, AppOrGame appOrGame, String query) {
		AppListRequest request = new AppListRequest(type);
		request.setPageIndex(page);
		if(appOrGame != AppOrGame.ANY) {
			appOrGame = AppOrGame.ANY;
		}
		request.setAppOrGame(appOrGame);
		request.setQuery(query);
		doRequest(page, request);
	}
	
	public void requestCategoryApp(int page, AppListType type, AppOrGame appOrGame, int categoryId) {
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
			if (getCount() > 20) {
				notifyNoMorePages();
			} else {
				notifyMayHaveMorePages();
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
