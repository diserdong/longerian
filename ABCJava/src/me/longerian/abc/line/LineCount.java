package me.longerian.abc.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineCount {

	public static void main(String[] args) {
		String text = "这个表达式用于匹配美国的邮政编码。美国邮编的规则是5位数字，或者用连字号间隔的9位数字。之所以要给出这个例子是因为它能说明一个问题：使用分枝条件时，要注意各个条件的顺序。如果你把它改成的话，那么就只会匹配5位的邮编(以及9位邮编的前5位)。原因是匹配分枝条件时，将会从左到右地测试每个条件，如果满足了某个分枝的话，就不会去再管其它的条件了。 There? ?  There are 2 different width measures for a text. 123456 One is the number of pixels which has been drawn in the width, the other is the number of 'pixels' the cursor should be advanced after drawing the text. \r paint.measureText and paint.getTextWidths returns the number of pixels (in float) which the cursor should be advanced after drawing the given string. For the number of pixels painted use paint.getTextBounds as mentioned in other answer. I believe this is called the 'Advance' of the font. \r For some fonts these two measurements differ (alot),            for instance the font Black Chancery have letters which extend past the other letters (overlapping) - see the capital 'L'. Use paint.getTextBounds as mentioned in other answer to get pixels painted. \r TCP的拥塞控制方法也是基于滑动窗口协议的。它通";
		String[] linesArray = text.split("\r\n|\r|\n");
		int lineCount = linesArray.length - 1;
		System.out.println("line count = " + lineCount);

		String chinese = "中文汉字";
		System.out.println(chinese.length());

		String chineseRegEx = "([\u4e00-\u9fa5]+)";
		String englishWordRegEx = "\\b(\\w*)\\b(\\s*)";
		String nonEmptyRegEx = "(\\S*)";
		Pattern pattern = Pattern.compile(englishWordRegEx);
		Matcher matcher = pattern.matcher(text);
//		while(matcher.find()) {
//			System.out.println(matcher.group(1));
//			System.out.println(matcher.group(2));
//		}
		
//		pattern = Pattern.compile(chineseRegEx);
//		matcher = pattern.matcher(text);
//		while(matcher.find()) {
//			System.out.println(matcher.group(1));
//		}
		
		pattern = Pattern.compile(nonEmptyRegEx);
		matcher = pattern.matcher(text);
		while(matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}

}
