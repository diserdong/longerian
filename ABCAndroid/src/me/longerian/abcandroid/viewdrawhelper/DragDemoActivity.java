package me.longerian.abcandroid.viewdrawhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.longerian.abcandroid.R;

/**
 * Created by huifeng.hxl on 2015/2/3.
 */
public class DragDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_layout_demo);
        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DragLayoutActivity.class);
                startActivity(intent);
                overridePendingTransition(
                        R.anim.slide_in_from_bottom, 0);
            }
        });
    }

}
