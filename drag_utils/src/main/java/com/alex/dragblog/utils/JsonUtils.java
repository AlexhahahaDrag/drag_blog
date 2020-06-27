package com.alex.dragblog.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *description:  json解析的工具类
 *  gson只能序列化基本数据类型和Date类型，其他数据类型如枚举需要自定义解析器registerTypeAdapter
 * setFieldNamingPolicy 设置序列字段的命名策略(UPPER_CAMEL_CASE,UPPER_CAMEL_CASE_WITH_SPACES,LOWER_CASE_WITH_UNDERSCORES,LOWER_CASE_WITH_DASHES)
 * addDeserializationExclusionStrategy 设置反序列化时字段采用策略ExclusionStrategy，如反序列化时不要某字段，当然可以采用@Expore代替。
 * excludeFieldsWithoutExposeAnnotation 设置没有@Expore则不序列化和反序列化
 * addSerializationExclusionStrategy 设置序列化时字段采用策略，如序列化时不要某字段，当然可以采用@Expore代替。
 * registerTypeAdapter 为某特定对象设置固定的序列和反序列方式，实现JsonSerializer和JsonDeserializer接口
 * setFieldNamingStrategy 设置字段序列和反序列时名称显示，也可以通过@Serializer代替
 * setPrettyPrinting 设置gson转换后的字符串为一个比较好看的字符串
 * setDateFormat 设置默认Date解析时对应的format格式
 *
 *
 * 作者：老柏的博客
 * 链接：https://www.jianshu.com/p/31396863d1aa
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *author:       alex
 *createDate:   2020/6/27 16:25
 *version:      1.0.0
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * description:  把对象转换为json数据
     * author:       alex
     * @param:       obj
     * @return:
     */
    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = null;
        try {
             json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * description:  将Object转换成json
     * author:       alex
     * @param:       obj
     * @return:
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonToMap(json);
    }

    /**
     * description:  把json字符串转换成对象
     * author:       alex
     * @param:       jsonString
     * @param:       clazz
     * @return:
     */
    public static Object jsonToObject(String jsonString, Class<?> clazz) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Object obj = null;
        try {
            obj = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * description:  json转arrayList
     * author:       alex
     * @param:       jsonArray
     * @return:
     */
    public static ArrayList<?> jsonArrayToArrayList(String jsonArray) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        ArrayList<?> list = null;
        try {
            Type listType = new TypeToken<ArrayList<?>>() {}.getType();
            list = gson.fromJson(jsonArray, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * description: jsonArray转ArrayList
     * author:      alex
     * @param:      jsonArray
     * @param :     clazz
     * @return:
     */
    public static ArrayList<?> jsonArrayToArrayList(String jsonArray, Class<?> clazz) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        ArrayList<?> list = null;
        try {
            list = gson.fromJson(jsonArray, (Type) clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * description :把json转换为map类型的数据
     * author :     alex
     * @param :     json
     * @return :
     */
    public static Map<String, Object> jsonToMap(String json) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        Map<String, Object> map = null;
        try {
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            map = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * description :将json转换成Map<String, ?></>
     * author :     alex
     * @param :     json
     * @param :     clazz
     * @return :
     */
    public static Map<String, ?> jsonToMap(String json, Class<?> clazz) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        Map<String, ?> map = null;
        try {
            map = gson.fromJson(json, (Type) clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * description :将map转换成pojo
     * author :     alex
     * @param :     map
     * @param :     beanType
     * @return :
     */
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> beanType) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        JsonElement jsonElement = gson.toJsonTree(map);
        return gson.fromJson(jsonElement, beanType);
    }

    /**
     * description :将json结果集转换成对象
     * author :     alex
     * @param :     jsonData
     * @param :     beanType
     * @return :
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * description :将json数据转换成pojo对象list
     * author :     alex
     * @param :     jsonData
     * @param :     beanType
     * @return :
     */
    public static  <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = null;
        try {
            list = MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * description :将任意pojo转换成map
     * author :     alex
     * @param :     t pojo对象
     * @return :
     */
    public static <T> Map<String, Object> pojoToMap(T t) {
        Map<String, Object> result = new HashMap<>();
        Method[] methods = t.getClass().getMethods();
        try {
            for (Method method : methods) {
                Class<?>[] paramClass = method.getParameterTypes();
                //如果方法带参数，则跳过
                if (paramClass.length > 0)
                    continue;
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    Object value = method.invoke(t);
                    result.put(methodName, value);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

}
