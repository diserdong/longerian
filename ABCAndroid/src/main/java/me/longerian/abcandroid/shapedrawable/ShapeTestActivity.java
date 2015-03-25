package me.longerian.abcandroid.shapedrawable;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.widget.TextView;

import me.longerian.abcandroid.R;

/**
 * Created by longerian on 15/3/25.
 */
public class ShapeTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_test);
        TextView test = (TextView) findViewById(R.id.shape);

        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f,5.0f, 5.0f,5.0f, 5.0f,5.0f, 5.0f,5.0f}, null, null));
        drawable.getPaint().setColor(Color.GREEN);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setAntiAlias(true);

        test.setBackgroundDrawable(drawable);

    }
}
