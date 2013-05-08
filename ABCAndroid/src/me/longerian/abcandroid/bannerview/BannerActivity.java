package me.longerian.abcandroid.bannerview;

import java.util.ArrayList;
import java.util.List;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BannerActivity extends FragmentActivity {

	private BannerView2 mViewPager;
	private PagerAdapter mAdapter;
	
	private Context cxt;  
    private AwesomePagerAdapter awesomeAdapter;  
      
    private LayoutInflater mInflater;  
    private List<View> mListViews;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_banner);
		setContentView(R.layout.activity_banner_2);
		mListViews = new ArrayList<View>();  
		ImageView iv1 = new ImageView(getApplicationContext());
		iv1.setImageResource(R.drawable.system_file_manager);
		ImageView iv2 = new ImageView(getApplicationContext());
		iv2.setImageResource(R.drawable.system_installer);
		ImageView iv3 = new ImageView(getApplicationContext());
		iv3.setImageResource(R.drawable.tencent_q);
		
		mListViews.add(iv1);  
		mListViews.add(iv2);  
		mListViews.add(iv3);  
		mViewPager = (BannerView2) findViewById(R.id.banner);
		mAdapter = new AwesomePagerAdapter();
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(1);
		
	}
	
	private class AwesomePagerAdapter extends PagerAdapter{  
		  
        
        @Override  
        public int getCount() {  
            return mListViews.size();  
        }  
  
        /** 
         * 从指定的position创建page 
         * 
         * @param container ViewPager容器 
         * @param position The page position to be instantiated. 
         * @return 返回指定position的page，这里不需要是一个view，也可以是其他的视图容器. 
         */  
        @Override  
        public Object instantiateItem(View collection, int position) {  
            ((ViewPager) collection).addView(mListViews.get(position),0);  
            return mListViews.get(position);  
        }  
  
        /** 
         * <span style="font-family:'Droid Sans';">从指定的position销毁page</span> 
         *<span style="font-family:'Droid Sans';">参数同上</span> 
         */  
        @Override  
        public void destroyItem(View collection, int position, Object view) {  
            ((ViewPager) collection).removeView(mListViews.get(position));  
        }  
  
          
          
        @Override  
        public boolean isViewFromObject(View view, Object object) {  
            return view==(object);  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {}  
          
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {}  
  
        @Override  
        public Parcelable saveState() {  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {}  
          
    }  
}
