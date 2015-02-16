package me.longerian.abc.abstractstatic;

import java.util.Map;

/**
 * Created by huifeng.hxl on 2014/10/29.
 */
public class ConcreteB extends AbstractBase {

    @Override
    public void fillMap(Map<String, String> map) {
        super.fillMap(map);
        map.put("concreteb", "b");
    }
}
