package cc.icefire.market.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabView extends LinearLayout {

	private ImageView icon;

	public TabView(Context context) {
		super(context);
		init(context);
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		icon = new ImageView(context);
		icon.setBackgroundColor(Color.TRANSPARENT);
		setGravity(Gravity.CENTER);
		addView(icon);
	}
	
	public void setIcon(int defaultDrawable, int selectedDrawable) {
		StateListDrawable listDrawable = new StateListDrawable();
		listDrawable.addState(SELECTED_STATE_SET, this.getResources()
				.getDrawable(selectedDrawable));
		listDrawable.addState(ENABLED_STATE_SET, this.getResources()
				.getDrawable(defaultDrawable));
		icon.setImageDrawable(listDrawable);
	}

}
