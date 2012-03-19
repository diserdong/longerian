package me.yek.top.demo.api.client;

import java.util.TreeMap;

import android.content.Context;

public abstract class TaobaoRequest {
	
	protected Context context;
	
	public TaobaoRequest() {
		
	}
	
	public TaobaoRequest(Context ctx) {
		context = ctx;
	}

	abstract public TreeMap<String, String> getAppParam();
	abstract public String getMethod();

}
