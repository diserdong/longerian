package me.longerian.pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

public class PeopleActivity extends ListActivity implements OnScrollListener {
	
	private final class RemoveWindow implements Runnable {
        public void run() {
            removeWindow();
        }
    }
	
	private RemoveWindow mRemoveWindow = new RemoveWindow();
    Handler mHandler = new Handler();
    private WindowManager mWindowManager;
    private TextView mDialogText;
    private boolean mShowing;
    private boolean mReady;
    private char mPrevLetter = Character.MIN_VALUE;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        List<People> items = new ArrayList<People>();
		People p0 = new People("苏芮");
		People p1 = new People("露西");
		People p2 = new People("XIAH Junsu");
		People p3 = new People("曹轩宾");
		People p4 = new People("Miss D.D");
		People p5 = new People("何韵诗");
		People p6 = new People("Orange Range");
		People p7 = new People("小夜 Dancer");
		People p8 = new People("游心");
		People p9 = new People("冷秋千");
		People p10 = new People("正小守");
		People p11 = new People("kctan");
		People p12 = new People("绯弹的亚里亚");
		People p13 = new People("Satsuki");
		People p14 = new People("binry");
		People p15 = new People("天天开心");
		People p16 = new People("siyipeng");
		People p17 = new People("琳华晓白");
		People p18 = new People("MusicLover33");
		People p19 = new People("lain");
		People p20 = new People("假面军团");
		items.add(p0);
		items.add(p1);
		items.add(p2);
		items.add(p3);
		items.add(p4);
		items.add(p5);
		items.add(p6);
		items.add(p7);
		items.add(p8);
		items.add(p9);
		items.add(p10);
		items.add(p11);
		items.add(p12);
		items.add(p13);
		items.add(p14);
		items.add(p15);
		items.add(p16);
		items.add(p17);
		items.add(p18);
		items.add(p19);
		items.add(p20);
		Collections.sort(items);
		getListView().setAdapter(new PeopleAdapter(getApplicationContext(), items));
		SideBar sb = (SideBar) findViewById(R.id.side_bar);
		sb.bindListView(getListView());
		setupIndicator();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mReady = true;
    }

    
    @Override
    protected void onPause() {
        super.onPause();
        removeWindow();
        mReady = false;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mDialogText);
        mReady = false;
    }
    
	private void removeWindow() {
        if (mShowing) {
            mShowing = false;
            mDialogText.setVisibility(View.INVISIBLE);
        }
  	}
    
    private void setupIndicator() {
    	mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		getListView().setOnScrollListener(this);
		LayoutInflater inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        mDialogText = (TextView) inflate.inflate(R.layout.list_position, null);
        mDialogText.setVisibility(View.INVISIBLE);
        
        mHandler.post(new Runnable() {

            public void run() {
                mReady = true;
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                mWindowManager.addView(mDialogText, lp);
            }});
    }
    
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		 if (mReady) {
	            char firstLetter = ((People) getListView().getItemAtPosition(firstVisibleItem)).getNamePinyin().charAt(0);
	            if (!mShowing && firstLetter != mPrevLetter) {
	                mShowing = true;
	                mDialogText.setVisibility(View.VISIBLE);
	            }
	            mDialogText.setText(((Character)firstLetter).toString().toUpperCase());
	            mHandler.removeCallbacks(mRemoveWindow);
	            mHandler.postDelayed(mRemoveWindow, 2000);
	            mPrevLetter = firstLetter;
	        }
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

}