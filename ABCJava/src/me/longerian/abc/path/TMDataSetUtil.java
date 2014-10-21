package me.longerian.abc.path;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMDataSetUtil {
	
	public static Object getValue(JSONObject json, ITMDynativePath path){
		if(json != null){
			String key = path.getCurrentPath();
			if(json.has(key)){
				Object subJson = json.opt(key);
				if(path.hasNext()){
					path.gotoNext();
					if(subJson instanceof JSONArray){
						return getArrayValue((JSONArray)subJson, path);
					} else {
						return getValue((JSONObject)subJson, path);
					}
				} else {
					return subJson;
				}
			}
		}
		return null;
	}
	
	public static Object getArrayValue(JSONArray jsonArray, ITMDynativePath path){
		if(null != jsonArray){
			try{
				String key = path.getCurrentPath();
				int index = Integer.parseInt(key);
				Object subJson = jsonArray.get(index);
				if(path.hasNext()){
					path.gotoNext();
					if(subJson instanceof JSONArray){
						return getArrayValue((JSONArray)subJson, path);
					} else {
						return getValue((JSONObject)subJson, path);
					}
				} else {
					return subJson;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void setValue(JSONObject json, ITMDynativePath path, Object data) {
		if(json == null || path == null) {
			return ;
		}
		try {
			String key = path.getCurrentPath();
			if(json.has(key)){
				if(path.hasNext()){
					Object subJson = json.opt(key);
					path.gotoNext();
					if(subJson instanceof JSONArray){
						setArrayValue((JSONArray)subJson, path, data);
					} else {
						setValue((JSONObject)subJson, path, data);
					}
				} else {
					json.put(key, data);
				}
			} else {
				if(path.hasNext()) {
					path.gotoNext();
					String childKey = path.getCurrentPath();
					boolean digit = isDigit(childKey);
					if(digit) {
						JSONArray newSubJson = new JSONArray();
						json.put(key, newSubJson);
						setArrayValue(newSubJson, path, data);
					} else {
						JSONObject newSubJson = new JSONObject();
						json.put(key, newSubJson);
						setValue(newSubJson, path, data);
					}
				} else {
					json.put(key, data);
				}
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void setArrayValue(JSONArray jsonArray, ITMDynativePath path, Object data) {
		if(jsonArray == null || path == null) {
			return ;
		}
		try{
			String key = path.getCurrentPath();
			int index = Integer.parseInt(key);
			if(jsonArray.length() > index) {
				Object subJson = jsonArray.get(index);
				if(path.hasNext()){
					path.gotoNext();
					if(subJson instanceof JSONArray){
						setArrayValue((JSONArray)subJson, path, data);
					} else {
						setValue((JSONObject)subJson, path, data);
					}
				} else {
					jsonArray.put(index, data);
				}
			} else {
				if(path.hasNext()) {
					path.gotoNext();
					String childKey = path.getCurrentPath();
					boolean digit = isDigit(childKey);
					if(digit) {
						JSONArray newSubJson = new JSONArray();
						jsonArray.put(index, newSubJson);
						setArrayValue(newSubJson, path, data);
					} else {
						JSONObject newSubJson = new JSONObject();
						jsonArray.put(index, newSubJson);
						setValue(newSubJson, path, data);
					}
				} else {
					jsonArray.put(index, data);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void iterateJSONValue(JSONObject json, OnVisitNodeCallback callback) {
		if(json == null) {
			return ;
		}
		Iterator<?> keys = json.keys();
		while(keys.hasNext()){
			String key = (String) keys.next();
			Object value = json.opt(key);
			if(value instanceof String) {
				String path = (String) value;
				if(callback != null) {
					callback.onVisitNode(json, key, path);
				}
			} else if(value instanceof JSONObject) {
				JSONObject subJson = (JSONObject) value;
				iterateJSONValue(subJson, callback);
			} else if(value instanceof JSONArray) {
				JSONArray subJsonArray = (JSONArray) value;
				iterateJSONArray(subJsonArray, callback);
			}
		}
	}
	
	public static void iterateJSONArray(JSONArray array, OnVisitNodeCallback callback) {
		if(array == null) {
			return ;
		}
		for(int i = 0, length = array.length(); i < length; i++) {
			Object value = array.opt(i);
			if(value instanceof String) {
				String path = (String) value;
				if(callback != null) {
					callback.onVisitArrayNode(array, i, path);
				}
			} else if(value instanceof JSONObject) {
				JSONObject subJson = (JSONObject) value;
				iterateJSONValue(subJson, callback);
			} else if(value instanceof JSONArray) {
				JSONArray subJsonArray = (JSONArray) value;
				iterateJSONArray(subJsonArray, callback);
			}
		}
	}
	
	public interface OnVisitNodeCallback {
		
		void onVisitNode(JSONObject json, String key, String path);
		void onVisitArrayNode(JSONArray jsonArray, int index, String path);
		
	}
	
	public static boolean isDigit(String str) {
		boolean result = true;
		try {
			Integer.valueOf(str);
		} catch(NumberFormatException e) {
			result = false;
		}
		return result;
	}
	
	public static void main(String[] args) {
		TMDynativePath path = new TMDynativePath("$this.newkey[1].newsubkey.newnewsubkey[0].final");
		path.gotoNext();
		try {
			JSONObject org = new JSONObject("{\"abc\":\"asd\",\"ccc\":\"xxx\"}");
			System.out.println(org);
			System.out.println(TMDataSetUtil.getValue(org, path));
			TMDataSetUtil.setValue(org, path, "new menber");
			System.out.println(org);
			path = new TMDynativePath("$this.newkey[1].newsubkey.newnewsubkey[0].final"); 
			path.gotoNext();
			System.out.println(TMDataSetUtil.getValue(org, path));
			
			org = new JSONObject("{\"array\":{\"subarray\":[1,2,3]}}");
			System.out.println(org);
			JSONArray newArray = new JSONArray("[1,2,5]");
			path = new TMDynativePath("$this.array.subarray");
			path.gotoNext();
			TMDataSetUtil.setValue(org, path, newArray);
			System.out.println(org);
			
			JSONObject params = new JSONObject("{\"v\":\"1.0\",\"timestamp\":123049958,\"array\":[\"string in array\",{\"subkey\":2,\"sk\":\"$this\",\"mobile\":false}],\"data\":{\"key\":\"$this.layout.item_list.bgColor\",\"wangwang\":{\"google\":\"$layotu.djldf.df\",\"array\":[1,4,5,6]}}}");
			System.out.println("org params = " + params.toString());
			iterateJSONValue(params, new OnVisitNodeCallback() {
				
				@Override
				public void onVisitNode(JSONObject json, String key, String path) {
					String value = "testreplace";
					if(path.startsWith("$")) {
						value = "testreplacetestreplacetestreplace";
					}
					try {
						json.put(key, value);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onVisitArrayNode(JSONArray jsonArray, int index,
						String path) {
					String value = "testreplaceINARRAY";
					if(path.startsWith("$")) {
						value = "testreplacetestreplacetestreplaceINARRAY";
					}
					try {
						jsonArray.put(index, value);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			System.out.println("real params = " + params.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
