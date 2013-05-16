package me.longerian.abcandroid.widget;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FavoriteButton extends FrameLayout {

	private boolean hasAddFavorite;
	
	private LinearLayout notInFavorites;
	
	private TextView inFavorites;
	
	public FavoriteButton(Context context) {
		super(context);
		init(context);
	}
	
	public FavoriteButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public FavoriteButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.notInFavorites = new LinearLayout(context);
		this.notInFavorites.setGravity(Gravity.CENTER);
		this.notInFavorites.setOrientation(LinearLayout.HORIZONTAL);
		this.notInFavorites.setBackgroundResource(R.drawable.btn_add_to_favorite_selector);
		this.notInFavorites.setDuplicateParentStateEnabled(true);
		
		ImageView icFavorite = new ImageView(context);
		icFavorite.setImageResource(R.drawable.ic_favorite);
		LinearLayout.LayoutParams icFavoriteLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		icFavoriteLp.setMargins(2, 0, 2, 0);
		notInFavorites.addView(icFavorite, icFavoriteLp);
		
		ImageView spliter = new ImageView(context);
		spliter.setImageResource(R.drawable.download_divider);
		LinearLayout.LayoutParams spliterLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		spliterLp.setMargins(2, 0, 2, 0);
		notInFavorites.addView(spliter, spliterLp);
		
		TextView labelFavorite = new TextView(context);
		labelFavorite.setText("收藏");
		labelFavorite.setTextColor(Color.rgb(255, 255, 255));
		LinearLayout.LayoutParams labelFavoriteLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		labelFavoriteLp.setMargins(2, 0, 2, 0);
		notInFavorites.addView(labelFavorite, labelFavoriteLp);
		
		this.addView(notInFavorites, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		
		this.inFavorites = new TextView(context);
		this.inFavorites.setText("已收藏");
		this.inFavorites.setBackgroundResource(R.drawable.dark_bg);
		this.inFavorites.setTextColor(Color.rgb(71, 79, 85));
		this.inFavorites.setGravity(Gravity.CENTER);
		this.addView(inFavorites, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		
		
	}
	
	public void toggle(boolean inFavorites) {
		hasAddFavorite = inFavorites;
		if(inFavorites) {
			this.inFavorites.setVisibility(View.VISIBLE);
			this.notInFavorites.setVisibility(View.INVISIBLE);
			this.setClickable(false);
			this.setEnabled(false);
		} else {
			this.inFavorites.setVisibility(View.INVISIBLE);
			this.notInFavorites.setVisibility(View.VISIBLE);
			this.setClickable(true);
			this.setEnabled(true);
		}
	}
	
}
