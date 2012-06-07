package com.skrasek.android.drinkhistory;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.j256.ormlite.dao.Dao;
import com.skrasek.android.drinkhistory.db.DatabaseHelper;
import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;
import com.skrasek.android.drinkhistory.pubsfolder.DrawableMapOverlay;

public class PubFromMapActivity extends MapActivity {
	private MapView mapView;
	
	protected Dao<Drinks, Integer> drinksDao = null;
	protected Dao<DrinkTypes, Integer> drinkTypesDao = null;
	protected Dao<Entries, Integer> entriesDao = null;
	protected Dao<Pubs, Integer> pubsDao = null;
	protected Dao<Visits, Integer> visitsDao = null;
	
	MapController mc;
	GeoPoint p;
	DrawableMapOverlay cur;
	List<Overlay> list;
	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.selectonmap);
		MapView mapView = (MapView) findViewById(R.id.selectonmapview);
		Bundle extras = getIntent().getExtras();
	
		 mc = mapView.getController();

	        list = mapView.getOverlays();
	        
	        insertMarks();
	        
	        mc.animateTo(new GeoPoint((int) (extras.getFloat("lat") * 1E6), (int) (extras.getFloat("lon") * 1E6)));
	        
	        mc.setZoom(16);          
	        mapView.invalidate();	
	}
	
	protected void initConnection() throws Exception {
		// TODO Auto-generated method stub

		Activity ac = this;
    	drinksDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getDrinksDao();
    	drinkTypesDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getDrinkTypesDao();
    	entriesDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getEntriesDao();
    	pubsDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getPubsDao();
    	visitsDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getVisitsDao();
    }
	
	private void insertMarks() {
		// TODO Auto-generated method stub
		 try{
        initConnection();
        List<Pubs> pubs = pubsDao.queryForAll();
        for(Pubs pub:pubs){
        	try{
        		 	list.add(new DrawableMapOverlay(this,new GeoPoint((int) (pub.getLat()* 1E6), (int) (pub.getLon() * 1E6)),R.drawable.markerred,(int)pub.getPubId()));
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	}

        }catch (Exception e) {
        	e.printStackTrace();
			// TODO: handle exception
		}
	
	       
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
