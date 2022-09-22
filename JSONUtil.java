package top.mnsx.sks.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import top.mnsx.sks.result.ResultMap;

import java.util.Map;

/**
 * @BelongsProject: background_management_system
 * @BelongsPackage: top.mnsx.background_management_system.utils
 * @CreateTime: 2022/9/10
 * @Description: FASTJSON工具类
 */
public class JSONUtil {
    /**
     * 将前端传入的空字符串转换为null，并且将json字符串转换为map类型
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Map<String, String> EmptyStringHandler(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, String> map = jsonObject.getObject("json", Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals("")) {
                entry.setValue(null);
            }
        }
        return map;
    }

    /**
     * @BelongsProject: background_management_system
     * @BelongsPackage: top.mnsx.background_management_system.constant
     * @CreateTime: 2022/9/10
     * @Description: 将json处理为map类型
     */
    public static Map<String, String> jsonToMap(String json) {
        return EmptyStringHandler(json);
    }

    /**
     * @BelongsProject: background_management_system
     * @BelongsPackage: top.mnsx.background_management_system.constant
     * @CreateTime: 2022/9/10
     * @Description: 将ResultMap相应返回类型转换为JSON字符串
     */
    public static String mapToJson(ResultMap result) {
        return JSON.toJSONString(result);
    }

    public static Integer getIntegerData(Map<String, String> input, String key) {
        return input.get(key) == null ? null : Integer.parseInt(input.get(key));
    }

    public static Long getLongData(Map<String, String> input, String key) {
        return input.get(key) == null ? null : Long.parseLong(input.get(key));
    }

    public static Double getDoubleData(Map<String, String> input, String key) {
        return input.get(key) == null ? null : Double.parseDouble(input.get(key));
    }
}
