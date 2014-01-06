package me.longerian.abc.list;

import java.util.ArrayList;
import java.util.Collections;
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
		
		ArrayList<Integer> recvPacketsSeq = new ArrayList<Integer>();
		recvPacketsSeq.add(2);
		recvPacketsSeq.add(62);
		recvPacketsSeq.add(31);
		recvPacketsSeq.add(76);
		System.out.println(recvPacketsSeq.toString());
		Collections.sort(recvPacketsSeq);
		System.out.println(recvPacketsSeq.toString());
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("hongxiaolong");
		System.out.println(sb.toString());
		sb.delete(0, sb.length());
		System.out.println(sb.toString());
		
	}

}
