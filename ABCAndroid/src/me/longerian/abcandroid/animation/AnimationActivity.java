package me.longerian.abcandroid.animation;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ImageView circle = (ImageView) findViewById(R.id.circle);
        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_progress_anim);
        circle.startAnimation(rotation);
	}
}
