package com.skrasek.android.drinkhistory;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Text;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.skrasek.android.drinkhistory.db.DatabaseHelper;
import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;
import com.skrasek.android.drinkhistory.visit.DialogListener;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VisitActivity extends BaseGPSActivity {
	
	public final int SELECT_PUB = 12123121;
	public final int ADD_DRINK_CODE = 11;

	Visits visit;
	
	private VisitActivity ac;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.visit);
			
			ac = this;
			
			Button btn = (Button) findViewById(R.id.newDrinkButton);
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(ac, AddDrinkActivity.class);
					startActivityForResult(i, ADD_DRINK_CODE);
				}
			});

			try {
				initConnection();
			} catch (Exception ex) {
				
			}


			Bundle extras = getIntent().getExtras();
			if (extras != null && extras.containsKey("visitId")) {
				int visitId = (int) extras.getLong("visitId");
				try {
					visit = visitsDao.queryForId(visitId);
				} catch (SQLException e) {
					Toast.makeText(this, "Nepodarilo se najit navstevu", Toast.LENGTH_SHORT).show();
				} 
			} else {
				this.initGPS();

				visit = new Visits();
				visit.setCreatedTime(new Date());
				visit.setLat(0f);
				visit.setLon(0f);

				try {
					visitsDao.create(visit);
				} catch (Exception e) {
					Toast.makeText(this, "Nepodarilo se vytvorit novou navstevu", Toast.LENGTH_SHORT).show();
				}
			}

			initData();
	}

	public void initData()
	{
			try {
				QueryBuilder<Drinks, Integer> builder = drinksDao.queryBuilder();
				Where<Drinks, Integer> where = builder.where();
				where.eq("visitId", (int) visit.getVisitId());

				List<Drinks> drinks = builder.query();

				Float finalPrice = new Float(0);

				for (final Drinks drink : drinks) {
	        		if (drink.getPrice() != 0) {
	        			finalPrice += drink.getPrice() * drink.getEntriesCount(entriesDao);
	        		}
	        	}

				ListView drinkList = (ListView) findViewById(R.id.drinksTable);
				drinkList.setAdapter(new DrinksAdapter(drinks, this));
				drinkList.setOnItemLongClickListener(new DialogListener(this));
				drinkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						ListView list = (ListView) parent;
						Drinks drink = (Drinks) list.getAdapter().getItem(position);

						Entries entry = new Entries();
						entry.setDrinkId((int) drink.getDrinkId());
						entry.setAddedTime(new Date());

						try {
							ac.entriesDao.create(entry);
							ac.initData();
						} catch (SQLException e) {
							Toast.makeText(ac, "Nepodarilo se pridat napoj", Toast.LENGTH_LONG).show();
						}
					}
				});


				TextView finalPriceView = (TextView) findViewById(R.id.finalPrice);
				finalPriceView.setText(finalPrice.toString() + getString(R.string.currency_text));

			} catch (Exception e) {
				Toast.makeText(this, "Neco se posralo!", Toast.LENGTH_LONG).show();
			}
	}
	
	
	public void pubSelect(View v){
		Intent i = new Intent(this, PubActivity.class);
		//i.putExtra("visitId", visit.getVisitId());
		startActivityForResult(i, SELECT_PUB);
	}


	@Override
	protected void serveGPS(double d, double e) {
		visit.setLat(Float.valueOf(String.valueOf(d)));
		visit.setLon(Float.valueOf(String.valueOf(e)));

		try {
			visitsDao.update(visit);
		} catch (SQLException e1) {
			Toast.makeText(this, "Nepodarilo se updantout souradnice", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//tady se obslouzi navrat z aktivity dle resultCode


		
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {
			case SELECT_PUB:
				if (data.hasExtra("ID")){
						setPub(Integer.valueOf(data.getExtras().get("ID").toString()));
					

				}
			break;
			case ADD_DRINK_CODE:
				if (data.hasExtra("drinkTypeId")) {
					
					try {
						DrinkTypes drink = drinkTypesDao.queryForId(data.getExtras().getInt("drinkTypeId"));
						
						Drinks d = new Drinks();
						d.setName(drink.getName());
						d.setVisitId((int) visit.getVisitId());
						d.setCreatedTime(new Date());
	
						drinksDao.create(d);
	
						Entries e = new Entries();
						e.setDrinkId((int) d.getDrinkId());
						e.setAddedTime(new Date());
						entriesDao.create(e);
						
						initData();
						
					} catch (Exception ex) {
						
						int i = 0;
					}
				}
				break;

			default:
			break;
		}
		  
		
	}


	private void setPub(Integer id) {

		((TextView) findViewById(R.id.visitpubname)).setTag(id);

		try {
			initConnection();
			Pubs pub = pubsDao.queryForId(id);
			((TextView) findViewById(R.id.visitpubname)).setText(pub.getName());
			// TODO nastavit ve visitu pubid
		} catch (Exception e) {

			e.printStackTrace();
		}

	}	
	
}
