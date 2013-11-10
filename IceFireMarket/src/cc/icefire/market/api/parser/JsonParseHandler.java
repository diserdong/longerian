package cc.icefire.market.api.parser;

import crow.api.parser.ParseHandler;

public abstract class JsonParseHandler<T> implements ParseHandler<T> {

	protected static final String SUCCESS = "1";
	protected static final String FAIL = "0";
	
	public abstract void parse(String inputSource);
	
}
