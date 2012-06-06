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
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class PubActivity extends BaseGPSActivity {

	public final int NEW_PUB=124354;
	
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
			/*
			Pubs pub = new Pubs();
			pub.setLat(13.1235244f);
			pub.setLon(14.1235244f);
			pub.setName("Putyka");
			pubsDao.create(pub);
		*/
			List<Pubs> pubs = pubsDao.queryForAll();	
			PubsAdapter.get().CreateTable((TableLayout)findViewById(R.id.selectPubTable), pubs);
		
		if (GPS==null){
		//nacist posledni	
		} else {
			//nacist dle gps
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	public void newPub(View v){
		Intent i = new Intent(this, NewPubActivity.class);
		//i.putExtra("ID", );
		startActivityForResult(i, NEW_PUB);
	}

	@Override
	protected void serveGPS(double d, double e) {
		// TODO Auto-generated method stub
		
	}


	
	
}
