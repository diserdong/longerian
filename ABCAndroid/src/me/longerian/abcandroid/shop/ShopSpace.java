package me.longerian.abcandroid.shop;

import java.util.Arrays;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class ShopSpace extends RelativeLayout {

	private String[] txts = new String[] {"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu"};
	
	private OnPinChangeListener listener;
	
	private ImageView banner;
	private ImageView pin;
	private ListView list;
	
	private Scroller scroller;
	private int currentX;
	private int currentY;
	private int dx;
	private int dy;
	
	public ShopSpace(Context context) {
		super(context);
		init(context);
	}
	public ShopSpace(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public ShopSpace(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		scroller = new Scroller(context);
		dx = 0;
		dy = 0;
		currentX = 0;
		currentY = 0;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		banner = (ImageView) findViewById(R.id.banner);
		pin = (ImageView) findViewById(R.id.pin);
		list = (ListView) findViewById(R.id.list);
		if(list != null) {
			list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, txts));
		}
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		Log.d(VIEW_LOG_TAG, "in computeScroll");
		if(scroller.computeScrollOffset()) {
			// Get current x and y positions
		     int currX = scroller.getCurrX();
		     int currY = scroller.getCurrY();
		     Log.d(VIEW_LOG_TAG, "in computeScroll currX = " + currX + " currY = " + currY);
		     scrollTo(currX, currY);
		     postInvalidate();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		Log.d(VIEW_LOG_TAG, "in onScrollChanged l = " + l + " t = " + t + " oldl = " + oldl + " oldt = " + oldt);
		currentX = l;
		currentY = t;
		dx += l - oldl;
		dy += t - oldt;
		Log.d(VIEW_LOG_TAG, "in onScrollChanged currentX = " + currentX + " currentY = " + currentY + " dx = " + dx + " dy = " + dy);
		Log.d(VIEW_LOG_TAG, "pin.height = " + pin.getHeight());
		int[] location = new int[2];
		pin.getLocationInWindow(location);
		Log.d(VIEW_LOG_TAG, "pin.WindowLocation = " + Arrays.toString(location));
		pin.getLocationOnScreen(location);
		Log.d(VIEW_LOG_TAG, "pin.ScreenLocation = " + Arrays.toString(location));
		Log.d(VIEW_LOG_TAG, "pin.height = " + Arrays.toString(location));
		Log.d(VIEW_LOG_TAG, "pin.top = " + pin.getTop());
		Log.d(VIEW_LOG_TAG, "pin.scrollY = " + pin.getScrollY());
		Log.d(VIEW_LOG_TAG, "this.top = " + getTop());
		Log.d(VIEW_LOG_TAG, "this.scrollY = " + getScrollY());
		if(listener != null) {
			if(location[1] <= 0) {
				listener.onPin();
			} else {
				listener.onUnpin();
			}
		}
	}
	
	public void setOnPinChangeListener(OnPinChangeListener l) {
		this.listener = l;
	}
	
	public void scrollUp() {
	     scroller.forceFinished(true);
	     // Start scrolling by providing a starting point and
	     // the distance to travel
	     scroller.startScroll(currentX, currentY, 0, 100);
	     // Invalidate to request a redraw
	     invalidate();
	 }
	 
	public void scrollDown() {
		scroller.forceFinished(true);
		// Start scrolling by providing a starting point and
		// the distance to travel
		scroller.startScroll(currentX, currentY, 0, -100);
		// Invalidate to request a redraw
		invalidate();
	}
	
	public interface OnPinChangeListener {
		
		public void onPin();
		public void onUnpin();
		
	}
	
}
