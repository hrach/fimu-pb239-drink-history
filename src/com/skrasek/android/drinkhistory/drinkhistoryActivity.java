package com.skrasek.android.drinkhistory;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.skrasek.android.drinkhistory.R;
import com.skrasek.android.drinkhistory.db.DatabaseHelper;
import com.skrasek.android.drinkhistory.db.LocalDBUtils;
import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;
import com.skrasek.android.drinkhistory.db.entity.Pubs;
import com.skrasek.android.drinkhistory.db.entity.Visits;
import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class drinkhistoryActivity extends BaseActivity {
    /** Called when the activity is first created. */
	BaseActivity ac;
	private static String ROOT = "com.skrasek.android.drinkhistory";
	public final int SHOW_VISIT = 89987;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ac=this;
        UpgradeSettings();


        

        try {
        	initConnection();
        	ListView visitsList = (ListView) findViewById(R.id.visitsTable);
        	visitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					ListView list = (ListView) parent;
					Visits visit = (Visits) list.getAdapter().getItem(position);

					Intent i = new Intent(ac, VisitActivity.class);
					i.putExtra("visitId", visit.getVisitId());
					startActivityForResult(i, 12345);

				}
			});

        	initData();
        	
        } catch (Exception e) {
        	Toast.makeText(ac, "Neco nevyslo!", Toast.LENGTH_SHORT);
        }
    }

    public void initData()
    {
    	ListView visitsList = (ListView) findViewById(R.id.visitsTable);
    	List<Visits> visits;
		try {
			visits = visitsDao.queryBuilder().orderBy("visitId", false).query();
			visitsList.setAdapter(new VisitsAdapter(visits, ac));
		} catch (SQLException e) {
			Toast.makeText(ac, "Nepodarilo se najit navstevy!", Toast.LENGTH_SHORT);
		}
    }
    
    
    //kdyz se v manifestu zvysi verze(android:versionCode) tak se dropne a znovu vytvori databaze...
	private void UpgradeSettings() {
		// TODO Auto-generated method stub
		PackageInfo pInfo = null; 
        try 
        { 
        	pInfo = getPackageManager().getPackageInfo(ROOT, PackageManager.GET_META_DATA); 
        
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if(!prefs.contains("Version"))
        {	
        	prefs.edit().putInt("Version", pInfo.versionCode).commit();
        	LocalDBUtils.get().dbRestoreLocalDB(this.getApplicationContext());
        } else if (prefs.getInt("Version", pInfo.versionCode)<pInfo.versionCode) 
        {
        	prefs.edit().putInt("Version", pInfo.versionCode).commit();
        	LocalDBUtils.get().dbRestoreLocalDB(this.getApplicationContext());
        }
        } 
        catch (NameNotFoundException e)  
        {  
        }
	}


	public void newVisitButtonClick(View v)
	{
		Intent i = new Intent(this, VisitActivity.class);
		startActivityForResult(i, SHOW_VISIT);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		initData();
	}

	
	private void prepareSelect(QueryBuilder<DrinkTypes, Integer> qb,Boolean param) {
		// TODO Auto-generated method stub
		
		try {
			Where where=qb.where();
			//type je nazev sloupce pojmenovanyho v entite
			where.eq("type", "basic");
			/*
			 // dalsi podminka
			if (param){
				where.and().eq("","" );
			}
			
			//kdyz chci nacist jen nektery sloupce (select) tak:
			qb.selectColumns("name","type",.......);
			
			*/
			
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}