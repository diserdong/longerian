package cc.icefire.market.model;

import com.google.gson.annotations.SerializedName;

/**
 * 分类信息
 *
 */
public class Category {

	@SerializedName("id")
	private int id;
	
	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("name")
    private String name;

	@SerializedName("icon_url")
    private String iconUrl;

    /**
     * 分类描述信息
     */
	@SerializedName("desp")
    private String desp;

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

}
