/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.longerian.abcandroid.viewfinderview;

import java.util.Collection;
import java.util.HashSet;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {
	
	private static final String TAG = "ViewfinderView";

  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 100L;
  private static final int OPAQUE = 0xFF;

  private final Paint paint;
  private Bitmap resultBitmap;
  private final int maskColor;
  private final int resultColor;
  private final int frameColor;
  private final int laserColor;
  private final int resultPointColor;
  private final int blueColor;
  private final int whiteColor;
  private int scannerAlpha;
//  private Collection<ResultPoint> possibleResultPoints;
//  private Collection<ResultPoint> lastPossibleResultPoints;
  
  private String tip1;
  private String tip2;

  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
    super(context, attrs);

    // Initialize these once for performance rather than calling them every time in onDraw().
    paint = new Paint();
    Resources resources = getResources();
    maskColor = resources.getColor(R.color.viewfinder_mask);
    resultColor = resources.getColor(R.color.result_view);
    frameColor = resources.getColor(R.color.viewfinder_frame);
    laserColor = resources.getColor(R.color.viewfinder_laser);
    resultPointColor = resources.getColor(R.color.possible_result_points);
    blueColor = resources.getColor(R.color.stand_blue);
    whiteColor = resources.getColor(R.color.color_ffcccccc);
    
    tip1 = resources.getString(R.string.tip_dim_code_scanning_1);
    tip2 = resources.getString(R.string.tip_dim_code_scanning_2);
    
    scannerAlpha = 0;
//    possibleResultPoints = new HashSet<ResultPoint>(5);
  }

  @Override
  public void onDraw(Canvas canvas) {
//    Rect frame = CameraManager.get().getFramingRect();
    Rect frame = new Rect(40, 120, 280, 360);
    if (frame == null) {
      return;
    }
    Log.d(TAG, "0");
    int width = canvas.getWidth();
    int height = canvas.getHeight();
    Log.d(TAG, "1");

    // Draw the exterior (i.e. outside the framing rect) darkened
    paint.setColor(resultBitmap != null ? resultColor : maskColor);
    Log.d(TAG, "1.1");
    canvas.drawRect(0, 0, width, frame.top, paint);
    Log.d(TAG, "1.2");
    canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
    Log.d(TAG, "1.3");
    canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
    Log.d(TAG, "1.4");
    canvas.drawRect(0, frame.bottom + 1, width, height, paint);
    Log.d(TAG, "2");

    if (resultBitmap != null) {
    	Log.d(TAG, "resultBitmap != null");
      // Draw the opaque result bitmap over the scanning rectangle
      paint.setAlpha(OPAQUE);
      canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
    } else {
    	Log.d(TAG, "3");
      // Draw a two pixel solid black border inside the framing rect
      paint.setColor(blueColor);
//      canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
//      canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
//      canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
//      canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);
      
      // draw four corners
      canvas.drawRect(frame.left - 10, frame.top - 10, frame.left + 40, frame.top, paint);
      canvas.drawRect(frame.left - 10, frame.top, frame.left, frame.top + 40, paint);
      
      canvas.drawRect(frame.right - 40, frame.top - 10, frame.right + 10, frame.top, paint);
      canvas.drawRect(frame.right, frame.top, frame.right + 10, frame.top + 40, paint);
      
      canvas.drawRect(frame.left - 10, frame.bottom, frame.left + 40, frame.bottom + 10, paint);
      canvas.drawRect(frame.left - 10, frame.bottom - 40, frame.left, frame.bottom, paint);
      
      canvas.drawRect(frame.right - 40, frame.bottom, frame.right + 10, frame.bottom + 10, paint);
      canvas.drawRect(frame.right, frame.bottom - 40, frame.right + 10, frame.bottom, paint);
      Log.d(TAG, "4");
   // Draw tips below framing rect
//      float density = CameraManager.get().getScreenDensity();
      float density = 1.0f;
      float fontSizeInSp = 18.0f;
      float scaledFontSizeInPx = fontSizeInSp * density + 0.5f;
      paint.setColor(whiteColor);
      paint.setTextSize(scaledFontSizeInPx);
      paint.setAntiAlias(true);
      Log.d(TAG, "5");
      float textW = paint.measureText(tip1);
      Log.d(TAG, "5.1");
      int w1 = (int)(( width - textW ) / 2);
      Log.d(TAG, "5.2");
      int h1 = frame.bottom + 60;
      Log.d(TAG, "5.3");
      canvas.drawText(tip1, w1, h1, paint);
      Log.d(TAG, "5.4");
      
      float textW1 = paint.measureText(tip2);
      Log.d(TAG, "5.5");
      int w2 = (int)(( width - textW1 ) / 2);
      Log.d(TAG, "5.6");
      int h2 = (int) (frame.bottom + 60 + scaledFontSizeInPx+ 10);
      Log.d(TAG, "5.7");
      canvas.drawText(tip2, w2, h2, paint);

      Log.d(TAG, "6");
      // Draw a red "laser scanner" line through the middle to show decoding is active
      paint.setColor(laserColor);
      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
      int middle = frame.height() / 2 + frame.top;
      canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
      Log.d(TAG, "7");
//      Collection<ResultPoint> currentPossible = possibleResultPoints;
//      Collection<ResultPoint> currentLast = lastPossibleResultPoints;
//      if (currentPossible.isEmpty()) {
//        lastPossibleResultPoints = null;
//      } else {
//        possibleResultPoints = new HashSet<ResultPoint>(5);
//        lastPossibleResultPoints = currentPossible;
//        paint.setAlpha(OPAQUE);
//        paint.setColor(resultPointColor);
//        for (ResultPoint point : currentPossible) {
//          canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
//        }
//      }
//      if (currentLast != null) {
//        paint.setAlpha(OPAQUE / 2);
//        paint.setColor(resultPointColor);
//        for (ResultPoint point : currentLast) {
//          canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
//        }
//      }

      // Request another update at the animation interval, but only repaint the laser line,
      // not the entire viewfinder mask.
      postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }
  }

  public void drawViewfinder() {
    resultBitmap = null;
    invalidate();
  }

  /**
   * Draw a bitmap with the result points highlighted instead of the live scanning display.
   *
   * @param barcode An image of the decoded barcode.
   */
  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

//  public void addPossibleResultPoint(ResultPoint point) {
//    possibleResultPoints.add(point);
//  }

}
