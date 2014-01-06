package me.longerian.abc.fakeclient;



/**
 * 运行时无缝连接服务配置参数
 * @author Kenny
 *
 */
public class WcsConfig {
	
	public static final String PRIVATE_KEY = "TencentMolo&&##%%!!!1234";
	public static boolean RUNTIME_DEBUG = false;
	
	public static int VERSION = 1;
	public static int COUNT_LOST_THRESHOLD = 16;
	public static int TIME_LOST_THRESHOLD = 20;
	public static int COUNT_LOST_UPPER_LIMIT = 128;
	public static int CACHE_SIZE_FOR_ACK = 1048576;
	public static float CACHE_SIZE_FACTOR = 1.2f;
	public static int GZIP_LOWER_THRESHOLD = 256;
	public static int REMOTE_OFFLINE_CACHE_TIME = 180;
	public static int CONNECTION_CHECK_INTERVAL = 90;
	public static int HEARTBEAT_INTERVAL = 180;
	public static boolean ALLOW_MOBILE_NETWORK = false;
	public static int MAX_AGENT_SESSION_POOL_SIZE = 20;

	public static void updateConfig(String key, String value) {
		try {
			if("VERSION".equalsIgnoreCase(key)) {
				VERSION = Integer.parseInt(value);
				return ;
			}
			if("COUNT_LOST_THRESHOLD".equalsIgnoreCase(key)) {
				COUNT_LOST_THRESHOLD = Integer.parseInt(value);
				return ;
			}
			if("TIME_LOST_THRESHOLD".equalsIgnoreCase(key)) {
				TIME_LOST_THRESHOLD = Integer.parseInt(value);
				return ;
			}
			if("COUNT_LOST_UPPER_LIMIT".equalsIgnoreCase(key)) {
				COUNT_LOST_UPPER_LIMIT = Integer.parseInt(value);
				return ;
			}
			if("CACHE_SIZE_FOR_ACK".equalsIgnoreCase(key)) {
				CACHE_SIZE_FOR_ACK = Integer.parseInt(value);
				return ;
			}
			if("CACHE_SIZE_FACTOR".equals(key)) {
				CACHE_SIZE_FACTOR = Float.parseFloat(value);
				if(Float.compare(CACHE_SIZE_FACTOR, 1.0f) < 0) {
					CACHE_SIZE_FACTOR = 1.2f;
				}
				return ;
			}
			if("GZIP_LOWER_THRESHOLD".equalsIgnoreCase(key)) {
				GZIP_LOWER_THRESHOLD = Integer.parseInt(value);
				return ;
			}
			if("REMOTE_OFFLINE_CACHE_TIME".equalsIgnoreCase(key)) {
				REMOTE_OFFLINE_CACHE_TIME = Integer.parseInt(value);
				return ;
			}
			if("CONNECTION_CHECK_INTERVAL".equalsIgnoreCase(key)) {
				CONNECTION_CHECK_INTERVAL = Integer.parseInt(value);
				return ;
			}
			if("HEARTBEAT_INTERVAL".equalsIgnoreCase(key)) {
				HEARTBEAT_INTERVAL = Integer.parseInt(value);
				return ;
			}
			if("ALLOW_MOBILE_NETWORK".equalsIgnoreCase(key)) {
				ALLOW_MOBILE_NETWORK = Boolean.parseBoolean(value.toLowerCase());
			}
			if("MAX_AGENT_SESSION_POOL_SIZE".equalsIgnoreCase(key)) {
				MAX_AGENT_SESSION_POOL_SIZE = Integer.parseInt(value);
				return ;
			}
		} catch(NumberFormatException e) {
		}
	}
	
	public static void printConfig() {
//		XLog.d("WcsConfig", "VERSION = " + VERSION);
//		XLog.d("WcsConfig", "COUNT_LOST_THRESHOLD = " + COUNT_LOST_THRESHOLD);
//		XLog.d("WcsConfig", "TIME_LOST_THRESHOLD = " + TIME_LOST_THRESHOLD);
//		XLog.d("WcsConfig", "COUNT_LOST_UPPER_LIMIT = " + COUNT_LOST_UPPER_LIMIT);
//		XLog.d("WcsConfig", "CACHE_SIZE_FOR_ACK = " + CACHE_SIZE_FOR_ACK);
//		XLog.d("WcsConfig", "CACHE_SIZE_FACTOR = " + CACHE_SIZE_FACTOR);
//		XLog.d("WcsConfig", "GZIP_LOWER_THRESHOLD = " + GZIP_LOWER_THRESHOLD);
//		XLog.d("WcsConfig", "REMOTE_OFFLINE_CACHE_TIME = " + REMOTE_OFFLINE_CACHE_TIME);
//		XLog.d("WcsConfig", "CONNECTION_CHECK_INTERVAL = " + CONNECTION_CHECK_INTERVAL);
//		XLog.d("WcsConfig", "HEARTBEAT_INTERVAL = " + HEARTBEAT_INTERVAL);
//		XLog.d("WcsConfig", "ALLOW_MOBILE_NETWORK = " + ALLOW_MOBILE_NETWORK);
//		XLog.d("WcsConfig", "MAX_AGENT_SESSION_POOL_SIZE = " + MAX_AGENT_SESSION_POOL_SIZE);
	}
	
}
