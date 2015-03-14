package me.longerian.abcandroid.showcase;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.showcase.AnimationUtils.AnimationEndListener;
import me.longerian.abcandroid.showcase.AnimationUtils.AnimationStartListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ShowcaseView extends RelativeLayout implements OnClickListener, OnTouchListener {

	private static final float SHOWCASE_RADIUS_IN_DP = 94.0f;
	
	private float showcaseX = -1;
    private float showcaseY = -1;
    private boolean isRedundant = false; // used to controll the first showup
    private float density;
    private float showcaseRadius;
//    private float scaleMultiplier;
    private Paint mEraser;
    private Rect voidedArea;
    
    private BitmapDrawable cling;
	private int backgroudColor;
	
	public ShowcaseView(Context context) {
		this(context, null);
		
	}
	
	public ShowcaseView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ShowcaseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	private void init() {
		backgroudColor = Color.argb(128, 80, 80, 80);
		cling = (BitmapDrawable) getResources().getDrawable(R.drawable.cling);
		density = getResources().getDisplayMetrics().density;
		showcaseRadius = density * SHOWCASE_RADIUS_IN_DP;
		
		PorterDuffXfermode mBlender = new PorterDuffXfermode(android.graphics.PorterDuff.Mode.MULTIPLY);
		mEraser = new Paint();
        mEraser.setColor(0xFFFFFF);
        mEraser.setAlpha(0);
        mEraser.setXfermode(mBlender);
        mEraser.setAntiAlias(true);
        setOnTouchListener(this);
        setOnClickListener(this);
	}

	 public void hide() {
	//	        if (mEventListener != null) {
	//	            mEventListener.onShowcaseViewHide(this);
	//	        }
	    if (Build.VERSION.SDK_INT >= 11) {
	        fadeOutShowcase();
	    } else {
	        setVisibility(View.GONE);
	    }
	}
	
	private void fadeOutShowcase() {
//	    AnimationUtils.createFadeOutAnimation(this, new AnimationEndListener() {
//	        @Override
//	        public void onAnimationEnd() {
//	            setVisibility(View.GONE);
//	        }
//	    }).start();
	}
	
	public void show() {
	//	        if (mEventListener != null) {
	//	            mEventListener.onShowcaseViewShow(this);
	//	        }
	    if (Build.VERSION.SDK_INT >= 11) {
	        fadeInShowcase();
	    } else {
	        setVisibility(View.VISIBLE);
	    }
	}

	private void fadeInShowcase() {
//	    AnimationUtils.createFadeInAnimation(this, new AnimationStartListener() {
//	        @Override
//	        public void onAnimationStart() {
//	            setVisibility(View.VISIBLE);
//	        }
//	    }).start();
	}
	    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float xDelta = Math.abs(event.getRawX() - showcaseX);
        float yDelta = Math.abs(event.getRawY() - showcaseY);
        double distanceFromFocus = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        if (distanceFromFocus > showcaseRadius) {
            this.hide();
            return true;
        } else {
        	return false;
        }
	}

	@Override
	public void onClick(View v) {
		hide();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (showcaseX < 0 || showcaseY < 0 || isRedundant) {
            super.dispatchDraw(canvas);
            return;
        }
		int measuredWidth = getMeasuredWidth();
		int measuredHeight = getMeasuredHeight();
		Bitmap b = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.drawColor(backgroudColor);
        
//        Matrix mm = new Matrix();
//        mm.postScale(scaleMultiplier, scaleMultiplier, showcaseX, showcaseY);
//        c.setMatrix(mm);
        
        c.drawCircle(showcaseX, showcaseY, showcaseRadius, mEraser);
		
        makeVoidedRect();
        
        cling.setBounds(voidedArea);
        cling.draw(c);

        canvas.drawBitmap(b, 0, 0, null);
        c.setBitmap(null);
        b.recycle();
        b = null;
        
		super.dispatchDraw(canvas);
	}
	
	private boolean makeVoidedRect() {
        // This if statement saves resources by not recalculating voidedArea
        // if the X & Y coordinates haven't changed
        if (voidedArea == null) {
            int cx = (int) showcaseX, cy = (int) showcaseY;
            int dw = cling.getIntrinsicWidth();
            int dh = cling.getIntrinsicHeight();
            voidedArea = new Rect(cx - dw / 2, cy - dh / 2, cx + dw / 2, cy + dh / 2);
            return true;
        }
        return false;
    }

	public void setShowcaseView(final View view) {
        if (isRedundant || view == null) {
            isRedundant = true;
            return;
        }
        isRedundant = false;
        view.post(new Runnable() {
            @Override
            public void run() {
            	init();
            	//cooridnates that based on the whole window
//                int[] coordinates = new int[2];
//                view.getLocationInWindow(coordinates);
//                showcaseX = (float) (coordinates[0] + view.getWidth() / 2);
//                showcaseY = (float) (coordinates[1] + view.getHeight() / 2);
            	//coordinates calculated by view's position,
            	showcaseX = view.getLeft() + view.getWidth() / 2;
            	showcaseY = view.getTop() + view.getHeight() / 2;
                invalidate();
            }
        });
    }
	
	public void setShowcasePosition(float x, float y) {
        if (isRedundant) {
            return;
        }
        showcaseX = x;
        showcaseY = y;
        init();
        invalidate();
    }
	
	public static ShowcaseView insertShowcaseView(View viewToShowcase,
			Activity activity) {
		ShowcaseView sv = new ShowcaseView(activity);
		((ViewGroup) activity.findViewById(android.R.id.content)).addView(sv);
		sv.setShowcaseView(viewToShowcase);
		return sv;
	}

	
}
