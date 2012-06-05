package com.skrasek.android.drinkhistory;


import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.skrasek.android.drinkhistory.db.DatabaseHelper;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VisitActivity extends BaseActivity {

	String VISIT_ID_PARAM = "visitId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			
			setContentView(R.layout.visit);
			Bundle extras = getIntent().getExtras();

			TableLayout drinkList = (TableLayout) findViewById(R.id.drinksTable);
			LayoutInflater inflater = this.getLayoutInflater();

			try {
				initConnection();
				
				if (extras.containsKey("visitId")) {
					long visitId = extras.getLong("visitId");
					QueryBuilder<Drinks, Integer> builder = drinksDao.queryBuilder();
					Where<Drinks, Integer> where = builder.where();
					where.eq("visitId", visitId);

					List<Drinks> drinks = builder.query();
					TableRow row;

					for (final Drinks drink : drinks) {
		        		row = (TableRow) inflater.inflate(R.layout.visitrow, drinkList, false);

		        		((TextView) row.findViewById(R.id.drinkName)).setText(drink.getName());

		        		List<Entries> entries = entriesDao.queryForEq("drinkId", drink.getDrinkId());
		        		int count = entries.size();

		        		((TextView) row.findViewById(R.id.drinkCount)).setText(count);

		        		row.setOnLongClickListener(new View.OnLongClickListener() {
							public boolean onLongClick(View v) {
								// zde spustit zobrazeni dialogu
								// drink.getDrinkId();

								return false;
							}
						});
		        		row.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {

								
								
							}
						});

		        		drinkList.addView(row);
		        	}

					
				}

			} catch (Exception e) {
				Toast.makeText(this, "Neco se posralo!", Toast.LENGTH_LONG).show();
			}
	}
	
}
