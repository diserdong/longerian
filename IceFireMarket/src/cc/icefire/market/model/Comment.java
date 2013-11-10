package cc.icefire.market.model;

import com.google.gson.annotations.SerializedName;

/**
 * 评论基本信息，暂时未用到，后续可补充字段
 *
 */
public class Comment {

	/**
	 * 评论时间
	 */
	@SerializedName("time")
	private String time;
	
	/**
	 * 评论内容
	 */
	@SerializedName("content")
	private String content;
	
	/**
	 * 用户评分
	 */
	@SerializedName("score")
	private int score;
	
	/**
	 * 评论者
	 */
	@SerializedName("user")
	private User user;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
