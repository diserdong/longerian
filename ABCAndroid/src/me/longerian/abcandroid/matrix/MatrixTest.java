package me.longerian.abcandroid.matrix;

import java.io.FileNotFoundException;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MatrixTest extends Activity implements OnClickListener {

	ImageView chosenImageView;
	ImageView alteredImageView;
	Button choosePicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matrix);
		chosenImageView = (ImageView) this.findViewById(R.id.ChosenImageView);
		alteredImageView = (ImageView) this.findViewById(R.id.AlteredImageView);
		choosePicture = (Button) this.findViewById(R.id.ChoosePictureButton);
		choosePicture.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(choosePictureIntent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			Uri imageFileUri = intent.getData();
			Display currentDisplay = getWindowManager().getDefaultDisplay();
			int dw = currentDisplay.getWidth();
			int dh = currentDisplay.getHeight() / 2 - 100;
			try {
				// Load up the image's dimensions not the image itself
				BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
				bmpFactoryOptions.inJustDecodeBounds = true;
				Bitmap bmp = BitmapFactory
						.decodeStream(
								getContentResolver().openInputStream(
										imageFileUri), null, bmpFactoryOptions);
				int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
						/ (float) dh);
				int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
						/ (float) dw);
				if (heightRatio > 1 && widthRatio > 1) {
					if (heightRatio > widthRatio) {
						bmpFactoryOptions.inSampleSize = heightRatio;
					} else {
						bmpFactoryOptions.inSampleSize = widthRatio;
					}
				}
				bmpFactoryOptions.inJustDecodeBounds = false;
				bmp = BitmapFactory
						.decodeStream(
								getContentResolver().openInputStream(
										imageFileUri), null, bmpFactoryOptions);
				chosenImageView.setImageBitmap(bmp);

				Bitmap alteredBitmap = Bitmap.createBitmap(bmp.getWidth(),
						bmp.getHeight(), bmp.getConfig());
				Canvas canvas = new Canvas(alteredBitmap);
				Paint paint = new Paint();
				// canvas.drawBitmap(bmp, 0, 0, paint);
				// Matrix matrix = new Matrix();
				// matrix.setValues(new float[] { 1, .5f, 0, 0, 1, 0, 0, 0, 1
				// });

				// matrix.setRotate(15);

				// matrix.setTranslate(10.0f, 20.0f);
				// matrix.setScale(0.5f, 0.5f);

				// matrix.setScale(1.5f, 1);
				// matrix.postRotate(15,bmp.getWidth()/2,bmp.getHeight()/2);

				// mirror
				// matrix.setScale(-1, 1);
				// matrix.postTranslate(bmp.getWidth(),0);

				// flip
				// matrix.setScale(1, -1);
				// matrix.postTranslate(0, bmp.getHeight());

				// canvas.drawBitmap(bmp, matrix, paint);

				//
				// Matrix matrix = new Matrix();
				// matrix.setRotate(15, bmp.getWidth() / 2, bmp.getHeight() /
				// 2);
				// alteredBitmap = Bitmap.createBitmap(bmp, 0, 0,
				// bmp.getWidth(), bmp.getHeight(), matrix, false);

				ColorMatrix cm = new ColorMatrix();
				//alter contrast and brightness
//				float contrast = 2;
//				float brightness = -125;
//				cm.set(new float[] { 
//						contrast, 0, 0, 0, brightness,
//						0, contrast, 0, 0, brightness, 
//						0, 0, contrast, 0, brightness, 
//						0, 0, 0, contrast, 0 });
				
				//chang saturation
				cm.setSaturation(0.5f);
				paint.setColorFilter(new ColorMatrixColorFilter(cm));
				Matrix matrix = new Matrix();
				canvas.drawBitmap(bmp, matrix, paint);

				alteredImageView.setImageBitmap(alteredBitmap);
			} catch (FileNotFoundException e) {
				Log.v("ERROR", e.toString());
			}
		}
	}
}
