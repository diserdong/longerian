package cc.icefire.market.api.response;

import java.util.List;

import cc.icefire.market.model.Category;

public class CategoryResponse extends BaseApiResponse {

	private List<Category> categories;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
}
