package com.enjoy.traffic.redisManger;

import java.util.ArrayList;
import java.util.List;


import com.enjoy.traffic.deal.Common;
//@Component
//@Scope("singleton")
public class RedisFactory {
	private RedisUtil r1;
	private static List<RedisUtil> list=new ArrayList<RedisUtil>();
	public RedisFactory() {
		//读取配置文件
    	String num=(String) Common.getProperties().get(Common.RedisCount);
		for(int i=0;i<Integer.parseInt(num);i++){
			list.add(new RedisUtil());
		}
	}
	public static List<RedisUtil> getRedis(){
		return list;
	}
	public static RedisUtil createRedis(){
		RedisUtil r = new RedisUtil();
		return r;
	}
}
