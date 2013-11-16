package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.List;

import cc.icefire.market.api.response.CategoryResponse;
import cc.icefire.market.model.Category;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class CategoryParser extends JsonParseHandler<CategoryResponse> {

	private CategoryResponse category = new CategoryResponse();
	
	@Override
	public CategoryResponse getModel() {
		return category;
	}

	@Override
	public void parse(String inputSource) {
		try {
			Type type = new TypeToken<List<Category>>(){}.getType();
			List<Category> categories = JsonUtil.fromJsonArray(inputSource, type);
			category.setCategories(categories);
			category.setSuccess(true);
		} catch (JsonSyntaxException e) {
			category.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			category.setSuccess(false);
			e.printStackTrace();
		}
	}

}
