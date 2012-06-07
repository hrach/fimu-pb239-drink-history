package com.skrasek.android.drinkhistory;


import java.util.ArrayList;
import java.util.List;

import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

public class AddDrinkActivity extends BaseActivity {

	private AddDrinkActivity ac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ac = this;
		setContentView(R.layout.adddrink);
		
		TabHost mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup();

	    mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Normal").setContent(R.id.drinkNormalTab));
	    mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Panáci").setContent(R.id.drinkShotsTab));

	    mTabHost.setCurrentTab(0);
	    loadDrinks();
	}

	protected void loadDrinks()
	{
		try {
			this.initConnection();

			ListView normalList = (ListView) findViewById(R.id.availableNormalDrinkList);
			ListView shotsLits = (ListView) findViewById(R.id.availableShotsDrinkList);

			List<DrinkTypes> normals = new ArrayList<DrinkTypes>();
			List<DrinkTypes> shots = new ArrayList<DrinkTypes>();
			
			List<DrinkTypes> drinks = drinkTypesDao.queryForAll();
			for (DrinkTypes drink : drinks) {
				if (drink.getType().endsWith("shots")) {
					shots.add(drink);
				} else {
					normals.add(drink);
				}
			}

			final DrinkTypesAdapter normalAdapter = new DrinkTypesAdapter(normals, this);
			final DrinkTypesAdapter shotsAdapter = new DrinkTypesAdapter(shots, this);

			normalList.setAdapter(normalAdapter);
			shotsLits.setAdapter(shotsAdapter);

			normalList.setClickable(true);
			shotsLits.setClickable(true);

			AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					DrinkTypes drink;
					ListView list = (ListView) parent;
					if (list.getId() == R.id.availableNormalDrinkList) {
						drink = (DrinkTypes) normalAdapter.getItem(position);
					} else {
						drink = (DrinkTypes) shotsAdapter.getItem(position);
					}

					ac.finish((int) drink.getDrinkTypeId());
					//Toast.makeText(ac, drink.getName(), Toast.LENGTH_LONG).show();
					
				}
			};

			normalList.setOnItemClickListener(listener);
			shotsLits.setOnItemClickListener(listener);

		} catch (Exception e) {
			Toast.makeText(this, "Neco se zase posralo", Toast.LENGTH_LONG).show();
		}
	}
	
	public void finish(int drinkTypeId)
	{
		Intent data = new Intent();
		data.putExtra("drinkTypeId", drinkTypeId);
		setResult(RESULT_OK, data);
		finish();
	}

	
}
