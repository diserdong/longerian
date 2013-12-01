package cc.icefire.market.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.api.request.CategoryRequest;
import cc.icefire.market.api.response.CategoryResponse;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.Category;
import crow.api.ApiCallback;
import crow.api.ApiException;

public class CategoryListAdapter extends PaginationListAdapter<Category> {

	private AppOrGame type;
	
	public CategoryListAdapter(Context context, AppOrGame type) {
		super(context);
		this.type = type;
	}
	
	@Override
	protected void onNextPageRequested(int page) {
		requestCategory(page, type);
	}

	@Override
	public View getAmazingView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_category, null);
			holder = new ViewHolder();
			holder.categoryIcon = (ImageView) convertView
					.findViewById(R.id.category_icon);
			holder.categoryName = (TextView) convertView
					.findViewById(R.id.category_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Category item = (Category) getItem(position);
		Bitmap icon = IceFireApplication.sharedInstance().getNetImgLoader().load(item.getIconUrl(), holder.categoryIcon);
		if(icon != null) {
			holder.categoryIcon.setImageBitmap(icon);
		}
		holder.categoryName.setText(item.getName());
		return convertView;
	}

	public class ViewHolder {

		private ImageView categoryIcon;
		private TextView categoryName;

	}
	
	public void requestCategory(int page, AppOrGame type) {
		if(page == 1) {
			onRequestFirstPage();
		}
		CategoryRequest request = new CategoryRequest(type);
		IceFireApplication.sharedInstance().getHttpEngine().request(request,
				 new CategoryCallback());
	}

	private class CategoryCallback implements ApiCallback<CategoryResponse> {

		@Override
		public void onSuccess(CategoryResponse response) {
			onRecvDataSuccess();
			addItems(response.getCategories());
			nextPage();
			if(getCount() > 20) {
				notifyNoMorePages();
			} else {
				notifyMayHaveMorePages();
			}
		}

		@Override
		public void onFail(CategoryResponse response) {
			onRecvDataFail();
			Toast.makeText(context, R.string.hint_loading_data_failed, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onException(ApiException e) {
			e.printStackTrace();
			onRecvDataFail();
			Toast.makeText(context, R.string.hint_network_error, Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
