package me.longerian.abc.jsontest;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest {

	public static void main(String[] args) throws JSONException {
		System.out.println("JSONObject.NULL = " + JSONObject.NULL);
		System.out.println("JSONObject.NULL is JSONObject ? " + (JSONObject.NULL instanceof JSONObject));
		JSONObject test = new JSONObject("{ \"test\": null  }");
		Object testValue = test.opt("test");
		System.out.println(testValue);
		System.out.println(testValue == JSONObject.NULL);
	}

}
