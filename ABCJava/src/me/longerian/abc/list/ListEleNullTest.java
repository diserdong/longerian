package me.longerian.abc.list;

import java.util.ArrayList;
import java.util.List;

public class ListEleNullTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> strs = new ArrayList<String>();
		strs.add("hello1");
		strs.add(null);
		strs.add("hello2");
		for(String s : strs) {
			System.out.println(s);
		}
	}

}
