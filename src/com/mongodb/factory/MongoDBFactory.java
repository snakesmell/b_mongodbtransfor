package com.mongodb.factory;

import javax.annotation.PostConstruct;

import org.bson.Document;

import com.enjoy.traffic.util.Common;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBFactory {
	private static String DB_Add="localhost";
	private static int Port=27017;
	private static String DB_Name="mycol";
	private static MongoDatabase mongoDatabase;
	private static MongoClient mongoClient;
	
	@PostConstruct
	public void init() {
		  this.DB_Add=Common.getProperties().getProperty("DB_Add");
		  this.Port=Integer.parseInt(Common.getProperties().getProperty("Port"));
		  this.DB_Name=Common.getProperties().getProperty("DB_Name");
	      // 连接到 mongodb 服务
	      mongoClient = new MongoClient(DB_Add,Port);
	      // 连接到数据库
	      mongoDatabase = mongoClient.getDatabase(DB_Name);  
	      System.out.println("Connect to database successfully");
	}
	
	/**
	 * 创建集合
	 * @param name
	 */
	public static void createCollection(String name) {
		   try{   
			      mongoDatabase.createCollection(name);
//			      mongoDatabase.createCollection("test");
			      System.out.println("集合"+name+"创建成功");
		   }catch(Exception e){
		        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		   }
	}
	 /**
	  * 获取集合，如果不存在则创建。
	  * @param name
	  */
	 public static MongoCollection<Document> getCollection(String name) {
		   try{   
		       System.out.println("集合"+name+"获取成功");
		       return mongoDatabase.getCollection(name);
		      }catch(Exception e){
		        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		        return null;
		     }
	 }
	 /**
	  * 查询所有数据
	  */
	 public static void queryAll(String name) {
		   try{   
		         MongoCollection<Document> collection = mongoDatabase.getCollection(name);
		         System.out.println("集合 test 选择成功");
		         
		         //检索所有文档  
		         /** 
		         * 1. 获取迭代器FindIterable<Document> 
		         * 2. 获取游标MongoCursor<Document> 
		         * 3. 通过游标遍历检索出的文档集合 
		         * */  
		         FindIterable<Document> findIterable = collection.find();  
		         MongoCursor<Document> mongoCursor = findIterable.iterator();  
		         while(mongoCursor.hasNext()){  
		            System.out.println(mongoCursor.next());  
		         }  
		      
		      }catch(Exception e){
		         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      }
	  }
}
