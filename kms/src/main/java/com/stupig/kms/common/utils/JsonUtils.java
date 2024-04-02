package com.stupig.kms.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Map;

public class JsonUtils {

    /**
     * Query String to JSONObject
     *
     * @param request
     * @return
     * @throws JSONException
     */
    public static JSONObject convertQueryStringToJsonObject(HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        Map<String, String[]> requestMap = request.getParameterMap();
        if (requestMap != null && !requestMap.isEmpty()) {
            for (String key : requestMap.keySet()) {
                String[] strArr = requestMap.get(key);
                if (key.contains("[")) {
                    String[] keys = key.split("\\[");
                    JSONObject childJsonObject = null;
                    JSONArray childJsonArray = null;

                    for (int i = keys.length - 1; i > 0; i--) {
                        if ("]".equals(keys[i])) {
                            if (childJsonObject == null && childJsonArray == null) {
                                childJsonArray = new JSONArray(strArr);
                            } else if (childJsonObject != null) {
                                childJsonArray = new JSONArray();
                                childJsonArray.put(childJsonObject);
                            } else {
                                JSONArray tempJsonArray = new JSONArray();
                                tempJsonArray.put(childJsonArray);
                                childJsonArray = tempJsonArray;
                            }

                            childJsonObject = null;
                        } else {
                            String tempKey = keys[i].replace("]", "");

                            if (childJsonObject == null && childJsonArray == null) {
                                childJsonObject = new JSONObject();
                                childJsonObject.put(
                                        tempKey,
                                        strArr.length == 0
                                                ? StringUtils.EMPTY
                                                : strArr.length == 1 ? strArr[0] : new JSONArray(strArr)
                                );
                            } else if (childJsonObject != null) {
                                JSONObject tempJsonObject = new JSONObject();
                                tempJsonObject.put(tempKey, childJsonObject);
                                childJsonObject = tempJsonObject;
                            } else {
                                childJsonObject = new JSONObject();
                                childJsonObject.put(tempKey, childJsonArray);
                            }

                            childJsonArray = null;
                        }
                    }

                    jsonObject.put(
                            keys[0],
                            childJsonObject == null ? childJsonArray : childJsonObject
                    );
                } else {
                    jsonObject.put(
                            key,
                            strArr.length == 0
                                    ? StringUtils.EMPTY
                                    : strArr.length == 1 ? strArr[0] : new JSONArray(strArr)
                    );
                }
            }
        }

        return jsonObject;
    }

    /**
     * Query String to JSON
     *
     * @param request
     * @return
     * @throws JSONException
     */
    public static String convertQueryStringToJson(HttpServletRequest request) throws JSONException {
        return convertQueryStringToJsonObject(request).toString();
    }
}
