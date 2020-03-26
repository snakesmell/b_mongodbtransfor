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


public class DataSaveGCMongodb implements Runnable{
	private MongoCollection<Document> col;
	private MongoDBFactory mongoDBFactory;
	private RedisUtil redis;
	public DataSaveGCMongodb() {
		// TODO Auto-generated constructor stub
		redis=RedisFactory.createRedis();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			mongoDBFactory=new MongoDBFactory();
			mongoDBFactory.init();
			String cname = Common.getProperties().getProperty(Common.MRS_VEH_SNAP);
			col=mongoDBFactory.getCollection(cname);//获取集合
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    while(true) {
	        try {
	        	String json=redis.getJedis().rpop(Common.MongoDbGCKey);
				if(json==null){
					Thread.sleep(Common.delay());//no data wating...
				}else{
					Map map=(Map)JSONValue.parse(json);
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
