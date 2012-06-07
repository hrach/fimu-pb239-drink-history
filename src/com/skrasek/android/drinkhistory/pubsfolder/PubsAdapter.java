package com.skrasek.android.drinkhistory.pubsfolder;

import java.util.ArrayList;
import java.util.List;

import com.skrasek.android.drinkhistory.R;
import com.skrasek.android.drinkhistory.db.entity.Pubs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PubsAdapter extends BaseAdapter {
	
	Activity ac;
	static PubsAdapter pa;
	
	
	public static PubsAdapter get() {
		if (pa==null){
			pa=new PubsAdapter();
		}
		return pa;
	}

	private List<Pubs> pubs=new ArrayList<Pubs>();
	private static boolean created=false;
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Pubs getItem(int arg0) {
		// TODO Auto-generated method stub
		return pubs.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return pubs.get(arg0).getPubId();
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
public void CreateTable(final TableLayout tl, List<Pubs> ir ) {
	setCreated(false);	
	setCreated(true);
	ac=(Activity)tl.getContext();
		if (tl.getChildCount()!=0){
		tl.removeViews(0, tl.getChildCount());
		}
		System.gc();
		pubs=ir;
		
		//im.setId(0);
		
		
		   
		for (int i = 0; i < pubs.size(); i++) {
			
			TableRow tr=createNewRow(tl,i);	
			//tr.setVisibility(View.VISIBLE);
			ac.registerForContextMenu(tr);
			tl.addView(tr);
			
		}
		setCreated(true);
	        
		
		
	}
private TableRow createNewRow(final TableLayout tl, int pos) {
	// TODO Auto-generated method stub
	final Pubs item = created? pubs.get(pos):new Pubs();
	System.gc();
	// Inflater inflater=ac;
	LayoutInflater inflater = ac.getLayoutInflater();
	TableRow row=null;
	try{
	row = (TableRow) inflater.inflate(R.layout.pubrow, tl,
			false);
	} catch (Exception e){
		e.printStackTrace();
	}
	TextView tv = (TextView) row.findViewById(R.id.pubNamee);
	tv.setText(item.getName());
	
	tv=(TextView) row.findViewById(R.id.gps);
	tv.setText("GPS:"+item.getLat()+";"+item.getLon());
	row.setTag(item.getPubId());
	row.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent resultIntent = new Intent();
        	resultIntent.putExtra("ID", item.getPubId());
        	ac.setResult(Activity.RESULT_OK, resultIntent);
        	ac.finish();
        	return;
			
			
	
		}
	});
	return row;
	

}

public void setCreated(boolean b) {
	// TODO Auto-generated method stub
	if (!b){
		pubs.clear();
		System.gc();
	}
	
	created=b;
}

}
