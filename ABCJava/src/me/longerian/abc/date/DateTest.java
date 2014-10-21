package me.longerian.abc.date;

import com.sun.jndi.toolkit.url.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by huifeng.hxl on 2014/7/31.
 */
public class DateTest {
    public static void main(String[] args) throws ParseException, MalformedURLException {
        long now = 3600 * 1000;
        Calendar independentCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        independentCal.setTimeInMillis(now);
        int year = independentCal.get(Calendar.YEAR);
        System.out.println("year = " + year);

        int month = independentCal.get(Calendar.MONTH);
        System.out.println("month = " + month);

        int date = independentCal.get(Calendar.DATE);
        System.out.println("date = " + date);

        int hour = independentCal.get(Calendar.HOUR_OF_DAY);
        System.out.println("hour = " + hour);

        int minute = independentCal.get(Calendar.MINUTE);
        System.out.println("minute = " + minute);

        int second = independentCal.get(Calendar.SECOND);
        System.out.println("second = " + second);

        // create a calendar
//        Calendar cal = Calendar.getInstance();

        // get the time in milliseconds
//        System.out.println("Current time is :" + cal.getTime());

        // set time to 5000 ms after january 1 1970
//        cal.setTimeInMillis(5000);

        // print the new time
//        System.out.println("After setting Time:  " + cal.getTime());


        String countto = "2014-08-01 19:40:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dateFormat.applyPattern("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        String format = dateFormat.format(new Date(36000));
        System.out.println("format " + format);

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        format = dateFormat.format(new Date(36000));
        System.out.println("format " + format);

        String url = "internal:url=tmall://mobile.tmall.com/page/itemDetail?itemId=123456&acm=1234,4896,156&scm=48778,8944,30256";

    }
}
