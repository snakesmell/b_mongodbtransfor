package com.enjoy.traffic.deal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.enjoy.traffic.redisManger.RedisUtil;

public final class Common {
	/**
	 * 车辆号牌号码处理，如果旧系统带有颜色，则去除
	 * @param hphm
	 * @return
	 */
	public static String dealHPHM(String hphm){
		int hphmLen=hphm.length();
		if(hphmLen>7){
			hphm=hphm.substring(1, hphmLen);
		}
		return hphm;
	}
	/**
	 * 违法代码处理，如果旧违法代码则加0矫正
	 * @param wfdm
	 * @return
	 */
	public static String dealWFDM(String wfdm){
		int wfdmLen=wfdm.length();
		if(wfdmLen<5){
			wfdm=wfdm+"0";
		}
		return wfdm;
	}
	public static boolean True=true;
	public static boolean False=false;
	//MQ配置表
	public static String SQL_MQ=" select * from XT_CK_ZC_FW_MQ ";
	//大货车相机
	public static String SQL_DHC=" SELECT cksb.CODE from  (SELECT ID FROM XT_CK_SB_SP_SXJ  where SXJLX=1) sxj LEFT JOIN XT_CK_SB cksb on sxj.id =cksb.id  ";
	////////////////////////////////////REDIS KEY  BEGIN //////////////////////////////////
	//redis list 违法
	public static String KEY_DAHUA_ILLEGAL_REDIS_LIST="DahuaIllegalList";
	//redis list 过车
	public static String KEY_DAHUA_WATER_REDIS_LIST="DahuaWaterList";
	//redis list 视频网过车
	public static String KEY_VIDEO_DAHUA_WATER_REDIS_LIST="VideoDahuaWaterList";
	
	//redis list 违法
	public static String KEY_Hikvison_ILLEGAL_REDIS_LIST="HikvisonIllegalList";
	//redis list 过车
	public static String KEY_Hikvison_WATER_REDIS_LIST="HikvisonWaterList";
	//redis list 视频网过车
	public static String KEY_VIDEO_Hikvison_WATER_REDIS_LIST="VideoHikvisonWaterList";
	
	//发送MQ违法
	public static String Redis_MQ_WF="mq_wf";
	//发送MQ过车
	public static String Redis_MQ_GC="mq_gc";
	//mongodb数据库违法
	public static String Redis_Mongodb_WF="mongodb_wf";
	//mongodb数据库过车
	public static String Redis_Mongodb_GC="mongodb_gc";
	////////////////////////////////////REDIS KEY  END //////////////////////////////////
	public static String getNowDate(){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sim.format(new Date());
	}
	//获取配置文件
	public static Properties getProperties(){
		Properties pro=null;
		try {
			InputStream file = new FileInputStream(Common.class.getClassLoader().getResource("config.properties").getFile());
			 pro = new Properties();
			 pro.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
         return pro;
	}
	public static String RedisAddress="redis_add";
	public static String RedisPort="redis_port";
	public static String RedisCount="redis_count";
}
