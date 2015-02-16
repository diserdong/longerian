package me.longerian.abc.abstractstatic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huifeng.hxl on 2014/10/29.
 */
abstract public class AbstractBase {

    private static Map<String, String> map = new HashMap<String, String>();

    public Map<String, String> getMap() {
        if(map.isEmpty()) {
            fillMap(map);
        }
        return map;
    }

    public void fillMap(Map<String, String> map) {
        map.put("base", "base");
    }

}
