package com.skrasek.android.drinkhistory.db;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.skrasek.android.drinkhistory.R;
import com.skrasek.android.drinkhistory.utils.FileUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class LocalDBUtils
{ 
	
	private static LocalDBUtils singleton=null;
	
	
	
	
	public static LocalDBUtils get() {
		if (singleton==null){
			singleton=new LocalDBUtils();
		} 
		
		return singleton;
	}

	private String DBname="LocalDB.db";
	
	private int DBres=R.raw.drinkhistory1;
	
	public int getDBres() {
		return DBres;
	}


	public void setDBres(int dBres) {
		DBres = dBres;
	}


	public String getDBname() {
		return DBname;
	}


	public void setDBname(String dBname) {
		DBname = dBname;
	}

	
		
	
	public  ArrayList<SelectRow> dbLocalSelect(Context AppContext,String Query)
	{
		
		Comparator<SelectRow> comperator = new Comparator<SelectRow>() {
			public int compare(SelectRow object1, SelectRow object2) {
			return object1.GetText().compareToIgnoreCase(object2.GetText());
			}
			};
			ArrayList<SelectRow> ret = new ArrayList<SelectRow>();
			
			Cursor c=null;
			SQLiteDatabase database = null;
		try
		{
			 database = dbOpenDatabase(AppContext);
			c = database.rawQuery(Query, null);
			while(c.moveToNext())
			{
				SelectRow row = new SelectRow();
				try{
				row.SetID(c.getInt(0));
				if (c.getString(1)==null)
					throw new NullPointerException();
				row.SetText(c.getString(1));
				
				ret.add(row);
				}catch (NullPointerException e){
					row.SetText("");
					ret.add(row);
				}
			}
			Collections.sort(ret, comperator);
			
		}
		catch(Exception e)
		{
			return null;
		} finally{
			try{
			c.close();
			} catch(Exception e){}
			database.close();
			
		}
		return ret;
	}
	
	public  SQLiteDatabase dbOpenDatabase(Context AppContext)
	{
		if(!new File(AppContext.getDatabasePath(getDBname()).getAbsolutePath()).exists())
		{
			return null;
		}
		else
		{
			return AppContext.openOrCreateDatabase(getDBname(), 0, null);
		}
	}

	public  boolean dbRestoreLocalDB(Context AppContext)
	{
		File db = new File(AppContext.getDatabasePath(getDBname()).getAbsolutePath());
		
		if(db.exists())
		{
			db.delete();
		}
		SQLiteDatabase dab = AppContext.openOrCreateDatabase(getDBname(), 0, null);
		dab.close();
		
		try {
			FileUtils.copyFile(AppContext,getDBres(), db);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public  void dbBackupLocalDB(Context AppContext)
	{
		File db = new File(AppContext.getDatabasePath(getDBname()).getAbsolutePath());
		File sd =  new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getDBname());
		if(!sd.exists())
		{
			try {
				sd.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileUtils.copyFile(db, sd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void dbCleanTable(Context AppContext,String Table)
	{
		SQLiteDatabase db = dbOpenDatabase(AppContext);
		db.execSQL("Delete from " + Table + ";");
	}
	

}
