package me.longerian.abc.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateFormat d3 = DateFormat.getTimeInstance(); 
	    String str3 = d3.format(new Date());
	    System.out.println(str3);
	    
	    String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ").format(Calendar.getInstance().getTime());
	    System.out.println(timeStamp + "log");
	    
	    timeStamp = new SimpleDateFormat("yyyyMMdd ").format(Calendar.getInstance().getTime());
	    System.out.println(timeStamp + "log");
	    
	}

}
