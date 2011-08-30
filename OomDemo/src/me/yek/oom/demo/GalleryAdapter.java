package me.yek.oom.demo;

import me.yek.oom.demo.util.ImageLoaderWithCache;
import me.yek.oom.demo.util.ImageLoaderWithScale;
import me.yek.oom.demo.util.ImageLoaderWithRecyle;
import me.yek.oom.demo.util.Loadable;
import me.yek.oom.demo.util.SimpleImageLoader;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private String[] mResPath;
	private int mContentLayout;
	private Loadable mImageLoader;
	private View mParent;
	
	public GalleryAdapter(Context context, int contentLayout,
			String[] resPath, View parent) {
		super(context, contentLayout, resPath);
		this.mContext = context;
		this.mContentLayout = contentLayout;
		this.mResPath = resPath;
		//要使用不同的ImageLoader，在此处手动修改吧。
		this.mImageLoader = new SimpleImageLoader();
//		this.mImageLoader = new ImageLoaderWithCache();
//		this.mImageLoader = new ImageLoaderWithScale();
//		this.mImageLoader = new ImageLoaderWithRecyle();
		this.mParent = parent;
	}

	@Override
	public int getCount() {
		return mResPath.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GalleryWrapper wrapper;
		if(null == convertView) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mContentLayout, null);
			wrapper = new GalleryWrapper(convertView);
			convertView.setTag(wrapper);
		} else {
			wrapper = (GalleryWrapper) convertView.getTag();
		}
		wrapper.getImage().setImageDrawable(mImageLoader.loadDrawableImage(mResPath[position]));
		Log.d("OomDemo", "max: " + ((float) Runtime.getRuntime().maxMemory()) / 1024 / 1024 + "MB");
		Log.d("OomDemo", "total: " + ((float) Runtime.getRuntime().totalMemory()) / 1024 / 1024 + "MB");
		Log.d("OomDemo", "available: " + ((float) Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB");
		Log.d("OomDemo", "displaying " + position + "th photo");
		return convertView;
	}
	
	public void releaseDrawable(AdapterView view) {
		MyGallery gallery = (MyGallery) view;
		int start = gallery.getFirstVisiblePosition() - 1;  
        int end = gallery.getLastVisiblePosition() + 1;  
        for(int i = 0; i < start; i++) {
            mImageLoader.releaseImage(mResPath[i]);
        }  
        for(int i = end + 1; i < mResPath.length; i++) {
        	mImageLoader.releaseImage(mResPath[i]);
        }  
	}
	
}
