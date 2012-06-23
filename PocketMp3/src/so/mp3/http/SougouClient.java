package so.mp3.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.text.TextUtils;

import so.mp3.util.NetworkUtil;

public class SougouClient {
	
	public static final String SERVER = "http://mp3.sogou.com/";
	
	public SougouResponse excute(SougouRequest request, SougouParser parser) {
		Map<String, String> apiParam = new HashMap<String, String>();
		apiParam.putAll(request.getAppParam());
        StringBuilder sb = new StringBuilder();
        for (Iterator<Map.Entry<String, String>> it = apiParam.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> e = it.next();
            sb.append("&").append(e.getKey()).append("=").append(e.getValue());
        }
        String param = TextUtils.isEmpty(sb.toString()) ? "" : sb.toString().substring(1);
        return parser.parse(NetworkUtil.getResult(SERVER + request.getUrl(), param));
	}
	
}
