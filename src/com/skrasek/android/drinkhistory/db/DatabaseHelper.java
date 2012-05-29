package com.skrasek.android.drinkhistory.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "LocalDB.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	
	private Dao<Drinks, Integer> drinksDao = null;
	private Dao<DrinkTypes, Integer> drinkTypesDao = null;
	private Dao<Entries, Integer> entriesDao = null;
	private Dao<Pubs, Integer> pubsDao = null;
	private Dao<Visits, Integer> visitsDao = null;
	
	private static DatabaseHelper databaseHelper = null;
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		/*
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			
			//TableUtils.dropTable(connectionSource, Requests1.class, true);
			//TableUtils.createTable(connectionSource, Requests1.class);

			// here we try inserting data in the on-create as a test
			
			Dao<Requests1, Integer> dao = getRequests();
			long millis = System.currentTimeMillis();
			// create some entries in the onCreate
			Requests1 simple = new Requests1();
			
			dao.create(simple);
			simple = new Requests1();
			dao.create(simple);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		*/
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		/*
		try {
			Log.i(DatabaseHelperA.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Requests.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelperA.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}*/
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	
	
	
	
	public Dao<Drinks, Integer> getDrinksDao() throws SQLException {
		if (drinksDao == null) {
			drinksDao = getDao(Drinks.class);
		}
		return drinksDao;
	}

	public Dao<DrinkTypes, Integer> getDrinkTypesDao() throws SQLException {
		if (drinkTypesDao == null) {
			drinkTypesDao = getDao(DrinkTypes.class);
		}
		return drinkTypesDao;
	}

	public Dao<Entries, Integer> getEntriesDao() throws SQLException {
		if (entriesDao == null) {
			entriesDao = getDao(Entries.class);
		}
		return entriesDao;
	}

	public Dao<Pubs, Integer> getPubsDao() throws SQLException {
		if (pubsDao == null) {
			pubsDao = getDao(Pubs.class);
		}
		return pubsDao;
	}

	public Dao<Visits, Integer> getVisitsDao() throws SQLException {
		if (visitsDao == null) {
			visitsDao = getDao(Visits.class);
		}
		return visitsDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		drinksDao = null;
		drinkTypesDao = null;
		entriesDao = null;
		pubsDao = null;
		visitsDao = null;
		
	}

	
	public static DatabaseHelper getHelper(Context ac) {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(ac, DatabaseHelper.class);
		} 
		return databaseHelper;
	}
	
}

