package me.yek.top.demo.api.pojo;

import java.util.ArrayList;
import java.util.Date;

import me.yek.top.demo.api.client.TaobaoTypePojo;


public class Trade extends TaobaoTypePojo {

	private String adjustFee;
	private String buyerNick;
	private int buyerObtainPointFee;
	private boolean buyerRate;
	private String codFee;
	/**
	 *  初始状态 NEW_CREATED, 
	 *  接单成功 ACCEPTED_BY_COMPANY, 
	 *  接单失败 REJECTED_BY_COMPANY, 
	 *  接单超时 RECIEVE_TIMEOUT, 
	 *  揽收成功 TAKEN_IN_SUCCESS, 
	 *  揽收失败 TAKEN_IN_FAILED, 
	 *  揽收超时 RECIEVE_TIMEOUT, 
	 *  签收成功 SIGN_IN, 
	 *  签收失败 REJECTED_BY_OTHER_SIDE, 
	 *  订单等待发送给物流公司 WAITING_TO_BE_SENT, 
	 *  用户取消物流订单 CANCELED
	 */
	private String codStatus;
	private Date consignTime;
	private Date created;
	private String discountFee;
	private Date endTime;
	private Date modified;
	private ArrayList<Order> orders;
	private Date payTime;
	private String payment;
	private String picPath;
	private int pointFee;
	private String postFee;
	private String price;
	private int realPointFee;
	private String receivedPayment;
	private String receiverAddress;
	private String receiverCity;
	private String receiverDistrict;
	private String receiverMobile;
	private String receiverName;
	private String receiverPhone;
	private String receiverState;
	private String receiverZip;
	private String sellerNick;
	private boolean sellerRate;
	private String shippingType;
	private String sid;
	private String status;
	private Long tid;
	private String title;
	private String totalFee;
	private String type;
	public String getAdjustFee() {
		return adjustFee;
	}
	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public int getBuyerObtainPointFee() {
		return buyerObtainPointFee;
	}
	public void setBuyerObtainPointFee(int buyerObtainPointFee) {
		this.buyerObtainPointFee = buyerObtainPointFee;
	}
	public boolean isBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(boolean buyerRate) {
		this.buyerRate = buyerRate;
	}
	public String getCodFee() {
		return codFee;
	}
	public void setCodFee(String codFee) {
		this.codFee = codFee;
	}
	public String getCodStatus() {
		return codStatus;
	}
	public void setCodStatus(String codStatus) {
		this.codStatus = codStatus;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public int getPointFee() {
		return pointFee;
	}
	public void setPointFee(int pointFee) {
		this.pointFee = pointFee;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public int getRealPointFee() {
		return realPointFee;
	}
	public void setRealPointFee(int realPointFee) {
		this.realPointFee = realPointFee;
	}
	public String getReceivedPayment() {
		return receivedPayment;
	}
	public void setReceivedPayment(String receivedPayment) {
		this.receivedPayment = receivedPayment;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverZip() {
		return receiverZip;
	}
	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	public boolean isSellerRate() {
		return sellerRate;
	}
	public void setSellerRate(boolean sellerRate) {
		this.sellerRate = sellerRate;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
}
