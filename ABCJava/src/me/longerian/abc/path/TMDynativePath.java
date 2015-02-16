package me.longerian.abc.path;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaoyang.kb
 *
 */
public class TMDynativePath implements ITMDynativePath {

	private static final String TAG = "TMDynativePath";
	
	private String pathStr;
	
	private int currentIndex;
	
	private List<String> pathArray;
	
	private enum ParseState{
		PARSE_STATE_NONE,
		PARSE_STATE_BEGIN,
		PARSE_STATE_CONVERT,
		PARSE_STATE_ARRAY_BEGIN,
		PARSE_STATE_ARRAY_END,
		PARSE_STATE_END,
		PARSE_STATE_ERR,
	}
	
	public TMDynativePath(String path) throws IllegalArgumentException{
		if(path != null){
			pathStr = path;
			pathArray = new ArrayList<String>();
			currentIndex = 0;
			parse();
		} else {
			throw new IllegalArgumentException("path is empty");
		}
	}
	
	private void addOnePath(StringBuilder pathName){
		int nameLen = pathName.length();
        if(nameLen > 0){
        	pathArray.add(pathName.toString());
        	pathName.delete(0, nameLen);
        }
	}
	
	/*
	 * 转义字符包括: '.'=46; '['=91; ']'=93; '$'=36; '\'='92'
	 * 异常字符包括: '!'=33 - '~'=126外的所有字符
	 */
	private void parse(){
		ParseState state = ParseState.PARSE_STATE_NONE;
		StringBuilder pathName = new StringBuilder();
		
		for(int i = 0, len = pathStr.length(); i < len; i++){
			char c = pathStr.charAt(i);
			if(ParseState.PARSE_STATE_NONE == state){
				if(c == '$'){
					pathName.append(c);
					state = ParseState.PARSE_STATE_BEGIN;
				}
			} else if(ParseState.PARSE_STATE_BEGIN == state){
				if(c >= 33 && c <= 126 && c != '.' && c != '[' && c != ']' && c != '$' && c != '\\'){
					pathName.append(c);
				} else if(c == '\\'){
					state = ParseState.PARSE_STATE_CONVERT;
				} else if(c == '.'){
					state = ParseState.PARSE_STATE_END;
				} else if(c == '['){
					addOnePath(pathName);
	                state = ParseState.PARSE_STATE_ARRAY_BEGIN;
				} else if(c == '$'){
					state = ParseState.PARSE_STATE_ERR;
				}
			} else if(ParseState.PARSE_STATE_CONVERT == state){
				pathName.append(c);
                state = ParseState.PARSE_STATE_BEGIN;
			} else if((ParseState.PARSE_STATE_END == state)
				||(ParseState.PARSE_STATE_ARRAY_END == state)) {

                if(ParseState.PARSE_STATE_ARRAY_END == state) {
                    //数组索引支持变量，增加变量值解析
                    String index = pathName.toString();
                    if(index.startsWith("$")) {
                        int indexLength = pathName.length();

                        pathName.delete(0, indexLength);
                        pathName.append(100);
                    }
                }

				//添加path
				addOnePath(pathName);
                
                //当前的c需要重新再被处理,以免漏掉了当前个一个字符c
                state = ParseState.PARSE_STATE_BEGIN;
                i--;
			} else if(ParseState.PARSE_STATE_ARRAY_BEGIN == state){
				if(c >= 33 && c <= 126 && c != '[' && c != ']'){
                	pathName.append(c);
                }  else if(c == ']'){
                	state = ParseState.PARSE_STATE_ARRAY_END;
                    if(i == len - 1) {
                        //遍历到最后一个元素，需要回退一格，以便处理数组索引引用
                        i--;
                    }
                }
			} else if(ParseState.PARSE_STATE_ERR == state){
                System.out.println("parse path err!");
                break;
			} 
		}
		
		//最后一个item没有结束标志，需要特殊处理
		addOnePath(pathName);
	}
	
	@Override
	public void gotoNext() throws ArrayIndexOutOfBoundsException{
		if(currentIndex < pathArray.size()-1){
			currentIndex += 1;
		} else {
			throw new ArrayIndexOutOfBoundsException("over the end of path");
		}
	}

	@Override
	public boolean hasNext() {
		if(currentIndex < pathArray.size()-1){
			return true;
		}
		return false;
	}

	@Override
	public String getCurrentPath() {
		if(currentIndex >= 0 && currentIndex < pathArray.size()){
			return pathArray.get(currentIndex);
		} else {
			System.out.println("currentIndex is invalid:" + currentIndex);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return pathStr + " " + pathArray.toString();
	}
	
	public static void main(String[] args) {
		String path = "$this.box.backgroud.color";
		TMDynativePath tmPath = new TMDynativePath(path);
		System.out.println(tmPath.toString());

		path = "$this.box[0].backgroud.color";
		tmPath = new TMDynativePath(path);
		System.out.println(tmPath.toString());

		path = "$variable.\\$\\\\abc";
		tmPath = new TMDynativePath(path);
		System.out.println(tmPath.toString());

		path = "$variable.box[0].pencl[\\$long]";
		tmPath = new TMDynativePath(path);
		System.out.println(tmPath.toString());

        path = "$layout.floor.items[0]";
        tmPath = new TMDynativePath(path);
        System.out.println(tmPath.toString());

        path = "$layout.floor.items[$args.index]";
        tmPath = new TMDynativePath(path);
        System.out.println(tmPath.toString());

		
	}
	
}
