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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VisitActivity extends BaseGPSActivity {
	
	public final int SELECT_PUB=12123121;
	public final int ADD_DRINK_CODE = 11;

	int visitId;
	
	private VisitActivity ac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.visit);
			
			Bundle extras = getIntent().getExtras();
			if (extras.containsKey("visitId")) {
				visitId = (int) extras.getLong("visitId");
			} else {
				// todo create new visit
			}
			
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
				initData();
			} catch (Exception ex) {
				
			}
	}
	
	public void initData()
	{
			TableLayout drinkList = (TableLayout) findViewById(R.id.drinksTable);
			drinkList.removeAllViews();
			LayoutInflater inflater = this.getLayoutInflater();

			try {
				QueryBuilder<Drinks, Integer> builder = drinksDao.queryBuilder();
				Where<Drinks, Integer> where = builder.where();
				where.eq("visitId", visitId);

				List<Drinks> drinks = builder.query();
				TableRow row;

				Float finalPrice = new Float(0);
				boolean showPrice = true;

				for (final Drinks drink : drinks) {
	        		row = (TableRow) inflater.inflate(R.layout.visitrow, drinkList, false);

	        		((TextView) row.findViewById(R.id.drinkName)).setText(drink.getName());

	        		List<Entries> entries = entriesDao.queryForEq("drinkId", drink.getDrinkId());
	        		int count = entries.size();

	        		((TextView) row.findViewById(R.id.drinkCount)).setText("" + count);

	        		row.setOnLongClickListener(new DialogListener(this, drinksDao, entriesDao, drink));
	        		row.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							
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

	        		
	        		// calculate final price
	        		if (drink.getPrice() == 0) {
	        			showPrice = false;
	        		} else {
	        			finalPrice += drink.getPrice() * count;
	        		}


	        		drinkList.addView(row);

	        	}

				TextView finalPriceView = (TextView) findViewById(R.id.finalprice);
				if (showPrice) {
					finalPriceView.setText(finalPrice.toString());
				} else {
					//finalPriceView.setText("");
				}

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
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//tady se obslouzi navrat z aktivity dle resultCode

		Bundle extras=data.getExtras();
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {
			case SELECT_PUB:
				if (extras!=null){
					if (extras.containsKey("ID")){
						setPub(Integer.valueOf(extras.get("ID").toString()));
					}

				}
			break;
			case ADD_DRINK_CODE:
				if (data.hasExtra("drinkTypeId")) {
					
					try {
						DrinkTypes drink = drinkTypesDao.queryForId(data.getExtras().getInt("drinkTypeId"));
						
						Drinks d = new Drinks();
						d.setName(drink.getName());
						d.setVisitId(this.visitId);
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
		// TODO Auto-generated method stub
	((TextView)findViewById(R.id.visitpubname)).setTag(id);
	try {
		initConnection();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
}
