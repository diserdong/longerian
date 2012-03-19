package me.yek.top.demo.api.parser;

import java.util.ArrayList;

import me.yek.top.demo.api.client.TaobaoParser;
import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.CollectItem;
import me.yek.top.demo.api.response.FavoriteSearchResponse;
import me.yek.top.demo.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Longer
 *
 *{
    "favorite_search_response": {
        "collect_items": {
            "collect_item": [
                {
                    "item_numid": 14804804065,
                    "item_owner_nick": "nanazi416",
                    "title": "L296 细致 彩金 钛钢项链 玫瑰金 豌豆锁骨链 不过敏"
                },
                {
                    "item_numid": 9541407955,
                    "item_owner_nick": "寰廷数码专营店",
                    "title": "MOMAX XT531 DEFY MB525 ME525 ME526 MB855 XT883电池+座充套"
                },
                {
                    "item_numid": 8205151460,
                    "item_owner_nick": "晶全521",
                    "title": "韩国原装ex2-207骨传导入耳式体感震动耳机健康防水耳机"
                }
            ]
        },
        "total_results": 3
    }
}
 */
public class FavoriteSearchParser extends TaobaoParser {

	@Override
	public TaobaoResponse parse(String strResponse) {
		FavoriteSearchResponse resp = new FavoriteSearchResponse();
		if(strResponse != null) {
			LogUtil.d(this.getClass().getName(), strResponse);
			try {
				JSONObject jsonResponse = new JSONObject(strResponse).getJSONObject("favorite_search_response");
				JSONObject jsonObjCollectItems = jsonResponse.getJSONObject("collect_items");
				JSONArray jsonArrCollectItems = jsonObjCollectItems.getJSONArray("collect_item");
				ArrayList<CollectItem> collectItems = new ArrayList<CollectItem>();
				int length = jsonArrCollectItems.length();
				for(int i = 0; i < length; i++) {
					JSONObject jsonCollectItem = jsonArrCollectItems.getJSONObject(i);
					CollectItem collectItem = new CollectItem();
					collectItem.setItemNumid(jsonCollectItem.getLong("item_numid"));
					collectItem.setItemOwnerNick(jsonCollectItem.getString("item_owner_nick"));
					collectItem.setTitle(jsonCollectItem.getString("title"));
					collectItems.add(collectItem);
				}
				resp.setCollectItems(collectItems);
				resp.setTotalResults(jsonResponse.getInt("total_results"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return resp;
	}

}
