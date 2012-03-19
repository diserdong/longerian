package me.yek.top.demo.fragments;

import java.util.ArrayList;
import java.util.List;

import me.yek.top.demo.R;
import me.yek.top.demo.TradeDetailActivity;
import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.parser.TradesBoughtGetParser;
import me.yek.top.demo.api.pojo.Order;
import me.yek.top.demo.api.pojo.Trade;
import me.yek.top.demo.api.request.TradesBoughtGetRequest;
import me.yek.top.demo.api.response.TradesBoughtGetResponse;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TradesFragment extends ListFragment implements OnSellerSelectListener {
	
	public static final String title = "交易列表";
	private static final int CTX_ITEM_LOGISTICS = 0;
	private static final int OPT_ITEM_FILTER = 0;
	private static final String DIALOG_TAG = "filter";
	private static final String ALL = "全部";
	private static final String B = "商城店铺";
	private static final String C = "普通店铺";
	private boolean isRequestSuccess =false;
	private TradesBoughtGetTask task;
	private TradesAdapter adapter;
	private ArrayList<String> seller = new ArrayList<String>();
	private FilterDialogFragment fdf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onCreate");
		setHasOptionsMenu(true);
		seller.add(ALL);
//		seller.add(B);
//		seller.add(C);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d(this.getClass().getName(), " in onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onActivityCreated");
		task = new TradesBoughtGetTask();
		if(!isRequestSuccess) {
			task.execute(new TradesBoughtGetRequest());
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.d(this.getClass().getName(), " in onStart");
		registerForContextMenu(getListView());
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.d(this.getClass().getName(), " in onStop");
		unregisterForContextMenu(getListView());
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.d(this.getClass().getName(), " in onPause");
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.d(this.getClass().getName(), " in onDestroy");
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(), TradeDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putLong("tid", ((Trade) l.getAdapter().getItem(position)).getTid());
		intent.putExtra("tid", bundle);
		startActivity(intent);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, CTX_ITEM_LOGISTICS, 0, "查看物流信息");
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(0, OPT_ITEM_FILTER, 0, "店铺筛选");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
		case OPT_ITEM_FILTER:
			FragmentTransaction ft = getFragmentManager().beginTransaction();
	        Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);
	        if (prev != null) {
	            ft.remove(prev);
	        }
	        ft.addToBackStack(null);
	        if(fdf == null) {
	        	fdf = new FilterDialogFragment(seller);
	        	fdf.setOnSellerSelectedListener(this);
	        }
			fdf.show(getFragmentManager(), DIALOG_TAG);
			break;
		default:
				break;
		}
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
		case CTX_ITEM_LOGISTICS:
			Toast.makeText(getActivity(), "查看物流信息还未实现", Toast.LENGTH_SHORT).show();
			break;
		default:
				break;
		}
		return true;
	}
	
	@Override
	public void onSellerItemSelected(int position) {
		adapter.setFilter(seller.get(position));
		adapter.notifyDataSetChanged();
	}
	
	private void fillFilterCondition(ArrayList<Trade> trades) {
		for(Trade t : trades) {
			if(!seller.contains(t.getSellerNick())) {
				seller.add(t.getSellerNick());
			}
		}
	}

	private class TradesBoughtGetTask extends AsyncTask<TradesBoughtGetRequest, Void, TradesBoughtGetResponse>{

		@Override
		protected void onPreExecute() {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}

		@Override
		protected TradesBoughtGetResponse doInBackground(TradesBoughtGetRequest... request) {
			return (TradesBoughtGetResponse) new TaobaoClient(getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE)).excute(request[0], new TradesBoughtGetParser());
		}

		@Override
		protected void onPostExecute(TradesBoughtGetResponse result) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			if(result.getTrades() != null) {
				isRequestSuccess = true;
				if(getListView() != null) {
					getListView().setCacheColorHint(Color.alpha(0));
					adapter = new TradesAdapter(getActivity().getApplicationContext(), R.layout.trade_item, result.getTrades());
					setListAdapter(adapter);
					fillFilterCondition(result.getTrades());
				}
			} else {
				isRequestSuccess =false;
			}
		}
		
	}
	
	private class TradesAdapter extends ArrayAdapter<Trade> {

		private Context ctx;
		private int layoutRes;
		private List<Trade> backupItems;
		private List<Trade> items = new ArrayList<Trade>();
		private String filter;
		
		public TradesAdapter(Context context, int textViewResourceId,
				List<Trade> objects) {
			super(context, textViewResourceId, objects);
			ctx = context;
			layoutRes = textViewResourceId;
			backupItems = objects;
			setFilter(null);
		}

		public void setFilter(String filter) {
			this.filter = filter;
			items.clear();
			if(backupItems != null) {
				for(Trade t : backupItems) {
					if(this.filter == null 
							|| this.filter.equals(ALL) 
							|| this.filter.equals(t.getSellerNick())) {
						items.add(t);
					}
				}
			}
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder;
				if(convertView == null) {
					convertView = LayoutInflater.from(ctx).inflate(layoutRes, null, false);
					holder = new ViewHolder(convertView);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.getCreated().setText(items.get(position).getCreated().toString());
				holder.getBuyerNick().setText(items.get(position).getBuyerNick());
				StringBuilder sb = new StringBuilder();
				for(Order o : items.get(position).getOrders()) {
					sb.append(o.getTitle()).append("\n");
				}
				holder.getOrders().setText(sb.toString());
				return convertView;
		}
	}
	
	private class ViewHolder {
		
		private View base;
		private TextView created;
		private TextView buyerNick;
		private TextView orders;
		
		public ViewHolder(View base) {
			this.base = base;
		}

		public TextView getCreated() {
			if(created == null) {
				created = (TextView) base.findViewById(R.id.created);
			}
			return created;
		}

		public TextView getBuyerNick() {
			if(buyerNick == null) {
				buyerNick = (TextView) base.findViewById(R.id.buyer_nick);
			}
			return buyerNick;
		}

		public TextView getOrders() {
			if(orders == null) {
				orders = (TextView) base.findViewById(R.id.orders);
			}
			return orders;
		}

	}
	
	public class FilterDialogFragment extends DialogFragment {
		
		private OnSellerSelectListener mListener;
		
		public FilterDialogFragment(ArrayList<String> seller) {
			Bundle sellers = new Bundle();
			sellers.putStringArrayList("seller", seller);
			setArguments(sellers);
		}
		
		public void setOnSellerSelectedListener(OnSellerSelectListener listener) {
			mListener = listener;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light_Panel);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View layout = inflater.inflate(R.layout.trade_filter_fragment, container, false);
			layout.setBackgroundColor(Color.DKGRAY);
			ListView list = (ListView) layout.findViewById(R.id.sellers);
			list.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_single_choice, getArguments().getStringArrayList("seller")));
			list.setOnItemClickListener(mOnListItemClickListener);
			return layout;
		}
		
		private OnItemClickListener mOnListItemClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, 
					long id) {
				fdf.dismiss();
				if(mListener != null) {
					mListener.onSellerItemSelected(position);
				}
			}
		};
		
	}

}
