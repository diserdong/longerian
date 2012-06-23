package so.mp3.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String replaceBlank(String source) {  
	   Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
	   Matcher m = p.matcher(source);  
	   return m.replaceAll("");   
	}  
	
	public static String replaceLine(String source) {  
		Pattern p = Pattern.compile("\r|\n");  
		Matcher m = p.matcher(source);  
		return m.replaceAll("");   
	}  
	 
}
