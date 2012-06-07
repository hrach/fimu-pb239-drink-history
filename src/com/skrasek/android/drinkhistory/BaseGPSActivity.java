package com.skrasek.android.drinkhistory;

import android.os.Handler;
import android.os.Message;

import com.skrasek.android.drinkhistory.utils.GpsClass;

public abstract class BaseGPSActivity extends BaseActivity{
	
	
	protected void initGPS() {
		// TODO Auto-generated method stub
		final GpsClass gps = GpsClass.getGpsClass();
		 Handler h = new Handler() {
					public void handleMessage(Message msg) {
				if (msg.what==1){
				GPS=gps.GetGPS();
				try{
					serveGPS(GPS[0],GPS[1]);
					gps.removeUpdates();
				}catch (Exception e) {
					// TODO: handle exception
				}
				}
			}
			
		};
		gps.addGPSReq(this, h);
		
	}

	protected void initGPSNoUpdate() {
		// TODO Auto-generated method stub
		final GpsClass gps = GpsClass.getGpsClass();
		 Handler h = new Handler() {
					public void handleMessage(Message msg) {
				if (msg.what==1){
				double[] g = gps.GetGPS();
				try{
					serveGPS(g[0],g[1]);
				}catch (Exception e) {
					// TODO: handle exception
				}
				}
			}
			
		};
		gps.addGPSReq(this, h);
		
	}

	protected abstract void serveGPS(double d, double e) ;

}
