package me.longerian.abcandroid.graphictouch;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SimpleFingerDraw extends Activity implements OnTouchListener,
		OnClickListener {

	private Button save;
	private ImageView image;
	private Canvas canvas;
	private Paint paint;
	private float downx = 0;
	private float downy = 0;
	private float upx = 0;
	private float upy = 0;
	private Bitmap masterPiece;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graphics_and_touch);
		save = (Button) findViewById(R.id.SavePictureButton);
		save.setOnClickListener(this);
		image = (ImageView) findViewById(R.id.image);
		masterPiece = Bitmap.createBitmap(getWindowManager()
				.getDefaultDisplay().getWidth(), getWindowManager()
				.getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
		canvas = new Canvas(masterPiece);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		image.setImageBitmap(masterPiece);
		image.setOnTouchListener(this);
		
//		Uri fileUri = Uri.parse("android.resource://me.longerian.abcandroid/" + R.drawable.tencent_q);
//		Log.d("URIFILE", fileUri.toString());
//		image.setImageURI(fileUri);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downx = event.getX();
			downy = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			upx = event.getX();
			upy = event.getY();
			canvas.drawLine(downx, downy, upx, upy, paint);
			image.invalidate();
			downx = upx;
			downy = upy;
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_CANCEL:

			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == save) {
			if (masterPiece != null) {
				Uri imageFileUri = getContentResolver().insert(
						Media.EXTERNAL_CONTENT_URI, new ContentValues());
				try {
					OutputStream imageFileOS = getContentResolver()
							.openOutputStream(imageFileUri);
					masterPiece.compress(CompressFormat.JPEG, 90, imageFileOS);
					Toast t = Toast
							.makeText(this, "Saved!", Toast.LENGTH_SHORT);
					t.show();
					Log.d("SAVE", imageFileUri.toString());
				} catch (FileNotFoundException e) {
					Log.v("EXCEPTION", e.getMessage());
				}
			}
		}
	}

}
