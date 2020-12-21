package com.zhihuishu.flume.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.zhihuishu.toolkit.user.UserIdConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: lihua
 * @date: 2020/12/16 13:15
 * @Description:
 */
public class BuriedLogParseUtil {
    private static final Logger logger = LoggerFactory.getLogger(BuriedLogParseUtil.class);

    public static List<String> parse(String jsonLog) {
        List<String> mapList = new ArrayList<>();
        try {
            JSONObject jsonObjectAll = JSON.parseObject(jsonLog);
            String appType = jsonObjectAll.getString("appType");
            JSONArray data = jsonObjectAll.getJSONArray("data");
            Iterator<Object> iterator = data.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = JSONUtil.ParseJSONToMap(iterator.next().toString());
                String uuid = String.valueOf(map.get("uuid"));
                //将uuid转为userId
                Long userId = UserIdConvertor.convert(uuid);
                map.put("userId", userId);
                //将createTime的UTC时间转化为GMT+8时间
                String utcTime=String.valueOf(map.get("createTime"));
                if(utcTime.contains("T")){
                    utcTime=DataUtil.getGMTTime(utcTime);
                }
                map.put("createTime",utcTime);
                //Mysql TableName
                String tableName = appType + "_" + map.get("module") + "_" + map.get("type");
                String fields = "";
                String values = "";
                //将map转化为Sql字符串
                for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
                    if (!mapEntry.getKey().equals("module") && !mapEntry.getKey().equals("type") && !mapEntry.getKey().equals("uuid")) {
                        fields += mapEntry.getKey() + ",";
                        if(null != mapEntry.getValue()) {
                            //不过滤null,会出现 'null'->转为-> int 异常
                            values += "\'" + mapEntry.getValue() + "\',";
                        }else{
                            values +=  mapEntry.getValue()+",";
                        }
                    }
                }
                fields = fields.substring(0, fields.length() - 1);
                values = values.substring(0, values.length() - 1);
                //insert into  weChatApplets_applets_choiceBannerClick()  values();
                mapList.add("insert into " + tableName + "(" + fields + ")" + " values(" + values + ")");
            }
        } catch (Exception e) {
            logger.error("数据解析异常:" + jsonLog);
            throw new JsonParseException(e.getMessage());
        }
        return mapList;
    }


}
