package me.longerian.abcandroid.dialog;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class DialogMainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main);
        Button openDialog = (Button) findViewById(R.id.open_dialog);
        openDialog.setOnClickListener(onOpenDialog);
        
        Button openPage = (Button) findViewById(R.id.open_page);
        openPage.setOnClickListener(onOpenPage);
        
    }

    private OnClickListener onOpenDialog = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
//			Dialog dlg = new Dialog(MainActivity.this, android.R.style.Theme_Holo_Wallpaper_NoTitleBar);
//			Dialog dlg = new Dialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);
			Dialog dlg = new Dialog(DialogMainActivity.this, R.style.CustomDialog);
//			dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mm_progress_dialog, null);
			ImageView loading = (ImageView) view.findViewById(R.id.mm_progress_dialog_icon);
			Animation rotating = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.custom_loading);
			rotating.setInterpolator(new LinearInterpolator());
			rotating.setRepeatCount(Animation.INFINITE);
			rotating.setDuration(700);
			loading.startAnimation(rotating);
			dlg.getWindow().setContentView(view);
			dlg.show();
		}
	};
    
	private OnClickListener onOpenPage = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), DialogStyleActivity.class);
			startActivity(i);
		}
	};
	
    
}
