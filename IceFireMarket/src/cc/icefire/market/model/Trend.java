package cc.icefire.market.model;

import com.google.gson.annotations.SerializedName;

/**
 * 热门搜索关键词
 * 对应的JSON对象表示（JSON的key为Java字段上方的注解）：
 * {
    "id": 10001,
    "word": "weibo",
    "count": 20000
	}
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
