package com.dusto.mobile.common.sap.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.err.println(System.getProperty("java.library.path"));
		try {
			//ping2("122.228.147.82");
			System.err.println(ping("10.0.2.69"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  10000 ;  //超时应该在3钞以上        
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }
	public static void ping2(String ipAddress) throws Exception {
		String delay = new String();
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("ping -c 4 " + "10.0.2.69");
			BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = new String();
			while ((str = buf.readLine()) != null) {
				System.out.println(str);
				if (str.contains("avg")) {
					int i = str.indexOf("/", 20);
					int j = str.indexOf(".", i);
					System.out.println("延迟:" + str.substring(i + 1, j));
					delay = str.substring(i + 1, j);
				}
			}
//			if (delay.equals("")) {
//				EventBusUtils.post(new NetEvent((long) 1000));
//			} else {
//				EventBusUtils.post(new NetEvent(Long.parseLong(delay)));
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
