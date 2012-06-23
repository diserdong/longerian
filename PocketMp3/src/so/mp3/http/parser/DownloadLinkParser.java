package so.mp3.http.parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

import so.mp3.http.SougouParser;
import so.mp3.http.SougouResponse;
import so.mp3.http.response.DownloadLinkResponse;
import so.mp3.util.StringUtils;

public class DownloadLinkParser extends SougouParser {

	private DownloadLinkResponse resp = new DownloadLinkResponse();
	
	@Override
	public SougouResponse parse(String source) {
		if(!TextUtils.isEmpty(source)) {
			String link = getLink(StringUtils.replaceLine(source));
			resp.setLink(link);
			resp.setNetworkException(false);
		} else {
			resp.setNetworkException(true);
		}
		return resp;
	}
	
	private String getLink(String s) {
		String link = "";
		Pattern pe = Pattern.compile("(<div class=\"dl\"><a href=\"(.*?)\" class=\"dlbtn\" )");
        Matcher m = pe.matcher(s);
        while(m.find()) {
//        	Log.d("longer", m.group(2));
        	link = m.group(2);
        }
		return link;
	}

}
