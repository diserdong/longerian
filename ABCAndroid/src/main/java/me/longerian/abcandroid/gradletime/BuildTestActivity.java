package me.longerian.abcandroid.gradletime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import me.longerian.abcandroid.R;

/**
 * Created by longerian on 15/3/27.
 */
public class BuildTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_flavor);
    }

    public void onButton2Clicked(View view) {

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(intent);

    }

}
