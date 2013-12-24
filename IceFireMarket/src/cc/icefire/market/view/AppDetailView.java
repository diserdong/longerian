package cc.icefire.market.view;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cc.icefire.market.BuildConfig;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.localapk.DownloadingAppManager.OnDownloadingEventListener;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.model.BasicDownloadInfo;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.util.BusinessTextUtil;
import cc.icefire.market.util.ILog;
import cc.icefire.providers.DownloadManager;
import cc.icefire.providers.DownloadManager.Request;

public class AppDetailView extends LinearLayout implements OnDownloadingEventListener {

	private BasicAppItem item;
	
	private ImageView icon;
	private TextView name;
//	private RateBar appRate;
	private TextView version;
	private TextView downloadCount;
	private TextView size;
	private TextView action;
	
	private LinearLayout screenshotWrapper;
	private TextView desp;
	
	public AppDetailView(Context context) {
		super(context);
		init(context);
	}
	
	public AppDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public AppDetailView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.widget_app_detail, this);
		setOrientation(VERTICAL);
		icon = (ImageView) findViewById(R.id.icon);
		name = (TextView) findViewById(R.id.name);
//		appRate = (RateBar) findViewById(R.id.rate);
		version = (TextView) findViewById(R.id.version);
		size = (TextView) findViewById(R.id.size);
		action = (TextView) findViewById(R.id.action);
		action.setOnClickListener(onActionClicked);
		downloadCount = (TextView) findViewById(R.id.count);
		screenshotWrapper = (LinearLayout) findViewById(R.id.screenshots_wrapper);
		desp = (TextView) findViewById(R.id.description);
	}
	
	public void onResume() {
		IceFireApplication.getInstance().getDownloadingAppManager().registerDwnlEventListener(this);
		bindAction();
	}
	
	public void onPause() {
		IceFireApplication.getInstance().getDownloadingAppManager().unregisterDwnlEventListener(this);
	}
	
	public void bindBasicAppItem(BasicAppItem item) {
		this.item = item;
		if(icon != null) {
			if (BuildConfig.DEBUG) {
				icon.setImageResource(R.drawable.ic_lastfm);
			} else {
				Bitmap bitmap = IceFireApplication.getInstance().getNetImgLoader()
						.load(item.getIconUrl(), icon);
				if (bitmap != null) {
					icon.setImageBitmap(bitmap);
				}
			}
		}
		if(name != null) {
			name.setText(item.getApkName());
		}
		if(version != null) {
			version.setText(item.getVersionName());
		}
		if(size != null) {
			size.setText(BusinessTextUtil.getSizeTxt(getContext(), item.getSize()));
		}
		if(downloadCount != null) {
			downloadCount.setText(BusinessTextUtil.getDownloadCountTxt(getContext(), item.getDownloadCount()));
		}
		if(desp != null) {
			desp.setText(item.getDesp());
		}
		if(screenshotWrapper != null) {
			for(int i = 0, size = item.getScreenshots().length; i < size; i++) {
				ImageView image = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.item_screenshot, null, false);
				if (BuildConfig.DEBUG) {
					image.setImageResource(R.drawable.ic_dribbble);
				} else {
					Bitmap bitmap = IceFireApplication.getInstance().getNetImgLoader()
							.load(item.getScreenshots()[i], image);
					if (bitmap != null) {
						image.setImageBitmap(bitmap);
					}
				}
				screenshotWrapper.addView(image);
				if(i < size - 1) {
					View space = new View(getContext());
					screenshotWrapper.addView(space, new LinearLayout.LayoutParams(12, 12));
				}
			}
		}
		bindAction();
	}
	
	private void bindAction() {
		if(action != null) {
			BasicDownloadInfo isDownloading = IceFireApplication.getInstance().getDownloadingAppManager().isDownloading(item.getDownloadUrl());
			BasicAppItem ifInstalled = IceFireApplication.getInstance().getInstalledAppManager().ifInstalled(item.getPkgName());
			if(isDownloading != null && ifInstalled == null) {
				switch (isDownloading.getStatus()) {
				case DownloadManager.STATUS_PENDING:
				case DownloadManager.STATUS_RUNNING:
				case DownloadManager.STATUS_PAUSED:
				case DownloadManager.STATUS_FAILED:
					action.setClickable(false);
					action.setText(R.string.downloading);
					action.setTag(null);
					break;
				case DownloadManager.STATUS_SUCCESSFUL:
					action.setClickable(true);
					action.setText(R.string.install);
					action.setTag(isDownloading);
					break;
				}
			} else {
				action.setClickable(true);
				if(ifInstalled != null) {
					if(item.getVersionCode() > ifInstalled.getVersionCode()) {
						action.setText(R.string.upgrade);
					} else {
						action.setText(R.string.launch);
					}
				} else {
					action.setText(R.string.download);
				}
				action.setTag(item);
			}
		}
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
				BasicAppItem ifInstalled = IceFireApplication.getInstance().getInstalledAppManager().ifInstalled(appItem.getPkgName());
				if(ifInstalled != null) {
					if(appItem.getVersionCode() > ifInstalled.getVersionCode()) {
						download(appItem);
					} else {
						ActivityUtil.launchApplication(getContext(), appItem.getPkgName());
					}
				} else {
					download(appItem);
				}
			}
			if(item instanceof BasicDownloadInfo) {
				BasicDownloadInfo downloadItem = (BasicDownloadInfo) item;
				if(downloadItem.getStatus() == DownloadManager.STATUS_SUCCESSFUL) {
					IceFireApplication.getInstance().getInstalledAppManager().installApp(downloadItem.getDestination());
				}
			}
		}
		
		private void download(BasicAppItem item) {
			if(item != null && item.getDownloadUrl() != null) {
				Uri srcUri = Uri.parse(item.getDownloadUrl());
				DownloadManager.Request request = new Request(srcUri);
				Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), item.getPkgName() + "_" + System.currentTimeMillis() + ".apk"));
				request.setDestinationUri(uri);
				request.setTitle(item.getApkName());
				request.setDescription(item.getPkgName());
				IceFireApplication.getInstance().getDownloadManager().enqueue(request);
			}
		}
		
	};

	@Override
	public void onDownloadingChange(String url) {
		bindAction();
	}
	
}
