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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class drinkhistoryActivity extends BaseActivity {
    /** Called when the activity is first created. */
	Activity ac;
	private static String ROOT = "com.skrasek.android.drinkhistory";
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ac=this;
        UpgradeSettings();
        //jen pro nazornost jak se s tim zachazi :)


        TableLayout visitsList = (TableLayout) findViewById(R.id.visitsTable);
        LayoutInflater inflater = ac.getLayoutInflater();
        

        try {
        	initConnection();
 
        	Visits vis = new Visits();
        	vis.setCreatedTime(new Date());
        	vis.setLat(12.24f);
        	vis.setLon(12.24f);
        	visitsDao.create(vis);
        	
        	List<Visits> visits = visitsDao.queryForAll();
        	TableRow row = null;
        	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        	for (final Visits visit : visits) {
        		row = (TableRow) inflater.inflate(R.layout.mainrow, visitsList, false);
        		String name = "";
        		name += sdf.format(visit.getCreatedTime());
        		if (visit.getPubId() != 0) {
        			Pubs pub = pubsDao.queryForId(visit.getPubId());
        			name += " - " + pub.getName();
        		}

        		((TextView) row.findViewById(R.id.visitname)).setText(name);
        		row.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						Intent i = new Intent(ac, VisitActivity.class);
						i.putExtra("visitId", visit.getVisitId());
						startActivityForResult(i, 12345);
						
					}
				});
        		visitsList.addView(row);
        	}

        } catch (Exception e) {
        	Toast.makeText(ac, "Jdi do prdele!", Toast.LENGTH_LONG);
        }
        
        /*View visitButton = ac.findViewById(R.id.newVisitButton);
        ((Button) visitButton).performClick();*/
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


	public void newVisitButtonClick(View v){
		showBasicTypes();
	/*
	 //tady budes nejspis chtit prejit k jiny aktivite, to se udela takhle:
	 
	 Intent s = new Intent(drinkhistoryActivity.this, druhaaktivita.class);
		//predani parametru:
		s.putExtra("parametr1", "cau");
		s.putExtra("parametr2", "nazdar");
		//prepnuti aktivity
		startActivityForResult(s, nejakycislo);

	 //ale bacha, kdyz spoustis jinou aktivitu nezapomen, ze musi byt zapsana v manifestu, jinak ti to padne :)
	 
	 
	 */
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//tady se obslouzi navrat z aktivity dle resultCode

		/*
		 
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

				switch (requestCode) {
		case nejakycislo:
			//udelej co je treba
			break;

		default:
			break;
		}
		  
		 */
		
	}
	
	private void showBasicTypes(){
		Runnable r = new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				try {
					initConnection();
					QueryBuilder<DrinkTypes, Integer> qb = drinkTypesDao.queryBuilder();
					
					prepareSelect(qb, true);
					final List<DrinkTypes> list = qb.query();
					/*
					 kdyz chcu vsechny z dany tabulky tak staci napr 
					 drinkTypesDao.queryForAll();
					 query builder toho umi vic, ale na jednoduchy dotazy neni treba...
					 
					 
					 jinac pro zobrazovani kompletnich ciselniku budem vyuzivat asi tridu SelectRow, ale o tom az v pondeli. Zatim si muzes hrat s timhle...
					 */
					ac.runOnUiThread(new Runnable() {
						//visualni prvky se muzou aktualizovat jen na UI threadu
						
						public void run() {
							// TODO Auto-generated method stub
							String text="";
							for(DrinkTypes dt : list){
								text=text+dt.getName()+", ";
							}
							Toast.makeText(ac, text, Toast.LENGTH_LONG).show();
							
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
        r.run();

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



	@Override
	protected void serveGPS(double d, double e) {
		// TODO Auto-generated method stub
		
	}

}