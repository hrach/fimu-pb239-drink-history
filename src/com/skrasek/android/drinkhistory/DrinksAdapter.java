package com.skrasek.android.drinkhistory;

import java.util.List;

import com.skrasek.android.drinkhistory.db.entity.Drinks;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



class DrinksAdapter extends BaseAdapter {

	private List<Drinks> data;
	private BaseActivity ac;

	public DrinksAdapter (List<Drinks> data, BaseActivity ac) {
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
          	v = vi.inflate(R.layout.visitrow, null);
        }

        Drinks drink = data.get(position);
        ((TextView) v.findViewById(R.id.drinkName)).setText(drink.getName());

        TextView count = (TextView) v.findViewById(R.id.drinkCount);
        TextView price = (TextView) v.findViewById(R.id.drinkPrice);
        if (drink.getPrice() != 0) {
        	price.setText( drink.getPrice() + ",-");
        } else {
        	price.setText("");
        }

        count.setText(drink.getEntriesCount(ac.getEntriesDao()) + "×");

        return v;
	}

	public int getCount() {
		return this.data.size();
	}

}