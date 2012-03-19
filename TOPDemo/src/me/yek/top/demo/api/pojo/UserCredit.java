package me.yek.top.demo.api.pojo;

import me.yek.top.demo.api.client.TaobaoTypePojo;

public class UserCredit extends TaobaoTypePojo {

	private int goodNum;
	private int level;
	private int score;
	private int totalNum;
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
}
