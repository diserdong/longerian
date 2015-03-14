package me.longerian.abcandroid.viewdrawhelper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.longerian.abcandroid.R;

/**
 * Created by huifeng.hxl on 2015/2/2.
 */
public class DragLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdraghelper);
        View view = findViewById(R.id.background);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView block = (TextView) findViewById(R.id.block1);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "click option", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("Longer", "package name " + getPackageName());
        try {
            Log.d("Longer", "package name " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,
                R.anim.slide_out_to_bottom);
    }
}
