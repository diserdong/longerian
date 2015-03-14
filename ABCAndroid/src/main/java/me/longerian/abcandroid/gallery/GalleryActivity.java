package me.longerian.abcandroid.gallery;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_app_detail_layout);
        Gallery screenshots = (Gallery) findViewById(R.id.screenshots);
        screenshots.setAdapter(new ShotAdapter(getApplicationContext(), R.layout.market_app_detail_item));
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        
        MarginLayoutParams mlp = (MarginLayoutParams) screenshots.getLayoutParams();
        mlp.setMargins(-(metrics.widthPixels/2), 
                       mlp.topMargin, 
                       mlp.rightMargin, 
                       mlp.bottomMargin
        );
	}
	
	private class ShotAdapter extends ArrayAdapter {

		private int[] apps = new int[] {
				R.drawable.movies,
				R.drawable.music,
				R.drawable.preferences_system,
				R.drawable.system_file_manager,
				R.drawable.system_installer,
				R.drawable.tencent_q
		};
		
		public ShotAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return apps.length;
		}

		@Override
		public Object getItem(int position) {
			return apps[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.market_app_detail_item, parent, false);
			ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
			icon.setImageResource(apps[position]);
//			ImageView imageView = new ImageView(getApplicationContext());
//			LinearLayout.LayoutParams vp = 
//			    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
//			                    LayoutParams.WRAP_CONTENT);
//			imageView.setLayoutParams(vp);        
//			imageView.setImageResource(apps[position]);        
			return convertView;
		}
		
	}
	
}
