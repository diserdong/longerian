package me.longerian.abc.serialize;

import java.io.Serializable;
import java.util.Arrays;


final public class ApkInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2488712542344088078L;
	
	private final String pkg;
	private final int versionCode;
	private final String versionName;
	private final int minSdkVersioin;
	private final int targetSdkVersion; // optional
	private final long size;
	private final String md5;
	private final String[] permissions; // optional
	private final String[] features; // optional, features apk require
	private final String[] libs; // optional,libs apk require
	
	public ApkInfo(String pkg, int versionCode, String versionName,
			int minSdkVersioin, int targetSdkVersion, long size, String md5,
			String[] permissions, String[] features, String[] libs) {
		super();
		this.pkg = pkg;
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.minSdkVersioin = minSdkVersioin;
		this.targetSdkVersion = targetSdkVersion;
		this.size = size;
		this.md5 = md5;
		
		if(permissions != null) {
			this.permissions = new String[permissions.length];
			System.arraycopy(permissions, 0, this.permissions, 0, permissions.length);
		} else {
			this.permissions = null;
		}
		if(features != null) {
			this.features = new String[features.length];
			System.arraycopy(features, 0, this.features, 0, features.length);
		} else {
			this.features = null;
		}
		if(libs != null) {
			this.libs = new String[libs.length];
			System.arraycopy(libs, 0, this.libs, 0, libs.length);
		} else {
			this.libs = null;
		}
	}

	public String getPkg() {
		return pkg;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public int getMinSdkVersioin() {
		return minSdkVersioin;
	}

	public int getTargetSdkVersion() {
		return targetSdkVersion;
	}

	public long getSize() {
		return size;
	}

	public String getMd5() {
		return md5;
	}

	public String[] getPermissions() {
		String[] result = null;
		if(permissions != null) {
			result = new String[permissions.length];
			System.arraycopy(permissions, 0, result, 0, permissions.length);
		}
		return result;
	}

	public String[] getFeatures() {
		String[] result = null;
		if(features != null) {
			result = new String[features.length];
			System.arraycopy(features, 0, result, 0, features.length);
		}
		return result;
	}

	public String[] getLibs() {
		String[] result = null;
		if(libs != null) {
			result = new String[libs.length];
			System.arraycopy(libs, 0, result, 0, libs.length);
		}
		return result;
	}

	@Override
	public String toString() {
		return "ApkInfo [pkg=" + pkg + ", versionCode=" + versionCode
				+ ", versionName=" + versionName + ", minSdkVersioin="
				+ minSdkVersioin + ", targetSdkVersion=" + targetSdkVersion
				+ ", size=" + size + ", md5=" + md5 + ", permissions="
				+ Arrays.toString(permissions) + ", features="
				+ Arrays.toString(features) + ", libs=" + Arrays.toString(libs)
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(features);
		result = prime * result + Arrays.hashCode(libs);
		result = prime * result + ((md5 == null) ? 0 : md5.hashCode());
		result = prime * result + minSdkVersioin;
		result = prime * result + Arrays.hashCode(permissions);
		result = prime * result + ((pkg == null) ? 0 : pkg.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + targetSdkVersion;
		result = prime * result + versionCode;
		result = prime * result
				+ ((versionName == null) ? 0 : versionName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApkInfo other = (ApkInfo) obj;
		if (!Arrays.equals(features, other.features))
			return false;
		if (!Arrays.equals(libs, other.libs))
			return false;
		if (md5 == null) {
			if (other.md5 != null)
				return false;
		} else if (!md5.equals(other.md5))
			return false;
		if (minSdkVersioin != other.minSdkVersioin)
			return false;
		if (!Arrays.equals(permissions, other.permissions))
			return false;
		if (pkg == null) {
			if (other.pkg != null)
				return false;
		} else if (!pkg.equals(other.pkg))
			return false;
		if (size != other.size)
			return false;
		if (targetSdkVersion != other.targetSdkVersion)
			return false;
		if (versionCode != other.versionCode)
			return false;
		if (versionName == null) {
			if (other.versionName != null)
				return false;
		} else if (!versionName.equals(other.versionName))
			return false;
		return true;
	}
	
};
