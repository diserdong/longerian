package cc.icefire.market.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;
/**
 * 平滑gallery
 * @author yinxing
 *
 */
public class SmoothGallery extends Gallery {

	public SmoothGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAnimationDuration(500);
//		setAnimationDuration(1000);
	}
	
	private ViewPager mPager;  
	
	  
    public ViewPager getPager() {  
        return mPager;  
    }  
  
    public void setPager(ViewPager mPager) {  
        this.mPager = mPager;
    }  
	
    
    

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int keyCode;
		if(e2.getX()>e1.getX()){
			keyCode=KeyEvent.KEYCODE_DPAD_LEFT;
		}else{
			keyCode=KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if(e2.getAction() ==  MotionEvent.ACTION_UP) {
			if(distanceX>0) {
				distanceX = distanceX+15;
			} else {
				distanceX = distanceX-15;
			}
		}
		
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	
	@Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
		if (mPager != null){
			mPager.requestDisallowInterceptTouchEvent(true);
		}
        return super.dispatchTouchEvent(ev);  
    }  
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
    	if (mPager != null){
    		mPager.requestDisallowInterceptTouchEvent(true);
    	}
        return super.onInterceptTouchEvent(ev);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        if (mPager != null){
        	mPager.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);  
    }  
	
}
