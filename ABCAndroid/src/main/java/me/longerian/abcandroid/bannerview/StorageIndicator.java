package me.longerian.abcandroid.bannerview;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

public class StorageIndicator extends ProgressBar {
	
	private boolean alert = false;
	
	public StorageIndicator(Context context) {
		super(context);
	}

	public StorageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StorageIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public synchronized void setProgress(int progress) {
		super.setProgress(progress);
		alert = progress > 90;
		// the setProgress super will not change the details of the progress bar
		// anymore so we need to force an update to redraw the progress bar
		invalidate();
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
	  // update the size of the progress bar and overlay
	  updateProgressBar();
	  // paint the changes to the canvas
	  super.onDraw(canvas);
	}
	 
	private float getScale(int progress) {
	  float scale = getMax() > 0 ? (float) progress / (float) getMax() : 0;
	  return scale;
	}
	 
	/**
	 * Instead of using clipping regions to uncover the progress bar as the
	 * progress increases we increase the drawable regions for the progress bar
	 * and pattern overlay. Doing this gives us greater control and allows us to
	 * show the rounded cap on the progress bar.
	 */
	private void updateProgressBar() {
	  Drawable progressDrawable = getProgressDrawable();
	  if (progressDrawable != null && progressDrawable instanceof LayerDrawable) {
	    LayerDrawable d = (LayerDrawable) progressDrawable;
	    final float scale = getScale(getProgress());
	    Drawable alertProgressBar = d.findDrawableByLayerId(R.id.alert_progress);
	    Drawable normalProgressBar = d.findDrawableByLayerId(R.id.progress);
	    final int width = d.getBounds().right - d.getBounds().left;
	    if(alert) {
	    	// get the progress bar and update it's size
	    	if (alertProgressBar != null) {
//	    		alertProgressBar.setVisible(true, true);
//	    		normalProgressBar.setVisible(false, false);
	    		Rect progressBarBounds = alertProgressBar.getBounds();
	    		progressBarBounds.right = progressBarBounds.left + (int) (width * scale + 0.5f);
	    		alertProgressBar.setBounds(progressBarBounds);
	    		normalProgressBar.setBounds(0, 0, 0, 0);
	    	}
	    } else {
	    	// get the progress bar and update it's size
	    	if (normalProgressBar != null) {
//	    		normalProgressBar.setVisible(true, true);
//	    		alertProgressBar.setVisible(false, false);
	    		Rect progressBarBounds = normalProgressBar.getBounds();
	    		progressBarBounds.right = progressBarBounds.left + (int) (width * scale + 0.5f);
	    		normalProgressBar.setBounds(progressBarBounds);
	    		alertProgressBar.setBounds(0, 0, 0, 0);
	    	}
	    }
	    /**
	    // get the pattern overlay
	    Drawable patternOverlay = d.findDrawableByLayerId(android.R.id.pattern);
	     
	    if (patternOverlay != null) {
	      if (progressBar != null) {
	        // we want our pattern overlay to sit inside the bounds of our progress bar
	        Rect patternOverlayBounds = progressBar.copyBounds();
	        final int left = patternOverlayBounds.left;
	        final int right = patternOverlayBounds.right;
	         
	        patternOverlayBounds.left = (left + 1 > right) ? left : left + 1;
	        patternOverlayBounds.right = (right > 0) ? right - 1 : right;
	        patternOverlay.setBounds(patternOverlayBounds);
	      } else {
	        // we don't have a progress bar so just treat this like the progress bar
	        Rect patternOverlayBounds = patternOverlay.getBounds();
	        patternOverlayBounds.right = patternOverlayBounds.left + (int) (width * scale + 0.5f);
	        patternOverlay.setBounds(patternOverlayBounds);
	      }
	    }
	    **/
	  }
	}

}
