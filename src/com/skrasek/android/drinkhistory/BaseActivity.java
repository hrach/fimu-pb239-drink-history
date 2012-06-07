package com.skrasek.android.drinkhistory;

import com.j256.ormlite.dao.Dao;
import com.skrasek.android.drinkhistory.db.DatabaseHelper;
import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;
import com.skrasek.android.drinkhistory.utils.GpsClass;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public abstract class BaseActivity extends Activity {

	protected Dao<Drinks, Integer> drinksDao = null;
	protected Dao<DrinkTypes, Integer> drinkTypesDao = null;
	protected Dao<Entries, Integer> entriesDao = null;
	protected Dao<Pubs, Integer> pubsDao = null;
	protected Dao<Visits, Integer> visitsDao = null;
	protected static double[] GPS=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public static double[] getGPS(){
		return GPS;
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
	
	public Dao<Entries, Integer> getEntriesDao()
	{
		return this.entriesDao;
	}
	
	public Dao<Drinks, Integer> getDrinksDao()
	{
		return this.drinksDao;
	}

	public Dao<Pubs, Integer> getPubsDao()
	{
		return this.pubsDao;
	}
}