package so.mp3.http.parser;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import so.mp3.http.SougouParser;
import so.mp3.http.SougouResponse;
import so.mp3.http.response.SearchResponse;
import so.mp3.type.OnlineMp3;
import so.mp3.util.StringUtils;
import android.text.TextUtils;
import android.util.Log;

public class SearchParser extends SougouParser {

	private SearchResponse resp = new SearchResponse();
	
	@Override
	public SougouResponse parse(String source) {
		if(!TextUtils.isEmpty(source)) {
			List<String> trs = getRawMp3InTr(StringUtils.replaceLine(source));
			List<OnlineMp3> mp3s = new ArrayList<OnlineMp3>();
			for(String s : trs) {
//			Log.d("longer", s);
				mp3s.add(buildMp3(s));
			}
			resp.setMp3s(mp3s);
			resp.setNetworkException(false);
		} else {
			resp.setNetworkException(true);
		}
		return resp;
	}
	
	private List<String> getRawMp3InTr(String source) {
		List<String> start = new ArrayList<String>();
		List<String> end = new ArrayList<String>();
		List<String> raw = new ArrayList<String>();
		Pattern ps = Pattern.compile("(<!--m--><tr id=\"musicmc_\\d{1,2}\">)");
		Matcher ms = ps.matcher(source);
        while(ms.find()) {
//        	Log.d("longer", ms.group(0));
        	start.add(ms.group(0));
        }
        Pattern pe = Pattern.compile("(<!--n-->)");
        Matcher me = pe.matcher(source);
        while(me.find()) {
//        	Log.d("longer", me.group(0));
//        	Log.d("longer", me.start() + "/" + me.end());
        	end.add(me.group(0));
        }
        for(int i = 0, len = start.size(); i < len; i++) {
        	int startIndex = source.indexOf(start.get(i));
        	int endIndex = source.indexOf(end.get(i), startIndex) + end.get(i).length();
        	raw.add(source.substring(startIndex, endIndex));
        }
		return raw;
	}
	
	private OnlineMp3 buildMp3(String s) {
		OnlineMp3 m = new OnlineMp3();
		m.setTitle(getTitle(s));
		m.setSinger(getSinger(s));
		m.setAlbum(getAlbum(s));
		m.setPlayerLink(getLink(s));
		m.setSize(getSize(s));
//		Log.d("longer", m.toString());
		return m;
	}

	private String getSize(String s) {
		String size = "";
		Pattern pe = Pattern.compile("(<td nowrap>(.*)<!--size:(.*)--></td>)");
	    Matcher m = pe.matcher(s);
	    while(m.find()) {
//	    	Log.d("longer", m.group(2));
	    	size = m.group(2);
	    }
		return size;
	}

	private String getLink(String s) {
		String link = "";
		Pattern pe = Pattern.compile("(window.open\\(\'(.*?)\')");
        Matcher m = pe.matcher(s);
        while(m.find()) {
//        	Log.d("longer", m.group(2));
        	link = m.group(2);
        }
		return link;
	}

	private String getAlbum(String s) {
		String album = "";
		Pattern pe = Pattern.compile("(<td class=\"singger\"><a href.*?>)");
        Matcher m = pe.matcher(s);
        while(m.find()) {
//        	Log.d("longer", m.group(0));
        	int start = m.group(0).lastIndexOf("title=\"") + "title=\"".length();
        	int end = m.group(0).indexOf("\" target=_blank");
        	album = m.group(0).substring(start, end);
        }
		return album;
	}

	private String getSinger(String s) {
		String singer = "";
		Pattern pe = Pattern.compile("(<td class=\"singger\"><a showSinger=t.*?\">)");
        Matcher m = pe.matcher(s);
        while(m.find()) {
//        	Log.d("longer", m.group(0));
        	int start = m.group(0).lastIndexOf("title=\"") + "title=\"".length();
        	int end = m.group(0).indexOf("\" target=_blank");
        	singer = m.group(0).substring(start, end);
        }
//        Log.d("longer", singer);
		return singer;
	}

	private String getTitle(String s) {
		String title = "";
		Pattern pe = Pattern.compile("(<td class=\"songname\">.*?action=\"listen\">)");
        Matcher m = pe.matcher(s);
        while(m.find()) {
//        	Log.d("longer", m.group(0));
        	int start = m.group(0).lastIndexOf("title=\"") + "title=\"".length();
        	int end = m.group(0).indexOf("\" action");
        	title = m.group(0).substring(start, end);
        }
//    	Log.d("longer", title);
		return title;
	}
	
	

}
