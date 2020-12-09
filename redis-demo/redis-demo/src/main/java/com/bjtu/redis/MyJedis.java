package com.bjtu.redis;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class MyJedis {
    String key="totalCnt";//设置围观人数的Key，value是进入时间
    private Jedis jedis;

    public MyJedis(){
        jedis=JedisInstance.getInstance().getResource();
    }

    public void addUser(String value){
        //获取当前时间
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
        String enterTime =dateFormat.format(date);//value内容
        SimpleDateFormat argformat=new SimpleDateFormat("HHmmss");
        String arg=argformat.format(date);
        int enterarg=Integer.parseInt(arg);//zset第二个参数

        System.out.println("Increase Online User");
        int num=Integer.parseInt(value);//add的数量
        if(jedis.get(key)==null) {
            jedis.set(key,value);
            //存储进入的时间
            jedis.rpush("enterList", enterTime);
            jedis.sadd("enterSet", enterTime);
            jedis.zadd("enterZset",enterarg,enterTime);
        }
        else {
            jedis.incr(key);
            //存储进入的时间
            jedis.rpush("enterList", enterTime);
            jedis.sadd("enterSet", enterTime);
            jedis.zadd("enterZset",enterarg,enterTime);
        }
    }

    public void delUser(String value){
        //获取当前时间
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
        String leaveTime =dateFormat.format(date);//value内容
        SimpleDateFormat argformat=new SimpleDateFormat("HHmmss");
        String arg=argformat.format(date);
        int leavearg=Integer.parseInt(arg);//zset第二个参数

        System.out.println("Decrease online user");
        if(jedis.get(key)==null){
            System.out.println("No User");
        } else if(Integer.parseInt(jedis.get(key))==0){
            System.out.println("The num of online user is 0, can't decrease");
        } else if(Integer.parseInt(jedis.get(key))!=0){
            jedis.decr(key);
            //存储离开的时间
            //list
            jedis.rpush("leaveList", leaveTime);
            //set
            jedis.sadd("leaveSet", leaveTime);
            //zset
            jedis.zadd("leaveZset",leavearg,leaveTime);
        }
    }

    public void showUserCount(){
        if(jedis.get(key)==null){
            System.out.println("No User");
        }
        else{
            System.out.println("The num of online user is "+jedis.get(key));
        }
    }

    public void showUserFreq(String stime){
        String[] re = stime.split(" ");
        String start=re[0];
        String end=re[1];

        List<String> enterlist = jedis.lrange("enterList",0,-1);//进入列表
        if(enterlist.size()==0){
            System.out.println("No users are online yet");
        }
        else{
            long enter1 =Long.valueOf(start), enter2 =Long.valueOf(end);
            int entertotal=0;
            if(enter2<enter1){
                System.out.println("Illegal input format");
            }
            else {
                long elem;
                for(int i=0;i<enterlist.size();i++){
                    elem=Long.valueOf(enterlist.get(i));
                    if(elem>=enter1&&elem<=enter2){
                        entertotal++;
                    }
                }
                System.out.println("In the period："+stime+"    Number of go-online user: "+entertotal);
            }
        }

        List<String> leavelist = jedis.lrange("leaveList",0,-1);//离开列表
        if(leavelist.size()==0){
            System.out.println("No users are offline yet");
        }
        else{
            long leave1 =Long.valueOf(start), leave2 =Long.valueOf(end);
            int leavetotal =0;
            if(leave2 < leave1){
                System.out.println("Illegal input format");
            }
            else {
                long elem;
                for(int i=0;i<leavelist.size();i++){
                    elem=Long.valueOf(leavelist.get(i));
                    if(elem>= leave1 &&elem<= leave2){
                        leavetotal++;
                    }
                }
                System.out.println("In the period："+stime+"    Number of offline user: "+leavetotal);
            }
        }
    }

    //获得并展示对应key的list中的内容
    public void ShowListAll(String listkey){
        List<String> list = jedis.lrange(listkey,0,-1);
        if(list.size()==0){
            System.out.println(listkey+"is null");
        }
        else{
            System.out.println(listkey+": ");
            for(int i=0; i<list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
    }
    //获得并展示对应key的set中的内容
    public void ShowSetAll(String setkey){
        Set<String> set = jedis.smembers(setkey);
        if(set.size()==0){
            System.out.println(setkey+"is null");
        }
        else{
            System.out.println(setkey+": ");
            System.out.println(set);
        }
    }
    //获得并展示对应key的zset中的内容
    public void ShowZsetAll(String zsetkey){
        Set<String> zset = jedis.zrange(zsetkey,0,-1);
        if(zset.size()==0){
            System.out.println(zsetkey+"is null");
        }
        else{
            System.out.println(zsetkey+": ");
            System.out.println(zset);
        }
    }
}
