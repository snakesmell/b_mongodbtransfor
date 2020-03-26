package com.enjoy.traffic.test;

import java.util.Calendar;

import com.enjoy.traffic.util.Common;

public class test2 {
public static void main(String[] args) {
//	String dt=Common.getFormatDate("\"yyyy-MM-dd HH:mm:ss\"", Calendar.getInstance());
//	System.out.println(dt);
	for(int i=400000;i<5000000;i++) {
		int code=i/210000;
		int m=i/625000;
		String time1="2019-10-29 15:27:21";
		if(code>9){
			time1="2019-10-30 "+code+":27:21";
		}else{
			time1="2019-10-30 0"+code+":27:21";
		}
		System.out.println(time1);
	}
}
}
