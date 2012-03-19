package me.yek.top.demo.api.request;

import java.util.TreeMap;

import me.yek.top.demo.api.client.TaobaoRequest;


public class TradeFullInfoGetRequest extends TaobaoRequest {

	private String tid;
	
	public void setTid(String tid) {
		this.tid = tid;
	}

	@Override
	public TreeMap<String, String> getAppParam() {
		TreeMap<String, String> appParam = new TreeMap<String, String>();
		appParam.put("fields","seller_nick,buyer_nick,title,type,created,sid,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,orders.title,orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type");
		appParam.put("tid", tid);
		return appParam;
	}

	@Override
	public String getMethod() {
		return "taobao.trade.fullinfo.get";
	}

}
