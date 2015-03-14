package me.longerian.abcandroid.animation;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AnimationActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        final ImageView circle = (ImageView) findViewById(R.id.circle);
        Button switcher = (Button) findViewById(R.id.switcher);
//        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_progress_anim);
//        circle.startAnimation(rotation);
        layout.addView(switcher);
        switcher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				TranslateAnimation slide = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.1f, Animation.RELATIVE_TO_PARENT, 0.3f, 
//						Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
				TranslateAnimation slide = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 100, 
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
				slide.setDuration(5000);
				slide.setRepeatCount(1);
				circle.startAnimation(slide);
				
//				AnimationSet snowMov1 = new AnimationSet(true);
//		        RotateAnimation rotate1 = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f , Animation.RELATIVE_TO_SELF,0.5f );
//		        rotate1.setFillAfter(true);
//		        rotate1.setDuration(5000);
//		        rotate1.setRepeatCount(3);
//		        rotate1.setRepeatMode(Animation.RESTART);
//		        snowMov1.addAnimation(rotate1);
//		        TranslateAnimation trans1 =  new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.1f, Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
//		        trans1.setFillAfter(true);
//		        trans1.setDuration(5000);
//		        trans1.setRepeatCount(3);
//		        trans1.setRepeatMode(Animation.RESTART);
//		        snowMov1.addAnimation(trans1);
//		        circle.startAnimation(snowMov1);
			}
		});
	}
}
