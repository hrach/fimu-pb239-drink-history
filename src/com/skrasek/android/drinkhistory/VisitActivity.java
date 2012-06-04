package com.skrasek.android.drinkhistory;

import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.skrasek.android.drinkhistory.db.DatabaseHelper;
import com.skrasek.android.drinkhistory.db.entity.Drinks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VisitActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			
			setContentView(R.layout.visit);
			Bundle extras = getIntent().getExtras();
			
			
			try {
				initConnection();
				
				if (extras.containsKey("visitId")) {
					int visitId = extras.getInt("visitId");
					QueryBuilder<Drinks, Integer> builder = drinksDao.queryBuilder();
					Where<Drinks, Integer> where = builder.where();
					where.eq("visitId", visitId);

					List<Drinks> entries = builder.query();

					ListView list = (ListView) findViewById(R.id.drink_list);
					
					
				}

			} catch (Exception e) {
				Toast.makeText(this, "Neco se posralo!", Toast.LENGTH_LONG).show();
			}
	}
	
}
