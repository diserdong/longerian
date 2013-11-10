package cc.icefire.market.api.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import crow.api.parser.ParseException;
import crow.api.parser.Parser;
import crow.util.Util;

public class JsonParser implements Parser {
	
	private JsonParseHandlerFactory handlerFactory;
	
	public JsonParser() {
		handlerFactory = new JsonParseHandlerFactory();
	}

	@Override
	public <T> T parse(String str, Class<T> clazz) throws ParseException {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		return parse(is, clazz);
	}

	@Override
	public <T> T parse(File file, Class<T> clazz) throws ParseException {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ParseException();
		}
		return parse(input, clazz);
	}

	@Override
	public <T> T parse(InputStream is, Class<T> clazz) throws ParseException {
		return getModelFromJSON(is, clazz);
	}

	private <T> T getModelFromJSON(InputStream inputSource, Class<T> clazz) {
		JsonParseHandler<T> handler = (JsonParseHandler<T>) handlerFactory.newHandler(clazz);
		String inputStr = null;
		try {
			inputStr = Util.inputStreamToString(inputSource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler.parse(inputStr);
		return handler.getModel();
	}
}
