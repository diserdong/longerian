package me.longerian.abcandroid.animation2;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Animation2Activity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ImageView circle = (ImageView) findViewById(R.id.circle);
        AnimationSet snowMov1 = new AnimationSet(true);
        RotateAnimation rotate1 = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f , Animation.RELATIVE_TO_SELF,0.5f );
        rotate1.setFillAfter(true);
        rotate1.setDuration(5000);
        rotate1.setRepeatCount(3);
        rotate1.setRepeatMode(Animation.RESTART);
        snowMov1.addAnimation(rotate1);
        TranslateAnimation trans1 =  new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.1f, Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
        trans1.setFillAfter(true);
        trans1.setDuration(5000);
        trans1.setRepeatCount(3);
        trans1.setRepeatMode(Animation.RESTART);
        snowMov1.addAnimation(trans1);
        circle.startAnimation(snowMov1);
	}
}
