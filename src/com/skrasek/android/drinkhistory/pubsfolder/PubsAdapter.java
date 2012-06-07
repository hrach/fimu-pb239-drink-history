package com.skrasek.android.drinkhistory.pubsfolder;

import java.util.List;

import com.skrasek.android.drinkhistory.R;
import com.skrasek.android.drinkhistory.db.entity.Pubs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class PubsAdapter extends BaseAdapter {

	private List<Pubs> data;
	private Activity ac;

	public PubsAdapter(List<Pubs> data, Activity ac) {
		this.data = data;
		this.ac = ac;
	}

	public Object getItem(int position) {
		return this.data.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          	v = vi.inflate(R.layout.selectpubrow, null);
        }

        ac.registerForContextMenu(v);

        Pubs item = data.get(position);
        
		TextView tv = (TextView) v.findViewById(R.id.pubNamee);
		tv.setText(item.getName());

		tv = (TextView) v.findViewById(R.id.gps);
		tv.setText("GPS:" + item.getLat() + ";" + item.getLon());

        return v;
	}

	public int getCount() {
		return this.data.size();
	}

}
