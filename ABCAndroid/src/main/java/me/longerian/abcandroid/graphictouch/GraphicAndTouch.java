package me.longerian.abcandroid.graphictouch;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;

public class GraphicAndTouch extends Activity {

	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphics_and_touch);
		image = (ImageView) findViewById(R.id.image);
		Bitmap bitmap = Bitmap.createBitmap(getWindowManager()
				.getDefaultDisplay().getWidth(), getWindowManager()
				.getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);
		canvas.drawPoint(100.0f, 100.0f, paint);
		canvas.drawRect(100, 100, 200, 200, paint);
		canvas.drawOval(new RectF(300.0f, 300.0f, 400.0f, 350.0f), paint);
		
		Path p = new Path(); 
		p.moveTo(20, 20); 
		p.lineTo(100, 200); 
		p.lineTo(200, 100); 
		p.lineTo(240, 155); 
		p.lineTo(250, 175); 
		p.lineTo(20, 20); 
		canvas.drawPath(p, paint); 
		
		paint.setStrokeWidth(2.0f);
		paint.setTextSize(40);
		Typeface serif_italic = Typeface.create(Typeface.SERIF, Typeface.ITALIC); 
		paint.setTypeface(serif_italic); 
		
		p = new Path(); 
		p.moveTo(320, 320); 
		p.lineTo(400, 450); 
		p.lineTo(300, 520); 
		canvas.drawTextOnPath("Hello text on path hahah haha ", p, 0, 0, paint); 
		image.setImageBitmap(bitmap);
	}

}
