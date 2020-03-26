package com.enjoy.traffic.deal;

import java.util.Iterator;
import java.util.Map;

import org.bson.Document;
import org.json.simple.JSONValue;

import com.enjoy.traffic.redisManger.RedisFactory;
import com.enjoy.traffic.redisManger.RedisUtil;
import com.enjoy.traffic.util.Common;
import com.mongodb.client.MongoCollection;
import com.mongodb.factory.MongoDBFactory;

import redis.clients.jedis.Jedis;


public class DataSaveWFMongodb implements Runnable{
	private MongoCollection<Document> col;
	private MongoDBFactory mongoDBFactory;
	private RedisUtil redis;
	private Jedis jed;
	private String MongoDbWFRedisKey;
	public DataSaveWFMongodb() {
		// TODO Auto-generated constructor stub
		redis=RedisFactory.createRedis();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			MongoDbWFRedisKey = Common.getProperties().getProperty(Common.MongoDbWFRedisKey);
			jed=redis.getJedis();
			mongoDBFactory=new MongoDBFactory();
			mongoDBFactory.init();
			String cname = Common.getProperties().getProperty(Common.ILG_INFO);
			col=mongoDBFactory.getCollection(cname);//获取集合
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    while(true) {
	        try {
	        	String json=jed.rpop(MongoDbWFRedisKey);
				if(json==null){
					Thread.sleep(Common.delay());//no data wating...
				}else{
					Map map=(Map)JSONValue.parse(json);
					System.out.println(json);
					//mongodb save
					Document document = new Document();
					Iterator<Object> it=map.keySet().iterator();
					while(it.hasNext()){
						String key=String.valueOf(it.next());
						String value=String.valueOf(map.get(key));
						document.append(key, value);
					}
					col.insertOne(document);
					//mongodb save
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

}
