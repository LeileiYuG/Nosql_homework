package com.bjtu.redis;

import java.io.*;

public class JsonToStr {
    //json转化为字符串
    public static String JsonToString(String path){
        String jsonStr = "";
        try{
            File file = new File(path);//打开并读取JSON文件，转化为字符串
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
            int temp = 0;
            StringBuffer buffer = new StringBuffer();
            while ((temp = reader.read()) != -1) {
                buffer.append((char) temp);
            }
            fileReader.close();
            reader.close();
            jsonStr = buffer.toString();
            return jsonStr;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
