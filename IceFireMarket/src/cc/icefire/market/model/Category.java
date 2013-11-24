package cc.icefire.market.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 分类信息
 *
 */
public class Category implements Parcelable {

	@SerializedName("id")
	private int id;
	
	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("name")
    private String name;

	@SerializedName("icon_url")
    private String iconUrl;
	
	/**
	 * 1.应用， 0.游戏
	 */
	@SerializedName("app_or_game")
	private int appOrGame;

    /**
     * 分类描述信息
     */
	@SerializedName("desp")
    private String desp;

	public Category(int id, int parentId, String name, String iconUrl,
			int appOrGame, String desp) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.iconUrl = iconUrl;
		this.appOrGame = appOrGame;
		this.desp = desp;
	}
	

	public Category() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public int getAppOrGame() {
		return appOrGame;
	}

	public void setAppOrGame(int appOrGame) {
		this.appOrGame = appOrGame;
	}


	public Category(Parcel in) {
		readFromParcel(in);
    }  
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeInt(parentId);
		parcel.writeString(name);
		parcel.writeInt(appOrGame);
		parcel.writeString(iconUrl);
		parcel.writeString(desp);
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		parentId = in.readInt();
		name = in.readString();
		appOrGame = in.readInt();
		iconUrl = in.readString();
		desp = in.readString();
	}
    
	public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {  
        @Override  
        public Category createFromParcel(Parcel source) {  
            return new Category(source);  
        }  
  
        @Override  
        public Category[] newArray(int size) {  
            return new Category[size];  
        }  
    };

}
