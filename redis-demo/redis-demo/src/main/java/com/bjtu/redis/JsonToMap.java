package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;


public class JsonToMap {
    JsonToStr js=new JsonToStr();
    private static HashMap<String,HashMap> actions=new HashMap<>();
    private static HashMap<String,HashMap> counters=new HashMap<>();

    public HashMap<String,HashMap> getActions(){
        return actions;
    }
    public HashMap<String,HashMap> getCounters(){
        return counters;
    }

    public JsonToMap(){
        ActionsToMap();
        CountersToMap();
    }


    public void ActionsToMap(){
        if(actions!=null){
            actions.clear();
        }
        String s = js.JsonToString("src/main/resources/Actions.json");
        JSONObject jsonobj = JSON.parseObject(s);//字符串转为json对象
        JSONArray array = jsonobj.getJSONArray("actions");//通过json对象构建JSONArray数组
        for (int i = 0; i < array.size(); i++) {//获取大数组中每一个json元素
            JSONObject elem = (JSONObject) array.get(i);
            HashMap<String, String> action = new HashMap<>();//每一个小action
            String name = (String) elem.get("name");
            action.put("name", name);
            String describe = (String) elem.get("describe");
            action.put("describe", describe);
            JSONArray a = elem.getJSONArray("operation");//数组
            if (a != null) {//有的没有operation，只是显示内容
                for (int n = 0; n < a.size(); n++) {
                    JSONObject o = (JSONObject) a.get(n);
                    String operation = (String) o.get("counterName");
                    action.put("operation", operation);
                }
            }
            actions.put(name, action);
        }
    }
    public void CountersToMap(){
        if(counters!=null){
            counters.clear();
        }
        String s = js.JsonToString("src/main/resources/Counters.json");
        JSONObject jsonobj = JSON.parseObject(s);//字符串转为json对象
        JSONArray array = jsonobj.getJSONArray("counters");//通过json对象构建JSONArray数组
        for (int i = 0; i < array.size(); i++) {//获取大数组中每一个json元素
            JSONObject elem = (JSONObject) array.get(i);
            HashMap<String, String> counter = new HashMap<>();

            String counterName = (String) elem.get("counterName");
            counter.put("counterName", counterName);

            String counterIndex= (String) elem.get("counterIndex");
            counter.put("counterIndex", counterIndex);

            String type = (String) elem.get("type");
            counter.put("type", type);

            String keyFields = (String) elem.get("keyFields");
            counter.put("keyFields", keyFields);

            String valueFields = (String) elem.get("valueFields");
            if(valueFields!=null){
                counter.put("valueFields", valueFields);
            }

            String fields = (String) elem.get("fields");
            if(fields!=null){
                counter.put("fields", fields);
            }
            counters.put(counterName, counter);
        }
    }
}
