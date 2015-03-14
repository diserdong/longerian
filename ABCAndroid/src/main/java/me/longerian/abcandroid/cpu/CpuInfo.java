package me.longerian.abcandroid.cpu;

public class CpuInfo {

	public static String getSupportArchitecture()
	{
		
		String cpu =  android.os.Build.CPU_ABI;
		if( android.os.Build.VERSION.SDK_INT >= 8)
		{
			java.lang.reflect.Field field =  null;
			String cpu2 =  null;
			try {
				field = android.os.Build.class.getField("CPU_ABI2");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if( field != null)
			{
				try {
					cpu2 = (String)field.get(null);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(Throwable e)
				{
					e.printStackTrace();
				}
			}
			if( cpu2 != null)
				cpu = cpu+","+cpu2;
		}
		
		return cpu;
	}
	
}
