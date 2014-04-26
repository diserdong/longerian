package me.longerian.abcandroid.shop;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.shop.ShopSpace.OnPinChangeListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class ShopActivity extends Activity implements OnClickListener, OnPinChangeListener {

	private ShopSpace shopSpace;
	private ImageView hiddenPin;
	private Button buttonUp;
	private Button buttonDown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop);
		shopSpace = (ShopSpace) findViewById(R.id.space);
		hiddenPin = (ImageView) findViewById(R.id.hidden_pin);
		shopSpace.setOnPinChangeListener(this);
		buttonUp = (Button) findViewById(R.id.button_up);
		buttonUp.setOnClickListener(this);
		buttonDown = (Button) findViewById(R.id.button_down);
		buttonDown.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_up:
			shopSpace.scrollUp();
			break;
		case R.id.button_down:
			shopSpace.scrollDown();
			break;
		}
	}

	@Override
	public void onPin() {
		hiddenPin.setVisibility(View.VISIBLE);
//		hiddenPin.bringToFront();
	}

	@Override
	public void onUnpin() {
		hiddenPin.setVisibility(View.INVISIBLE);
	}
	
}
