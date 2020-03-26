package com.enjoy.traffic.util;

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
		new Thread(wf).start();
		//过车
		DataSaveGCMongodb gc = new DataSaveGCMongodb();
		new Thread(gc).start();
	}
}
