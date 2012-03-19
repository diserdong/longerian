package me.yek.top.demo.api.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import me.yek.top.demo.api.client.TaobaoParser;
import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.Order;
import me.yek.top.demo.api.pojo.Trade;
import me.yek.top.demo.api.response.TradeFullInfoGetResponse;
import me.yek.top.demo.api.response.TradesBoughtGetResponse;
import me.yek.top.demo.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TradeFullInfoGetParser extends TaobaoParser {

	@Override
	public TaobaoResponse parse(String strResponse) {
		TradeFullInfoGetResponse resp = new TradeFullInfoGetResponse();
		if(strResponse != null) {
			LogUtil.d(this.getClass().getName(), strResponse);
			try {
				JSONObject jsonResponse = new JSONObject(strResponse).getJSONObject("trade_fullinfo_get_response");
				JSONObject jsonTrade = jsonResponse.getJSONObject("trade");
				Trade trade = parseTradeFromJson(jsonTrade);
				resp.setTrade(trade);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				return resp;
			}
		} else {
			return resp;
		}
	}
	
	private Trade parseTradeFromJson(JSONObject json) throws JSONException, ParseException {
		Trade trade = new Trade();
		trade.setAdjustFee(json.getString("adjust_fee"));
		trade.setBuyerNick(json.getString("buyer_nick"));
		trade.setBuyerObtainPointFee(json.getInt("buyer_obtain_point_fee"));
		trade.setBuyerRate(json.getBoolean("buyer_rate"));
		trade.setCodFee(json.getString("cod_fee"));
		trade.setCodStatus(json.getString("cod_status"));
		if(json.optString("consign_time") != "") {
			trade.setConsignTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.optString("consign_time")));
		}
		trade.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("created")));
		trade.setDiscountFee(json.getString("discount_fee"));
		if(json.optString("end_time") != "") {
			trade.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("end_time")));
		}
		trade.setModified(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getString("modified")));
		JSONObject jsonObjOrders = json.getJSONObject("orders");
		JSONArray jsonArrOrders = jsonObjOrders.getJSONArray("order");
		ArrayList<Order> orders = new ArrayList<Order>();
		int length = jsonArrOrders.length();
		for(int i = 0; i < length; i++) {
			orders.add(parseOrderFromJson(jsonArrOrders.getJSONObject(i)));
		}
		trade.setOrders(orders);
		if(!"".equals(json.optString("pay_time"))) {
			trade.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.optString("pay_time")));
		}
		trade.setPayment(json.getString("payment"));
		trade.setPicPath(json.optString("pic_path"));
		trade.setPointFee(json.getInt("point_fee"));
		trade.setPostFee(json.getString("post_fee"));
		trade.setPrice(json.optString("price"));
		trade.setRealPointFee(json.getInt("real_point_fee"));
		trade.setReceivedPayment(json.getString("received_payment"));
		trade.setReceiverAddress(json.optString("receiver_address"));
		trade.setReceiverCity(json.getString("receiver_city"));
		trade.setReceiverDistrict(json.getString("receiver_district"));
		trade.setReceiverState(json.getString("receiver_state"));
		trade.setReceiverName(json.optString("receiver_name"));
		trade.setTid(json.getLong("tid"));
		trade.setTotalFee(json.getString("total_fee"));
		//TODO:还有其他未解析
		
		return trade;
	}
	
	private Order parseOrderFromJson(JSONObject json) throws JSONException {
		Order order = new Order();
		order.setAdjustFee(json.getString("adjust_fee"));
		order.setBuyerRate(json.getBoolean("buyer_rate"));
		order.setDiscountFee(json.getString("discount_fee"));
		order.setNum(json.getInt("num"));
		order.setNumIid(json.getLong("num_iid"));
		order.setOid(json.getLong("oid"));
		order.setPayment(json.getString("payment"));
		order.setPicPath(json.getString("pic_path"));
		order.setPrice(json.getString("price"));
		order.setRefundStatus(json.getString("refund_status"));
		order.setSellerRate(json.getBoolean("seller_rate"));
		order.setSellerType(json.getString("seller_type"));
		order.setStatus(json.getString("status"));
		order.setTitle(json.getString("title"));
		order.setTotalFee(json.getString("total_fee"));
		return order;
	}

}
