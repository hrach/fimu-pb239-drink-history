package com.skrasek.android.drinkhistory;


import java.util.List;

import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.pubsfolder.PubsAdapter;
import com.skrasek.android.drinkhistory.utils.GpsClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TableLayout;

public class PubActivity extends BaseActivity {

	
	private PubActivity ac;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectpub);
		ac=this;
		Bundle extras= getIntent().getExtras();
		if (GPS==null){
			initGPS();
		}
		initPubs();
		
		
	}

	private void initPubs() {
		// TODO Auto-generated method stub
		try {
			initConnection();
			
			Pubs pub = new Pubs();
			pub.setLat(13.1235244f);
			pub.setLon(14.1235244f);
			pub.setName("Putyka");
			pubsDao.create(pub);
		
		if (GPS==null){
			List<Pubs> pubs = pubsDao.queryForAll();	
			PubsAdapter.get().CreateTable((TableLayout)findViewById(R.id.selectPubTable), pubs);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void serveGPS(double d, double e) {
		// TODO Auto-generated method stub
		initPubs();
		
	}



	
	
}
