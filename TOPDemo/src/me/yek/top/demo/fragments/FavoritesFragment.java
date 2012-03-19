package me.yek.top.demo.fragments;

import java.util.List;

import me.yek.top.demo.R;
import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.parser.FavoriteSearchParser;
import me.yek.top.demo.api.pojo.CollectItem;
import me.yek.top.demo.api.request.FavoriteSearchRequest;
import me.yek.top.demo.api.response.FavoriteSearchResponse;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FavoritesFragment extends ListFragment {

	public static final String title = "收藏列表";
	private boolean isRequestSuccess =false;
	private SharedPreferences pref;
	private FavoriteSearchTask task;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d(this.getClass().getName(), " in onCreate");
		pref = getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE);
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
		task = new FavoriteSearchTask();
		if(!isRequestSuccess) {
			FavoriteSearchRequest rqst = new FavoriteSearchRequest();
			rqst.setBuyerNick(PreferenceUtil.getUserName(pref));
			task.execute(rqst);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.d(this.getClass().getName(), " in onPause");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(task != null) {
			task.cancel(true);
			isRequestSuccess = false;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

	private class FavoriteSearchTask extends AsyncTask<FavoriteSearchRequest, Void, FavoriteSearchResponse>{

		@Override
		protected void onPreExecute() {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(true);
			}
		}

		@Override
		protected FavoriteSearchResponse doInBackground(FavoriteSearchRequest... request) {
			return (FavoriteSearchResponse) new TaobaoClient(getActivity().getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE)).excute(request[0], new FavoriteSearchParser());
		}

		@Override
		protected void onPostExecute(FavoriteSearchResponse result) {
			if(getActivity() != null) {
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			if(result.getCollectItems() != null) {
				isRequestSuccess = true;
				if(getListView() != null) {
					getListView().setCacheColorHint(Color.alpha(0));
					setListAdapter(new FavoriteAdapter(getActivity().getApplicationContext(), R.layout.favorite_item, result.getCollectItems()));
				}
			} else {
				isRequestSuccess = false;
			}
		}
		
	}
	
	private class FavoriteAdapter extends ArrayAdapter<CollectItem> {

		private Context ctx;
		private int layoutRes;
		private List<CollectItem> items; 
		
		public FavoriteAdapter(Context context, int textViewResourceId,
				List<CollectItem> objects) {
			super(context, textViewResourceId, objects);
			ctx = context;
			layoutRes = textViewResourceId;
			items = objects;
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
			holder.getTitle().setText(items.get(position).getTitle());
			holder.getItemOwnerNick().setText(items.get(position).getItemOwnerNick());
			holder.getItemNumid().setText(String.valueOf(items.get(position).getItemNumid()));
			return convertView;
		}
		
	}
	
	private class ViewHolder {
		
		private View base;
		private TextView title;
		private TextView itemOwnerNick;
		private TextView itemNumid;
		
		public ViewHolder(View base) {
			this.base = base;
		}

		public TextView getTitle() {
			if(title == null) {
				title = (TextView) base.findViewById(R.id.title);
			}
			return title;
		}

		public TextView getItemOwnerNick() {
			if(itemOwnerNick == null) {
				itemOwnerNick = (TextView) base.findViewById(R.id.item_owner_nick);
			}
			return itemOwnerNick;
		}

		public TextView getItemNumid() {
			if(itemNumid == null) {
				itemNumid = (TextView) base.findViewById(R.id.item_numid);
			}
			return itemNumid;
		}
		
	}
	
}
