package com.skrasek.android.drinkhistory;

import java.util.List;

import com.skrasek.android.drinkhistory.db.entity.DrinkTypes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



class DrinkTypesAdapter extends BaseAdapter {

	private List<DrinkTypes> data;
	private Activity ac;

	public DrinkTypesAdapter(List<DrinkTypes> data, Activity ac) {
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
          	v = vi.inflate(R.layout.adddrinkrow, null);
        }

        TextView ll = (TextView) v.findViewById(R.id.addDrinkType);
        ll.setText(data.get(position).getName());
        return v;
	}

	public int getCount() {
		return this.data.size();
	}

}