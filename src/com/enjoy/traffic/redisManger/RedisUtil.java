package com.enjoy.traffic.redisManger;
import org.apache.log4j.Logger;
import com.enjoy.traffic.deal.Common;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	
	public static void main(String[] args) {
		RedisUtil ru = new RedisUtil();
		Jedis redis = ru.getJedis();
		
		
		
//		String name=redis.get("name");
//		System.out.println(name);
		
//		for(int i=0;i<1000000;i++){
//			redis.lpush("ru", String.valueOf(i));
//		}
		
//		String temp ;
//		while((temp = redis.lpop("ru"))!=null){
//			System.out.println(temp);
//		}
	}
	
	//服务器IP地址
//    private static String ADDR = "localhost";
    //端口
//    private static int PORT = 6379;
    //密码
    //private static String AUTH = "123456";
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT = 10000;
    //连接超时的时间
    private static int TIMEOUT = 10000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;
    private static Jedis jedis=null;
    
	public RedisUtil() {
		try {
            //读取配置文件
        	String add=(String) Common.getProperties().get(Common.RedisAddress);
            int port=Integer.parseInt((String)Common.getProperties().get(Common.RedisPort)) ;
            //redis配置
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
//          jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            jedisPool = new JedisPool(config, add, port, TIMEOUT);
            jedis = jedisPool.getResource();
            System.out.println("Redis Init Success...");
        } catch (Exception e) {
        	System.out.println("Redis Init Fial...");
            e.printStackTrace();
        }
		Logger.getLogger(this.getClass()).info("redis create");
	}
    
    /**
     * 初始化Redis连接池
     */
//    static {
//        try {
//            //读取配置文件
//        	String add=(String) Common.getProperties().get(Common.RedisAddress);
//            int port=Integer.parseInt((String)Common.getProperties().get(Common.RedisPort)) ;
//            //redis配置
//            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxTotal(MAX_ACTIVE);
//            config.setMaxIdle(MAX_IDLE);
//            config.setMaxWaitMillis(MAX_WAIT);
//            config.setTestOnBorrow(TEST_ON_BORROW);
////          jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
//            jedisPool = new JedisPool(config, add, port, TIMEOUT);
//            jedis = jedisPool.getResource();
//            System.out.println("Redis Init Success...");
//        } catch (Exception e) {
//        	System.out.println("Redis Init Fial...");
//            e.printStackTrace();
//        }
//    }
    
    
    /**
     * 获取Jedis实例
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /***
     * 
     * 释放资源
     */
    public static void returnResource(final Jedis jedis) {
            if(jedis != null) {
                jedisPool.returnResource(jedis);
            }
    }
    /**
     * 获取初始化的jedis
     * @return
     */
    public static Jedis getInitJedis(){
    	return jedis;
    }
    
    public static synchronized boolean push(String key,String json){
    	getInitJedis().lpush(key, json);
    	return Common.True;
    }
}