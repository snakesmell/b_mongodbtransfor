package com.enjoy.traffic.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.enjoy.traffic.deal.DataSaveGCMongodb;
import com.enjoy.traffic.deal.DataSaveWFMongodb;

public class ServerStart implements ServletContextListener{
	private final Logger logger= LogManager.getLogger(this.getClass());
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//违法
		DataSaveWFMongodb wf = new DataSaveWFMongodb();
//		new Thread(wf).start();
		//过车
		DataSaveGCMongodb gc = new DataSaveGCMongodb();
//		new Thread(gc).start();
		
		ExecutorService exec_wf = Executors.newCachedThreadPool();// 获得一个线程池
		for(int i=0;i<Integer.parseInt((String) Common.getProperties().get(Common.WfThread));i++){
			try {
				wf = new DataSaveWFMongodb();
				exec_wf.submit(wf);
				logger.info("大华违法线程："+i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ExecutorService exec_gc = Executors.newCachedThreadPool();// 获得一个线程池
		for(int i=0;i<Integer.parseInt((String) Common.getProperties().get(Common.GcThread));i++){
			try {
				gc = new DataSaveGCMongodb();
				exec_gc.submit(gc);
				logger.info("大华过车线程："+i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
