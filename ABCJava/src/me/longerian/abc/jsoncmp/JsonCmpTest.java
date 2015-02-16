package me.longerian.abc.jsoncmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huifeng.hxl on 2014/10/25.
 */
public class JsonCmpTest {

    public static void main(String[] args) throws JSONException {
        JSONObject objectA = new JSONObject();
        objectA.put("A", "a");
        objectA.put("B", "b");
        objectA.put("int", new JSONArray());

        JSONObject objectB = new JSONObject();
        objectB.put("int", new JSONArray());
        objectB.put("B", "b");
        objectB.put("A", "a");

        System.out.println("objectA = " + objectA.toString());
        System.out.println("objectB = " + objectB.toString());

    }

}
