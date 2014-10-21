package me.longerian.abcandroid.textlayout;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TextLayout extends Activity implements OnClickListener {

	private TextView text;
	private Button cal;
	private static final String TEXT = "There are two different width measures for a text. One is the number of pixels which has been drawn in the width, the other is the number of pixels the cursor should be advanced after drawing the text.paint.measureText and paint.getTextWidths returns the number of pixels (in float) which the cursor should be advanced after drawing the given string. For the number of pixels painted use paint.getTextBounds as mentioned in other answer. I believe this is called the Advance of the font.For some fonts these two measurements differ (alot), for instance the font Black Chancery have letters which extend past the other letters (overlapping) - see the capital L. Use paint.getTextBounds as mentioned in other answer to get pixels painted.拥塞是由于路由器超载而引起的严重延迟现象，是通信子网能力不足的表现。一旦发生拥塞，路由器便丢弃数据包，并导致发送方重传被丢弃的报文，而大量的重传报文又会进一步加剧拥塞，这种恶性循环有可能导致整个因特网无法工作。而简单地采用确认重传技术并不能解决传输层的所有问题，TCP还必须提供适当机制以进行拥塞控制。TCP的拥塞控制方法也是基于滑动窗口协议的。它通过限制发送方注入报文的速率而达到拥塞控制的目的。具体地说，TCP是通过控制发送窗口的大小来对拥塞进行响应。而决定发送窗口大小的因素有两个：第一个因素是接收方所通告的窗口大小；第二个因素是发送方的拥塞窗口限制，又叫拥塞窗口。发送窗口的大小是取二者之中的较小者。";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_layout);
		text = (TextView) findViewById(R.id.hello);
		cal = (Button) findViewById(R.id.cal);
		text.setText(TEXT);
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		cal.setOnClickListener(this);
		
		TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(15);
		textPaint.setTextScaleX(getResources().getDisplayMetrics().scaledDensity);
		float interlineSpace = textPaint.getFontMetrics(null);
		StaticLayout sl = new StaticLayout(TEXT, textPaint, 768, Layout.Alignment.ALIGN_NORMAL, 1.0f, interlineSpace, true);
		Log.d("Longer", "static layout height = " + sl.getHeight());
		Log.d("Longer", "static layout linecount = " + sl.getLineCount());
		Log.d("Longer", "application context = " + getApplication());
		Log.d("Longer", "activity context = " + this);
		Log.d("Longer", "textview context = " + text.getContext());
		Log.d("Longer", "button context = " + cal.getContext());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cal:
			Log.d("Longer", "height = " + text.getHeight());
			break;

		default:
			break;
		}
	}
	
}
