package so.mp3.http;

import java.util.Map;

import android.content.Context;

public abstract class SougouRequest {
	
	protected Context context;
	
	public SougouRequest() {
		
	}
	
	public SougouRequest(Context ctx) {
		context = ctx;
	}

	abstract public Map<String, String> getAppParam();
	abstract public String getUrl();

}
