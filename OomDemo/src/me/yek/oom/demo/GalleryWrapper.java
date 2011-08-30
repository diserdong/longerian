package me.yek.oom.demo;

import android.view.View;
import android.widget.ImageView;

public class GalleryWrapper {

	private ImageView image;
	private View base;
	
	public GalleryWrapper(View base) {
		this.base = base;
	}

	public ImageView getImage() {
		if(null == image)
			image = (ImageView) base.findViewById(R.id.image);
		return image;
	}
	
}
