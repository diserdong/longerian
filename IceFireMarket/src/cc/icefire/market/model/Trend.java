package cc.icefire.market.model;

import com.google.gson.annotations.SerializedName;

/**
 * 热门搜索关键词
 */
public class Trend {

	@SerializedName("id")
	private int id;

	@SerializedName("word")
    private String word;
	
	@SerializedName("count")
	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
