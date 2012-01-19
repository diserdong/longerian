package me.longerian.interactive.demo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class InteractiveAdapter extends ArrayAdapter<RowModel> {
	
	private Context context;
	private ArrayList<RowModel> items;

	public InteractiveAdapter(Context context, ArrayList<RowModel> items) {
		super(context, R.layout.row_layout, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RatingBar rate;
		final ViewHolder holder;
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.row_layout, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		OnRatingBarChangeListener listener = new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				Integer pos = (Integer) ratingBar.getTag(); 
	            RowModel row = items.get(pos); 
	            row.setRating(rating); 
	            holder.getLabel().setText(row.toString());
			}
		};
		RowModel row = items.get(position);
		holder.getLabel().setText(row.toString());
		rate = holder.getRate();
		rate.setOnRatingBarChangeListener(listener);
		rate.setTag(new Integer(position));
		rate.setRating(row.getRating());
		return convertView;
	}
	
	private class ViewHolder {
		
		private View base;
		private RatingBar rate;
		private TextView label;
		
		public ViewHolder(View base) {
			this.base = base;
		}

		public RatingBar getRate() {
			if(rate == null) {
				rate = (RatingBar) base.findViewById(R.id.rate);
			}
			return rate;
		}

		public TextView getLabel() {
			if(label == null) {
				label = (TextView) base.findViewById(R.id.label);
			}
			return label;
		}
		
	}

}
