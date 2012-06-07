package com.skrasek.android.drinkhistory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
import com.skrasek.android.drinkhistory.utils.GpsClass;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewPubActivity extends MapActivity {
	private Pubs mPub = null;
	private final int MODE_NEW = 1;
	private final int MODE_UPDATE = 2;
	int mMode = MODE_NEW;
	private double[] localGPS = new double[2];

	MapController mc;
	GeoPoint p;
	DrawableMapOverlay cur;
	List<Overlay> list;
	Activity ac;
	protected Dao<Drinks, Integer> drinksDao = null;
	protected Dao<DrinkTypes, Integer> drinkTypesDao = null;
	protected Dao<Entries, Integer> entriesDao = null;
	protected Dao<Pubs, Integer> pubsDao = null;
	protected Dao<Visits, Integer> visitsDao = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ac=this;
		try{
		setContentView(R.layout.newpub);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey("ID")) {
			mMode = MODE_UPDATE;
			setData(Integer.valueOf(extras.get("ID").toString()));
			((Button)findViewById(R.id.newPubButtonOKpdate)).setText("Update pub");
			
		} else {
			setData();
		}

	}

	protected void serveGPS(double d, double e) {
		// TODO Auto-generated method stub
		findViewById(R.id.newPubUpdateGPS).setEnabled(true);
		localGPS[0] = d;
		localGPS[1] = e;
		((TextView) findViewById(R.id.newpublat)).setText(String
				.valueOf(localGPS[0]));
		((TextView) findViewById(R.id.newpublon)).setText(String
				.valueOf(localGPS[1]));
		
		
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.getOverlays().clear();
		insertMarks();
		setAddress();
		
		
	}

	private void setAddress() {
		Geocoder gcd = new Geocoder(this, Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = gcd.getFromLocation(localGPS[0],localGPS[1],100);
		
		if (addresses.size() > 0 && addresses != null) {
			
			((TextView)findViewById(R.id.newPubAddress)).setText((addresses.get(0).getThoroughfare()==null? addresses.get(0).getFeatureName():addresses.get(0).getThoroughfare()) +"-"+addresses.get(0).getLocality()+"-"+addresses.get(0).getSubAdminArea()+"-"+addresses.get(0).getCountryName());
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setData(Integer id) {
		try {
			initConnection();
			mPub = pubsDao.queryForId(id);
			// GPS=new double[2];
			localGPS[0] = mPub.getLat();
			localGPS[1] = mPub.getLon();

			((TextView) findViewById(R.id.newpublat)).setText(String
					.valueOf(localGPS[0]));
			((TextView) findViewById(R.id.newpublon)).setText(String
					.valueOf(localGPS[1]));
			((TextView) findViewById(R.id.pubName)).setText(String.valueOf(mPub
					.getName()));
			setAddress();
		} catch (Exception e) {

		}

	}

	private void setData() {
		double[] GPS = BaseActivity.getGPS();
		if (GPS != null) {
			localGPS[0] = GPS[0];
			localGPS[1] = GPS[1];
			((TextView) findViewById(R.id.newpublat)).setText(String
					.valueOf(localGPS[0]));
			((TextView) findViewById(R.id.newpublon)).setText(String
					.valueOf(localGPS[1]));
			setAddress();
		} else {
			initGPSNoUpdate();
			findViewById(R.id.newPubStopUpdateGPS).setEnabled(true);
		}
	}

	public void UpdateGPS(View v) {
		v.setEnabled(false);
		initGPSNoUpdate();
		findViewById(R.id.newPubStopUpdateGPS).setEnabled(true);
	}

	private Pubs getData(Pubs p) {
		p.setLat((float) localGPS[0]);
		p.setLon((float) localGPS[1]);
		p.setName(((TextView) findViewById(R.id.pubName)).getText().toString());
		return p;
	}

	public void savePub(View v) {
		if (conditionCheck()) {
			Intent resultIntent = new Intent();
			// resultIntent.putExtra("ID", item.getPubId());
			try {
				initConnection();

				if (mMode == MODE_NEW) {
					mPub = getData(new Pubs());
					pubsDao.create(mPub);

				} else {
					mPub = getData(mPub);
					pubsDao.update(mPub);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setResult(Activity.RESULT_OK, resultIntent);
			this.finish();

		}

	}

	private boolean conditionCheck() {
		// TODO Auto-generated method stub
		if (((TextView) findViewById(R.id.pubName)).getText() == null
				|| ((TextView) findViewById(R.id.pubName)).getText().toString()
						.equals("")) {
			Toast.makeText(this, "Fill in restaurant name please.",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (((TextView) findViewById(R.id.newpublon)).getText() == null
				|| ((TextView) findViewById(R.id.newpublon)).getText()
						.toString().equals("")
				||

				((TextView) findViewById(R.id.newpublat)).getText() == null
				|| ((TextView) findViewById(R.id.newpublat)).getText()
						.toString().equals("")) {
			Toast.makeText(this, "Please wait for GPS coordinates",
					Toast.LENGTH_LONG).show();

			return false;
		} else {
			return true;
		}
	}
	
	
	public void showMap(View v){
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent ev) {
				// TODO Auto-generated method stub
			    int action = ev.getAction();
			    switch (action) {
			    case MotionEvent.ACTION_DOWN:
			        // Disallow ScrollView to intercept touch events.
			        v.getParent().requestDisallowInterceptTouchEvent(true);
			        break;

			    case MotionEvent.ACTION_UP:
			        // Allow ScrollView to intercept touch events.
			        v.getParent().requestDisallowInterceptTouchEvent(false);
			        break;
			    }

			    // Handle MapView's touch events.
			    v.onTouchEvent(ev);
			    return true;
			}
		});
        mapView.setVisibility(View.VISIBLE);
        findViewById(R.id.showOnMap).setVisibility(View.GONE);
        mc = mapView.getController();

        list = mapView.getOverlays();
        
        insertMarks();
        mc.animateTo(p);
        mc.setZoom(16);          
        mapView.invalidate();
	}

	private void insertMarks() {
		// TODO Auto-generated method stub
		 try{
        initConnection();
        List<Pubs> pubs = pubsDao.queryForAll();
        for(Pubs pub:pubs){
        	try{
        		if (mPub==null || mPub.getPubId()!=pub.getPubId())
        	list.add(new DrawableMapOverlay(ac,new GeoPoint((int) (pub.getLat()* 1E6), (int) (pub.getLon() * 1E6)),R.drawable.markerred));
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	}

        }catch (Exception e) {
        	e.printStackTrace();
			// TODO: handle exception
		}
	        p = new GeoPoint((int) (localGPS[0] * 1E6), (int) (localGPS[1] * 1E6));

	        list.add(new DrawableMapOverlay(this,p,R.drawable.markerredact));
	       
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void StopGPS(View v){
		GpsClass.getGpsClass().removeUpdates();
	v.setEnabled(false);
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
	
	protected void initConnection() throws Exception {
		// TODO Auto-generated method stub

		Activity ac = this;
    	drinksDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getDrinksDao();
    	drinkTypesDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getDrinkTypesDao();
    	entriesDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getEntriesDao();
    	pubsDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getPubsDao();
    	visitsDao = DatabaseHelper.getHelper(ac.getApplicationContext()).getVisitsDao();
    }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		GpsClass.getGpsClass().removeUpdates();
	}
}
