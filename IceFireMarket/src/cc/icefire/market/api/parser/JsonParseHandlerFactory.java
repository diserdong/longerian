package cc.icefire.market.api.parser;

import crow.api.parser.ParseHandler;
import crow.api.parser.ParseHandlerFactory;

public class JsonParseHandlerFactory implements ParseHandlerFactory {

	/**
	 * 依据 Class 类型返回具体的数据解析类
	 * @param <T> 
	 * @param clazz
	 * @return
	 */
	@Override
	public <T> ParseHandler<T> newHandler(Class<T> clazz) {
		String response = clazz.getName();
		String handler = response.replace("Response", "Parser").replace("response", "parser");
		
		try {
			ParseHandler<T> h = (ParseHandler<T>)Class.forName(handler).newInstance();
			return h;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
