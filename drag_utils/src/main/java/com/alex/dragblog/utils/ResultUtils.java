package com.alex.dragblog.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *description:  返回统一接口
 *author:       alex
 *createDate:   2020/6/27 16:20
 *version:      1.0.0
 */
public class ResultUtils {

    /**
     *description:
     *author:       alex
     *@param:       code
     * @param:
     *@return:
     */
    public static String result(Object code, Object data) {
        Map<Object, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("data", data);
        return JsonUtils.objectToJson(map);
    }
}
