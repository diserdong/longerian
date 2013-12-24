package cc.icefire.market.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.BusinessTextUtil;

public class UserAppListAdapter extends LocalAppListAdapter<BasicAppItem> {

	public UserAppListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_user_app, null);
			holder = new ViewHolder();
			holder.appIcon = (ImageView) convertView.findViewById(R.id.icon);
			holder.appName = (TextView) convertView.findViewById(R.id.name);
			holder.version = (TextView) convertView.findViewById(R.id.version);
			holder.size = (TextView) convertView.findViewById(R.id.size);
			holder.uninstall = (TextView) convertView.findViewById(R.id.uninstall);
			holder.uninstall.setOnClickListener(onUninstallClicked);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BasicAppItem item = (BasicAppItem) getItem(position);
		Bitmap icon = IceFireApplication.getInstance().getApkImgLoader()
				.load(item.getPkgName(), holder.appIcon);
		if (icon != null) {
			holder.appIcon.setImageBitmap(icon);
		}
		holder.appName.setText(item.getApkName());
		holder.version.setText(item.getVersionName());
		holder.size.setText(BusinessTextUtil.getSizeTxt(context, item.getSize()));
		holder.uninstall.setTag(item);
		return convertView;
	}
	
	public class ViewHolder {

		private ImageView appIcon;
		private TextView appName;
		private TextView version;
		private TextView size;
		private TextView uninstall;

	}
	
	private OnClickListener onUninstallClicked = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			BasicAppItem item = (BasicAppItem) v.getTag();
			if(item != null) {
				IceFireApplication.getInstance().getInstalledAppManager().uninstallApp(item.getPkgName());
			}
		}
	};

}
