package me.yek.top.demo.api.pojo;

import me.yek.top.demo.api.client.TaobaoTypePojo;

public class Order extends TaobaoTypePojo {

	private String adjustFee;
	private boolean buyerRate;
	private String discountFee;
	private int num;
	private Long numIid;
	private Long oid;
	private String payment;
	private String picPath;
	private String price;
	private String refundStatus;
	private boolean sellerRate;
	private String sellerType;
	private String status;
	private String title;
	private String totalFee;
	public String getAdjustFee() {
		return adjustFee;
	}
	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}
	public boolean isBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(boolean buyerRate) {
		this.buyerRate = buyerRate;
	}
	public String getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Long getNumIid() {
		return numIid;
	}
	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public boolean isSellerRate() {
		return sellerRate;
	}
	public void setSellerRate(boolean sellerRate) {
		this.sellerRate = sellerRate;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
}
