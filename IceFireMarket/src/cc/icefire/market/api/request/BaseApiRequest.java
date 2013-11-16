package cc.icefire.market.api.request;

import java.util.Date;

import org.apache.http.client.methods.HttpPost;

import cc.icefire.market.api.BaseApiConstant;
import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.BaseApiResponse;
import crow.api.ApiRequest;
import crow.util.FileUtil;
import crow.util.Util;

/**
 * <p>
 * 在CoreApiClient 中会对如下参数进行设定。一般不需在Request 对象中设定，除非不默认规则不同才进行设定。<br/></p>
 * method : 若{@link #getMethod()}不为空由 ApiClient 进行设定。<br/>
 * t ： 时间戳    <br/>
 * appkey :   <br/>
 * authtype： <br/>
 * sign: 默认签名方式为： 当 {@link #getMethod()}返回不为空时，{@link Util.md5}({@link #getMethod()} + {@link #getTimestamp()})<br/>
 * 					    
 * 
 * @author long api请求中，客户端时间戳t、加密格式authtype、软件身份appkey、数据验证签名sign这四个系统级参数应当统一设置；
 *         签名的加密各API不完全相同
 * @param <T>
 */
public abstract class BaseApiRequest<T extends BaseApiResponse>
		implements ApiRequest<T, BaseApiContext>,BaseApiConstant{
	private String t;

	/**
	 * API 请求的时间戳
	 * 
	 * @return
	 */
	public String getTimestamp() {
		if (t == null) {
			Date date = new Date();
			long time = date.getTime() / 1000;
			t = String.valueOf(time);
		}
		return t;
	}

	public static String makeCachePath(String ...strings){
		// 去掉殊字符
		int len = strings.length;
		for(int i = 0; i < len; i ++) {
			if(Util.isEmpty(strings[i]))
				strings[i] = "default";
		}
		return FileUtil.joinPath(strings);		
	}
	
	@Override
	public Class<?> getHttpRequestClass() {
		return HttpPost.class;
	}
	
}
