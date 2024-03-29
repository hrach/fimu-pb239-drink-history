package com.skrasek.android.drinkhistory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.skrasek.android.drinkhistory.db.LocalDBUtils;
import com.skrasek.android.drinkhistory.db.SelectRow;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
				
			
			ListView pubsTable = (ListView) findViewById(R.id.selectPubTable);
			pubsTable.setAdapter(new PubsAdapter(pubs, this));
        	pubsTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					ListView list = (ListView) parent;
					Pubs pub = (Pubs) list.getAdapter().getItem(position);

					Intent resultIntent = new Intent();
					resultIntent.putExtra("ID", (int) pub.getPubId());
					ac.setResult(Activity.RESULT_OK, resultIntent);
					ac.finish();
					return;
				}
        	});
        	/*pubsTable.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					//ListView list = (ListView) arg0;
					//Pubs pub = (Pubs) list.getAdapter().getItem(arg2);

					ac.openContextMenu(arg1);
					
					return true;
				}
			});*/
        	this.registerForContextMenu(pubsTable);

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
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    //info.position
	    
	    ListView view = (ListView) v;
	    Pubs pub = (Pubs) view.getAdapter().getItem(info.position);
	    
		clickedItemId = String.valueOf(pub.getPubId());

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
		
		ListView view = (ListView) findViewById(R.id.selectPubTable);
		Pubs pub = (Pubs) view.getAdapter().getItem(0); 
		i.putExtra("lat", pub.getLat() );
		i.putExtra("lon", pub.getLon() );
		 
		startActivityForResult(i, SELECT_ON_MAP);
		
	}
	
}
