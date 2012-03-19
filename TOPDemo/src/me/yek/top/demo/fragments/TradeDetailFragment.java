package me.yek.top.demo.fragments;

import java.text.SimpleDateFormat;

import me.yek.top.demo.R;
import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.parser.TradeFullInfoGetParser;
import me.yek.top.demo.api.pojo.Order;
import me.yek.top.demo.api.pojo.Trade;
import me.yek.top.demo.api.request.TradeFullInfoGetRequest;
import me.yek.top.demo.api.response.TradeFullInfoGetResponse;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TradeDetailFragment extends Fragment {
	
	private boolean isRequestSuccess =false;
	private TradeFullInfoGetTask task;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d(this.getClass().getName(), " in onCreateView");
		return inflater.inflate(R.layout.trade_detail_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onActivityCreated");
		if(!isRequestSuccess) {
			TradeFullInfoGetRequest request = new TradeFullInfoGetRequest();
			request.setTid(getTid());
			task = new TradeFullInfoGetTask();
			task.execute(request);
		}
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.d(this.getClass().getName(), " in onDestroyView");
		if(task != null) {
			task.cancel(true);
			isRequestSuccess = false;
		}
	}
	
	private String getTid() {
		return String.valueOf(getArguments().getLong("tid", 0));
	}
	
	private class TradeFullInfoGetTask extends AsyncTask<TradeFullInfoGetRequest, Void, TradeFullInfoGetResponse>{

		@Override
		protected void onPreExecute() {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}

		@Override
		protected TradeFullInfoGetResponse doInBackground(TradeFullInfoGetRequest... request) {
			return (TradeFullInfoGetResponse) new TaobaoClient(getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE)).excute(request[0], new TradeFullInfoGetParser());
		}

		@Override
		protected void onPostExecute(TradeFullInfoGetResponse result) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			if(result.getTrade() != null) {
				isRequestSuccess = true;
				setViewWithTradeData(result.getTrade());
			} else {
				isRequestSuccess = false;
			}
		}
		
	}

	private void setViewWithTradeData(Trade trade) {
		Activity activity = getActivity();
		TextView adjustFee = (TextView) activity.findViewById(R.id.adjust_fee);
		adjustFee.setText(trade.getAdjustFee());
		TextView buyerNick = (TextView) activity.findViewById(R.id.buyer_nick);
		buyerNick.setText(trade.getBuyerNick());
		TextView buyerObtainPointFee = (TextView) activity.findViewById(R.id.buyer_obtain_point_fee);
		buyerObtainPointFee.setText(trade.getBuyerObtainPointFee() + "");
		TextView buyerRate = (TextView) activity.findViewById(R.id.buyer_rate);
		buyerRate.setText(String.valueOf(trade.isBuyerRate()));
		TextView codFee = (TextView) activity.findViewById(R.id.cod_fee);
		codFee.setText(trade.getCodFee());
		TextView codStatus = (TextView) activity.findViewById(R.id.cod_status);
		codStatus.setText(trade.getCodStatus());
		TextView consignTime = (TextView) activity.findViewById(R.id.consign_time);
		if(trade.getConsignTime() != null) {
			consignTime.setText(sdf.format(trade.getConsignTime()));
		}
		TextView created = (TextView) activity.findViewById(R.id.created);
		created.setText(sdf.format(trade.getCreated()));
		TextView discountFee = (TextView) activity.findViewById(R.id.discount_fee);
		discountFee.setText(trade.getDiscountFee());
		TextView endTime = (TextView) activity.findViewById(R.id.end_time);
		endTime.setText(sdf.format(trade.getEndTime()));
		TextView modified = (TextView) activity.findViewById(R.id.modified);
		modified.setText(sdf.format(trade.getModified()));
		
		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.orders);
		for(Order o : trade.getOrders()) {
			LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.order_item_layout, null);
			setViewWithOrderData(itemLayout, o);
			layout.addView(itemLayout);
		}
		
		TextView payTime = (TextView) activity.findViewById(R.id.pay_time);
		if(trade.getPayTime() != null) {
			payTime.setText(sdf.format(trade.getPayTime()));
		}
		TextView payment = (TextView) activity.findViewById(R.id.payment);
		payment.setText(trade.getPayment());
		TextView picPath = (TextView) activity.findViewById(R.id.pic_path);
		picPath.setText(trade.getPicPath());
		TextView pointFee = (TextView) activity.findViewById(R.id.point_fee);
		pointFee.setText(trade.getPointFee() + "");
		TextView postFee = (TextView) activity.findViewById(R.id.post_fee);
		postFee.setText(trade.getPostFee());
		TextView price = (TextView) activity.findViewById(R.id.price);
		price.setText(trade.getPrice());
		TextView realPointFee = (TextView) activity.findViewById(R.id.real_point_fee);
		realPointFee.setText(trade.getRealPointFee() + "");
		TextView receivedPayment = (TextView) activity.findViewById(R.id.received_payment);
		receivedPayment.setText(trade.getReceivedPayment());
		TextView receiverAddress = (TextView) activity.findViewById(R.id.receiver_address);
		receiverAddress.setText(trade.getReceiverState() + trade.getReceiverCity() 
				+ trade.getReceiverDistrict() + trade.getReceiverAddress());
		TextView receiverName = (TextView) activity.findViewById(R.id.receiver_name);
		receiverName.setText(trade.getReceiverName());
		TextView sellerNick = (TextView) activity.findViewById(R.id.seller_nick);
		sellerNick.setText(trade.getSellerNick());
		TextView totalFee = (TextView) activity.findViewById(R.id.total_fee);
		totalFee.setText(trade.getTotalFee());
		LogUtil.d("longer", "trade total fee: " + trade.getTotalFee());
	}
	
	private void setViewWithOrderData(View view, Order order) {
		TextView adjustFee = (TextView) view.findViewById(R.id.order_adjust_fee);
		adjustFee.setText(order.getAdjustFee());
		TextView buyerRate = (TextView) view.findViewById(R.id.order_buyer_rate);
		buyerRate.setText(order.isBuyerRate() + "");
		TextView discountFee = (TextView) view.findViewById(R.id.order_discount_fee);
		discountFee.setText(order.getDiscountFee());
		TextView num = (TextView) view.findViewById(R.id.order_num);
		num.setText(order.getNum() + "");
		TextView numIid = (TextView) view.findViewById(R.id.order_num_iid);
		numIid.setText(order.getNumIid() + "");
		TextView oid = (TextView) view.findViewById(R.id.order_oid);
		oid.setText(order.getOid() + "");
		TextView picPath = (TextView) view.findViewById(R.id.order_pic_path);
		picPath.setText(order.getPicPath());
		TextView price = (TextView) view.findViewById(R.id.order_price);
		price.setText(order.getPrice());
		TextView refundStatus = (TextView) view.findViewById(R.id.order_refund_status);
		refundStatus.setText(order.getRefundStatus());
		TextView sellerRate = (TextView) view.findViewById(R.id.order_seller_rate);
		sellerRate.setText(order.isSellerRate() + "");
		TextView sellerType = (TextView) view.findViewById(R.id.order_seller_type);
		sellerType.setText(order.getSellerType());
		TextView status = (TextView) view.findViewById(R.id.order_status);
		status.setText(order.getStatus());
		TextView title = (TextView) view.findViewById(R.id.order_title);
		title.setText(order.getTitle());
		TextView totalFee = (TextView) view.findViewById(R.id.order_total_fee);
		totalFee.setText(order.getTotalFee());
	}
	
}
