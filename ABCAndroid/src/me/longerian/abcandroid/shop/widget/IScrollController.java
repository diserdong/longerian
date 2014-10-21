package me.longerian.abcandroid.shop.widget;

public interface IScrollController {

	public boolean canScrollup();
	
	public boolean canScrollDown();
	
	public void scrollBy(int dy);
	
}
