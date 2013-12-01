package cc.icefire.market.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cc.icefire.market.BuildConfig;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.BusinessTextUtil;
import cc.icefire.market.util.ILog;

public class AppDetailView extends LinearLayout {

	private BasicAppItem item;
	
	private ImageView icon;
	private TextView name;
//	private RateBar appRate;
	private TextView version;
	private TextView downloadCount;
	private TextView size;
	
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
		downloadCount = (TextView) findViewById(R.id.count);
		screenshotWrapper = (LinearLayout) findViewById(R.id.screenshots_wrapper);
		desp = (TextView) findViewById(R.id.description);
	}
	
	public void bindBasicAppItem(BasicAppItem item) {
		this.item = item;
		if(icon != null) {
			if (BuildConfig.DEBUG) {
				icon.setImageResource(R.drawable.ic_lastfm);
			} else {
				Bitmap bitmap = IceFireApplication.sharedInstance().getNetImgLoader()
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
					Bitmap bitmap = IceFireApplication.sharedInstance().getNetImgLoader()
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
	}
	
}
