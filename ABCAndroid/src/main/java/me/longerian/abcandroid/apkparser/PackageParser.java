package me.longerian.abcandroid.apkparser;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class PackageParser {

public static final int INSTALL_SUCCEEDED = 1;
	
	public static final int INSTALL_FAILED_INVALID_APK = -2;
	public static final int INSTALL_FAILED_INVALID_URI = -3;
	public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
	public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
	public static final int INSTALL_FAILED_NO_SHARED_USER = -6;
	public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
	public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
	public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
	public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
	public static final int INSTALL_FAILED_DEXOPT = -11;
	public static final int INSTALL_FAILED_OLDER_SDK = -12;
	public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
	public static final int INSTALL_FAILED_NEWER_SDK = -14;
	public static final int INSTALL_FAILED_TEST_ONLY = -15;
	public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
	public static final int INSTALL_FAILED_MISSING_FEATURE = -17;
	public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
	public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
	public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
	public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
	public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
	public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
	public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
	public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
	public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
	public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
	public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
	public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
	public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
	public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
	public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
	public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
	public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
	
	public int err = INSTALL_SUCCEEDED ;
	
	//use this to 
	private Class classPackageParser = null;
	private Constructor constructorPackageParser = null;	
	private Method methodParsePackage = null;
	
	private Resources res = null;
	private AssetManager assmgr  = null;
	
	@Override
	protected void finalize() throws Throwable 
	{
		super.finalize();
		if(assmgr != null) {
			assmgr.close();
		}
	}
	
	public TApplicationInfo parseApplicationInfo(String path) throws Exception {
		this.err = INSTALL_SUCCEEDED;
		if( classPackageParser == null) {
			 classPackageParser = Class.forName("android.content.pm.PackageParser");
		}
		if( constructorPackageParser == null) {
			constructorPackageParser = classPackageParser.getConstructor(String.class);
		}		
		if( methodParsePackage == null) {
			methodParsePackage  = classPackageParser.getDeclaredMethod("parsePackage", File.class,String.class,DisplayMetrics.class,int.class);
		}
		Object packageParser = constructorPackageParser.newInstance(path);
		
		Method methodGetParseError = classPackageParser.getMethod("getParseError");
		DisplayMetrics metrics = new DisplayMetrics();
        metrics.setToDefaults();
        Object objectPackage = methodParsePackage.invoke(packageParser, new File(path),path,metrics,0);
        this.err = (Integer) methodGetParseError.invoke(packageParser);
        if( objectPackage == null) {
        	return null;
        }
        TApplicationInfo ai = new TApplicationInfo();
        Class classPackage = objectPackage.getClass();
        Field fieldActivities = classPackage.getDeclaredField("activities"); //Package对象中有activities字段
        
        Class classComponent = null;
        Method methodGetComponentName = null;
        Field fieldIntents = null;
        ArrayList<Object> activities = (ArrayList<Object>) fieldActivities.get(objectPackage);
        for(Object o : activities) {
        	TActivityInfo aci = new TActivityInfo();
        	if(classComponent == null) {
        		classComponent = o.getClass().getSuperclass(); //o对象是Activity类，其父类为Component，这两个类都是PackageParser的父类
        		methodGetComponentName = classComponent.getDeclaredMethod("getComponentName");//Component类中有getComponentName方法
        		methodGetComponentName.setAccessible(true);
        		fieldIntents = classComponent.getDeclaredField("intents");//Component类中有intents字段
        	}
        	ComponentName cn = (ComponentName) methodGetComponentName.invoke(o);
        	aci.setClassName(cn.getClassName());
        	ArrayList<Object> intents = (ArrayList<Object>) fieldIntents.get(o);
        	for(Object i : intents) {
        		IntentFilter intentFilter = ((IntentFilter) i);//intents集合的元素都是IntentFilter子类
        		aci.addIntentFilter(intentFilter);
        	}
        	ai.addActivity(aci);
        }
        return ai;
	}

}
