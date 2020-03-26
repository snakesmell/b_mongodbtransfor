package com.enjoy.traffic.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.enjoy.traffic.deal.DataSaveMongodb;

public class ServerStart implements ServletContextListener{
	private final Logger logger= LogManager.getLogger(this.getClass());
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		DataSaveMongodb dataSaveMongodb = new DataSaveMongodb();
		new Thread(dataSaveMongodb).start();
	}
}
