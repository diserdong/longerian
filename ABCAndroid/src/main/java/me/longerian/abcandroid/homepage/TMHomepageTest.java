package me.longerian.abcandroid.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.build.BuildTestActivity;
import me.longerian.abcandroid.build.SecondActivity;
import me.longerian.abcandroid.viewdrawhelper.DragDemoActivity;

/**
 * Created by huifeng.hxl on 2015/3/16.
 */
public class TMHomepageTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_test);

        TextView textView = (TextView) findViewById(R.id.block1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent[] intents = new Intent[3];
                Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);
                Intent intent2 = new Intent(getApplicationContext(), DragDemoActivity.class);
                Intent intent3 = new Intent(getApplicationContext(), BuildTestActivity.class);
                intents[0] = intent1;
                intents[1] = intent2;
                intents[2] = intent3;
                startActivities(intents);
            }
        });


    }
}
