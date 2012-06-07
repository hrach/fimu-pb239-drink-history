package com.skrasek.android.drinkhistory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.skrasek.android.drinkhistory.db.LocalDBUtils;
import com.skrasek.android.drinkhistory.db.SelectRow;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.pubsfolder.PubsAdapter;
import com.skrasek.android.drinkhistory.utils.GpsClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

public class PubActivity extends BaseGPSActivity {

	public final int NEW_PUB = 124354;
	public final int MODIFY_PUB = 124355;
	public final int SELECT_ON_MAP=123543;
	private PubActivity ac;
	private String clickedItemId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectpub);
		ac = this;
		Bundle extras = getIntent().getExtras();
		if (GPS == null) {
			initGPS();
		}
		initPubs();

	}

	private void initPubs() {
		// TODO Auto-generated method stub
		try {
			initConnection();
			
			List<Pubs> pubs;// = pubsDao.queryForAll();

			if (GPS == null) {
				String arg0;
				//pubs = pubsDao.queryBuilder().selectRaw(arg0);
				pubs=getLastVisitedPubs();
				pubs.addAll(pubsDao.queryForAll());
				// nacist posledni navstivene
				
				
				
			} else {
				// podle GPS nejblizsi
				double fudge = Math.pow(Math.cos(Math.toRadians(GPS[0])), 2);
				// pubs=pubsDao.queryBuilder().orderByRaw("("
				// +"6378.135*ACOS(COS(RADIANS(90-(lat)))*COS(RADIANS(90-("+GPS[0]+")))+SIN(RADIANS(90-(lat)))*SIN(RADIANS(90-("+GPS[0]+")))*COS(RADIANS((lon-"+GPS[1]+"))))"
				// + ") ").query();
				pubs = pubsDao
						.queryBuilder()
						.orderByRaw(
								"(" + "(" + GPS[0] + " - lat) * (" + GPS[0]
										+ " - lat) + (" + GPS[1]
										+ " - lon) * (" + GPS[1] + " - lon) * "
										+ fudge + ") ").query();
				int i = 0;

			}
			if (pubs.size()>0){
				findViewById(R.id.pubByMapButton).setEnabled(true);
			} else {
				findViewById(R.id.pubByMapButton).setEnabled(false);
			}
				
				
			PubsAdapter.get().CreateTable(
					(TableLayout) findViewById(R.id.selectPubTable), pubs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Pubs> getLastVisitedPubs() {
		// TODO Auto-generated method stub
		List<Pubs> resultlist=new ArrayList<Pubs>();
		
		GenericRawResults<String[]> rawResults;
		try {
			rawResults = pubsDao.queryRaw( "select distinct (pubs.*),MAX(visits.visitId) as maximum from pubs left outer join visits on pubs.pubId=visits.pubId  GROUP BY pubs.pubId order by maximum");
		
				// there should be 1 result
				List<String[]> results = rawResults.getResults();
				for (String[] s:results){
					Pubs pub=parsePub(s);
					resultlist.add(pub);
				}
					
				// the results array should have 1 value
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultlist;
	}

	private Pubs parsePub(String[] s) {
		// TODO Auto-generated method stub
		Pubs p=new Pubs();
		p.setPubId(Long.valueOf(s[0]));
		p.setName(s[1]);
		p.setLat(Float.valueOf(s[2]));
		p.setLon(Float.valueOf(s[3]));
		return p;
	}

	public void newPub(View v) {
		Intent i = new Intent(this, NewPubActivity.class);
		// i.putExtra("ID", );
		startActivityForResult(i, NEW_PUB);
	}

	@Override
	protected void serveGPS(double d, double e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		clickedItemId = v.getTag().toString();

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pubitemcontextmenu, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.modifyPub:
			Intent i = new Intent(this, NewPubActivity.class);
			i.putExtra("ID", clickedItemId);

			startActivityForResult(i, MODIFY_PUB);
			return true;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case NEW_PUB:
		case MODIFY_PUB:
			initPubs();
			break;
		case SELECT_ON_MAP:
			Intent resultIntent = new Intent();
        	resultIntent.putExtra("ID", String.valueOf(data.getExtras().get("ID")));
        	ac.setResult(Activity.RESULT_OK, resultIntent);
        	ac.finish();
			break;
		default:
			break;
		}
	}

	public void pubByMap(View v){
		Intent i = new Intent(this,PubFromMapActivity.class);
		
		Pubs pub = PubsAdapter.get().getItem(0);
		i.putExtra("lat", pub.getLat() );
		i.putExtra("lon", pub.getLon() );
		 
		startActivityForResult(i, SELECT_ON_MAP);
		
	}
	
}
