package cc.icefire.market.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * APP简要信息
 */
public class BasicAppItem implements Parcelable {

	/**
	 * 应用id
	 */
	@SerializedName("id")
    private String id;

	/**
	 * 应用名
	 */
	@SerializedName("apk_name")
    private String apkName;
	
	/**
	 * 应用包名
	 */
	@SerializedName("pkg_name")
	private String pkgName;
    
	/**
	 * 版本号
	 */
	@SerializedName("version_code")
    private int versionCode;
    
	/**
	 * 版本名称
	 */
	@SerializedName("version_name")
    private String versionName;

	/**
	 * apk的md5校验值
	 */
	@SerializedName("apk_md5")
    private String apkMd5;

	/**
	 * 所属分类id
	 */
	@SerializedName("category_id")
    private int categoryId;

	/**
	 * 图片地址
	 */
	@SerializedName("icon_url")
    private String iconUrl;

	/**
	 * 综合评分
	 */
	@SerializedName("score")
    private double score;

	/**
	 * 下载次数
	 */
	@SerializedName("download_count")
    private int downloadCount;

	/**
	 * 下载地址
	 */
	@SerializedName("download_url")
    private String downloadUrl;

	/**
	 * 应用描述
	 */
	@SerializedName("desp")
    private String desp;

	/**
	 * 应用大小，单位byte
	 */
	@SerializedName("size")
    private int size;
	
	/**
	 * 应用截图
	 */
	@SerializedName("screenshots")
	private String[] screenshots;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkMd5() {
		return apkMd5;
	}

	public void setApkMd5(String apkMd5) {
		this.apkMd5 = apkMd5;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String[] getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(String[] screenshots) {
		this.screenshots = screenshots;
	}

	public BasicAppItem(Parcel in) {
		readFromParcel(in);
    }  
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(id);
		parcel.writeString(apkName);
		parcel.writeString(pkgName);
		parcel.writeInt(versionCode);
		parcel.writeString(versionName);
		parcel.writeString(apkMd5);
		parcel.writeInt(categoryId);
		parcel.writeString(iconUrl);
		parcel.writeDouble(score);
		parcel.writeInt(downloadCount);
		parcel.writeString(downloadUrl);
		parcel.writeString(desp);
		parcel.writeInt(size);
		parcel.writeStringArray(screenshots);
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readString();
		apkName = in.readString();
		pkgName = in.readString();
		versionCode = in.readInt();
		versionName = in.readString();
		apkMd5 = in.readString();
		categoryId = in.readInt();
		iconUrl = in.readString();
		score = in.readDouble();
		downloadCount = in.readInt();
		downloadUrl = in.readString();
		desp = in.readString();
		size = in.readInt();
		screenshots = in.createStringArray();
	}
    
	public static final Parcelable.Creator<BasicAppItem> CREATOR = new Parcelable.Creator<BasicAppItem>() {  
        @Override  
        public BasicAppItem createFromParcel(Parcel source) {  
            return new BasicAppItem(source);  
        }  
  
        @Override  
        public BasicAppItem[] newArray(int size) {  
            return new BasicAppItem[size];  
        }  
    };  
    
}
