package com.skrasek.android.drinkhistory;

import java.util.List;

import com.skrasek.android.drinkhistory.db.entity.Visits;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



class VisitsAdapter extends BaseAdapter {

	private List<Visits> data;
	private BaseActivity ac;

	public VisitsAdapter (List<Visits> data, BaseActivity ac) {
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
          	v = vi.inflate(R.layout.mainrow, null);
        }

        Visits visit = data.get(position);
        ((TextView) v.findViewById(R.id.visitName)).setText(visit.getShowName(ac.getPubsDao()));

        return v;
	}

	public int getCount() {
		return this.data.size();
	}

}