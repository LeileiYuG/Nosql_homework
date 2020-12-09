package com.bjtu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisInstance {
    //私有化构造函数
    private JedisInstance(){ }

    //定义一个静态枚举类
    static enum SingletonEnum{
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private JedisPool jedisPool;
        private SingletonEnum(){
        //私有化枚举的构造函数
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(30);
            config.setMaxIdle(10);

            jedisPool = new JedisPool(config, "127.0.0.1", 6379);
        }
        public JedisPool getInstnce(){
            return jedisPool;
        }
    }
 
    //对外暴露一个获取User对象的静态方法
    public static JedisPool getInstance(){
        return SingletonEnum.INSTANCE.getInstnce();
    }
}

