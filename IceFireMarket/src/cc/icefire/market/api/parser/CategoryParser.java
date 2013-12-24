package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cc.icefire.market.BuildConfig;
import cc.icefire.market.api.response.CategoryResponse;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.Category;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * 解析分类列表接口响应，获得一个Category对象的列表。服务器返回的数据格式应当为：
 * [
    {
        "id": 10,
        "parent_id": 1,
        "name": "RPG",
        "icon_url": "www.xxx.com",
        "app_or_game": 0,
        "desp": "RPG is waiting for you here, come on!"
    },
    {
        "id": 10,
        "parent_id": 1,
        "name": "RPG",
        "icon_url": "www.xxx.com",
        "app_or_game": 0,
        "desp": "RPG is waiting for you here, come on!"
    },
    {
        "id": 10,
        "parent_id": 1,
        "name": "RPG",
        "icon_url": "www.xxx.com",
        "app_or_game": 0,
        "desp": "RPG is waiting for you here, come on!"
    },
    {
        "id": 10,
        "parent_id": 1,
        "name": "RPG",
        "icon_url": "www.xxx.com",
        "app_or_game": 0,
        "desp": "RPG is waiting for you here, come on!"
    }
]
 */
public class CategoryParser extends JsonParseHandler<CategoryResponse> {

	private CategoryResponse category = new CategoryResponse();
	
	@Override
	public CategoryResponse getModel() {
		return category;
	}

	@Override
	public void parse(String inputSource) {
		if(BuildConfig.DEBUG) {
			List<Category> categories = new ArrayList<Category>();
			Category c1 = new Category(0, 0, "SocialApp", "http://market.icefire.com/social.png", AppOrGame.APP.ordinal(), "it's all for you");
			Category c2 = new Category(0, 0, "SocialApp", "http://market.icefire.com/social.png", AppOrGame.APP.ordinal(), "it's all for you");
			Category c3 = new Category(0, 0, "SocialApp", "http://market.icefire.com/social.png", AppOrGame.APP.ordinal(), "it's all for you");
			Category c4 = new Category(0, 0, "SocialApp", "http://market.icefire.com/social.png", AppOrGame.APP.ordinal(), "it's all for you");
			Category c5 = new Category(0, 0, "SocialApp", "http://market.icefire.com/social.png", AppOrGame.APP.ordinal(), "it's all for you");
			Category c6 = new Category(0, 0, "SocialApp", "http://market.icefire.com/social.png", AppOrGame.APP.ordinal(), "it's all for you");
			categories.add(c1);
			categories.add(c2);
			categories.add(c3);
			categories.add(c4);
			categories.add(c5);
			categories.add(c6);
			category.setCategories(categories);
			category.setSuccess(true);
		} else {
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

}
