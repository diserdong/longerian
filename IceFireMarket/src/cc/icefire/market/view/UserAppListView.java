package cc.icefire.market.view;

import cc.icefire.market.IceFireApplication;
import cc.icefire.market.localres.InstalledAppManager.OnAppInstallOrUninstallListener;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.ILog;
import android.content.Context;
import android.util.AttributeSet;

/**
 * 加载本地用户安装的应用列表
 */
public class UserAppListView extends LocalListView implements OnAppInstallOrUninstallListener {

	private UserAppListAdapter adapter;
	
	public UserAppListView(Context context) {
		super(context);
		init(context);
	}
	
	public UserAppListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public UserAppListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
	}
	
	public void onCreate() {
		IceFireApplication.sharedInstance().getInstalledAppManager().registerPkgEventListener(this);
	}
	
	public void onDestroy() {
		IceFireApplication.sharedInstance().getInstalledAppManager().unregisterPkgEventListener(this);
	}
	
	public void loadApp() {
		if(adapter == null) {
			adapter = new UserAppListAdapter(getContext());
			adapter.addItems(IceFireApplication.sharedInstance().getInstalledAppManager().getUserAppList());
			setAdapter(adapter);
		}
	}

	@Override
	public void onAppInstalled(BasicAppItem item) {
		if(adapter != null && item != null) {
			adapter.addItem(item);
		}
	}

	@Override
	public void onAppUninstalled(BasicAppItem item) {
		if(adapter != null && item != null) {
			adapter.removeItem(item);
		}
	}
	
}
