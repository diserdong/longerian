package me.longerian.abcandroid.droplist;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class QQListPopupWindow {

	private Context mContext;
	private PopupWindow mPopup;
	private ListAdapter mAdapter;
	private ListView mDropdownList;
	private FrameLayout mSideBar;
	private View mDropdownAnchorView;
	private int mDropDownHorizontalOffset = 0;
    private int mDropDownVerticalOffset = 0;
    private int mDropdownListBackground = -1;
	
	private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
	
	public QQListPopupWindow(Context context) {
		this(context, null);
	}
	
	public QQListPopupWindow(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public QQListPopupWindow(Context context, AttributeSet attrs, int defStyle) {
		mContext  = context;
		mPopup = new PopupWindow(context, attrs, defStyle);
		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
	}
	
	public void setAdapter(ListAdapter adapter) {
		mAdapter = adapter;
		if(mDropdownList != null) {
			mDropdownList.setAdapter(mAdapter);
		}
	}
	
	public void setAnimationStyle(int animationStyle) {
        mPopup.setAnimationStyle(animationStyle);
    }
	
	public int getAnimationStyle() {
        return mPopup.getAnimationStyle();
    }
	
	public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
        mItemClickListener = clickListener;
    }
	
	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
        mItemSelectedListener = selectedListener;
    }
	
	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mPopup.setOnDismissListener(listener);
    }
	
	public void dismiss() {
        mPopup.dismiss();
        mPopup.setContentView(null);
        mDropdownList = null;
    }
	
	public boolean isShowing() {
        return mPopup.isShowing();
    }
	
	public Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return mDropdownList.getSelectedItem();
    }
	
	public int getSelectedItemPosition() {
        if (!isShowing()) {
            return ListView.INVALID_POSITION;
        }
        return mDropdownList.getSelectedItemPosition();
    }
	
	public long getSelectedItemId() {
        if (!isShowing()) {
            return ListView.INVALID_ROW_ID;
        }
        return mDropdownList.getSelectedItemId();
    }

    public View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return mDropdownList.getSelectedView();
    }
    
    public ListView getListView() {
        return mDropdownList;
    }
    
    public View getAnchorView() {
        return mDropdownAnchorView;
    }

    public void setAnchorView(View anchor) {
        mDropdownAnchorView = anchor;
    }
    
    public int getHorizontalOffset() {
        return mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int offset) {
        mDropDownHorizontalOffset = offset;
    }

    public int getVerticalOffset() {
        return mDropDownVerticalOffset;
    }

    public void setVerticalOffset(int offset) {
        mDropDownVerticalOffset = offset;
    }
    
    public int getDropdownListBackground() {
		return mDropdownListBackground;
	}

	public void setDropdownListBackground(int dropdownListBackground) {
		this.mDropdownListBackground = dropdownListBackground;
	}

	public void show() {
    	if(getAnchorView() == null) {
    		throw new NullPointerException("anchor view must be set first");
    	}
        buildDropDown();
        if (!mPopup.isShowing()) {
        	mPopup.setBackgroundDrawable(new BitmapDrawable());
        	mPopup.setWidth(getAnchorView().getWidth());
        	mPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopup.setWindowLayoutMode(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopup.setClippingEnabled(false);
            mPopup.setOutsideTouchable(true);
            mPopup.setFocusable(true);
            mPopup.showAsDropDown(getAnchorView(),
                    mDropDownHorizontalOffset, mDropDownVerticalOffset);
            mDropdownList.setSelection(ListView.INVALID_POSITION);
        }
    }
    
    private void buildDropDown() {
        if (mDropdownList == null && mContext != null) {
            RelativeLayout container = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.qqaccount_dropdown_list, null);
            mSideBar = (FrameLayout) container.findViewById(R.id.bar);
            mDropdownList = (ListView) container.findViewById(R.id.list);
            if(mSideBar != null && mDropdownList != null && mAdapter != null) {
            	if(mAdapter.getCount() > 3) {
            		mSideBar.setVisibility(View.VISIBLE);
            		mDropdownList.setPadding(0, 0, 48, 0);
            	} else {
            		mSideBar.setVisibility(View.GONE);
            		mDropdownList.setPadding(0, 0, 0, 0);
            	}
            	if(mDropdownListBackground != -1) {
            		mDropdownList.setBackgroundResource(mDropdownListBackground);
            	}
            	mDropdownList.setAdapter(mAdapter);
            	mDropdownList.setOnItemClickListener(mItemClickListener);
            	mDropdownList.setFocusable(true);
            	mDropdownList.setFocusableInTouchMode(true);
            	if (mItemSelectedListener != null) {
            		mDropdownList.setOnItemSelectedListener(mItemSelectedListener);
            	}
            	setContainerHeight(container, mDropdownList);
            	mPopup.setContentView(container);
            }
        }
    }
    
	private void setContainerHeight(RelativeLayout container, ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return ;
		}
		int count = listAdapter.getCount();
		if(count <= 3) {
			return ;
		}
		int totalHeight = 0;
		for (int i = 0; i < count; i++) {
		    View listItem = listAdapter.getView(i, null, listView);
		    listItem.measure(0, 0);
		    totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams parmas= container.getLayoutParams();
		parmas.height = (totalHeight + (listView.getDividerHeight() * (count - 1))) / count * 3;
		container.setLayoutParams(parmas);
	}
	
}
