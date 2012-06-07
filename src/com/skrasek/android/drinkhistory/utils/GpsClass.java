package com.skrasek.android.drinkhistory.utils;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;


public class GpsClass{
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	private static LocationManager locationManager = null;
	private static LocationListener Lis;
	private static double[] GPS=new double[2];
	private static GpsClass singleton=null;
	private Activity ac;
	
	private static ArrayList<Handler> registeredReq=new ArrayList<Handler>();
	
	
	public static GpsClass getGpsClass(){
		if (singleton==null){
			singleton=new GpsClass();
			
			GPS[0] = 0;
			GPS[1] = 0;
			return singleton;
		} else {
			return singleton;
		}
		
	}

	
	public void addGPSReq(Activity act, Handler h){
		
		registeredReq.add(h);
		
		
		ac=act;
		if (registeredReq.size()==1){
			UpdateGPS();
		}
	}
	
	
    public void StopGPS() {
		locationManager.removeUpdates(Lis);
		Lis = null;
	}

	public double[] GetGPS() {
		return GPS;
	}

	private void createGpsDisabledAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);
		builder.setMessage("GPS i Wi-fi je vypnutá! Pro získání vaši polohy je nutné zapnout alespoò jednu ze zmiòovaných technologií. Chcete ji zapnout?")
				//"GPS je vypnutá! Pro získání vaši polohy je nutné ji zapnout.Chcete ji zapnout?")
				
				.setCancelable(false)
				.setPositiveButton("Zapnout",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								showGpsOptions();
							}
						});
		builder.setNegativeButton("Zrušit",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showGpsOptions() {
		Intent gpsOptionsIntent = new Intent(
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		ac.startActivity(gpsOptionsIntent);
	}

	public void removeUpdates(){
		try{		
		registeredReq.clear();
		
					locationManager.removeUpdates(Lis);
					Lis = null;	
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void UpdateGPS() {
		locationManager = (LocationManager) ac.getSystemService(Context.LOCATION_SERVICE);
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			createGpsDisabledAlert();
		}
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				GPS[0] = (float) location.getLatitude();
				GPS[1] = (float) location.getLongitude();
				
	
				for( Handler h: registeredReq){
					try{
					h.sendEmptyMessage(1);
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					registeredReq.remove(h);
					}
				}
	
		//		
	
			}

		
			public void onProviderDisabled(String provider) {

			}

		
			public void onProviderEnabled(String provider) {

			}

		
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}
		};
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
		try{/*
			locationManager.
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
			} else {
					
			}
			*/
			Criteria c = new Criteria();
			c.setAccuracy(Criteria.ACCURACY_COARSE);
		
			c.setPowerRequirement(Criteria.POWER_LOW);
			locationManager.requestLocationUpdates(locationManager.getBestProvider(c, true), 0,MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
			
		Lis = locationListener;
		} catch(Exception e){
			
			for( Handler h: registeredReq){
				h.sendEmptyMessage(-1);
				
			}
			registeredReq.clear();
			
		}
	}
}