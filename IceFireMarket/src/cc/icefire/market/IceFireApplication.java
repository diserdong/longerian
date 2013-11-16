package cc.icefire.market;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Application;
import cc.icefire.market.api.CoreClient;
import cc.icefire.market.api.parser.JsonParser;
import cc.icefire.market.bitmaploader.BaseBitmapAsyncLoader;
import crow.cache.Cache;
import crow.loader.AsyncLoaderEngine;
import crow.loader.BitmapAsyncLoader;

public class IceFireApplication extends Application {
	
	private static IceFireApplication instance;
	
	/**
	 * 处理异步加载图片的对象
	 */
	private BitmapAsyncLoader mImgLoader;
	/**
	 * 封装应用的API请求
	 */
	private CoreClient mHttpAPI;
	/**
	 * 缓存处理对象
	 */
	private Cache mCacher;
	/**
	 * 线程池对象，处理所有应用级别请求数据的线程任务
	 */
	private ThreadPoolExecutor mThreadPoolExecutor;
	
	public static IceFireApplication sharedInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		init();
	}
	
	public void exit() {
		System.exit(0);
	}

	private void init() {
		mCacher = new Cache(getApplicationContext());
		mThreadPoolExecutor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue <Runnable>(100) );
		mHttpAPI = new CoreClient(getApplicationContext(), new JsonParser(), mThreadPoolExecutor, mCacher);
		mImgLoader = new BaseBitmapAsyncLoader(new AsyncLoaderEngine(getApplicationContext(), mThreadPoolExecutor, mCacher));
	}
}