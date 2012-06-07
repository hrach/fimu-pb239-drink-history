package com.skrasek.android.drinkhistory.visit;

import java.sql.SQLException;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.skrasek.android.drinkhistory.R;
import com.skrasek.android.drinkhistory.VisitActivity;
import com.skrasek.android.drinkhistory.db.entity.Drinks;
import com.skrasek.android.drinkhistory.db.entity.Entries;

public class DialogListener implements AdapterView.OnItemLongClickListener {

	private Dao<Drinks, Integer> drinksDao;
	private Dao<Entries, Integer> entriesDao;
	private VisitActivity activity;
	private Drinks drink;

	public DialogListener(VisitActivity activity) {
		this.activity = activity;
		this.drinksDao = activity.getDrinksDao();
		this.entriesDao = activity.getEntriesDao();
	}


	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
		ListView list = (ListView) parent;
		drink = (Drinks) list.getAdapter().getItem(position);

		final Dialog dialog = new Dialog(activity);
		dialog.setContentView(R.layout.editdialog);
		dialog.setTitle(R.string.edit_drink_dialog_name);
		dialog.setCancelable(true);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.FILL_PARENT;
	    
		final TextView nameText = (TextView) dialog.findViewById(R.id.drinkName);
		final TextView priceText = (TextView) dialog.findViewById(R.id.drinkPrice);


		nameText.setText(drink.getName());
		Float price = new Float(drink.getPrice());
		if (price.floatValue() != 0) {
			priceText.setText(price.toString());
		} else {
			priceText.setText("");
		}


		setOkButton(dialog);
		setDeleteButton(dialog);
		dialog.show();
	    dialog.getWindow().setAttributes(lp);

		return true;
	}
	
	private void setDeleteButton(final Dialog dialog)
	{
		Button buttonDelete = (Button) dialog.findViewById(R.id.deleteButton);
		buttonDelete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				Builder alertDialog = new AlertDialog.Builder(activity);
				alertDialog.setTitle(activity.getString(R.string.delete) + " " + drink.getName());
				alertDialog.setMessage(activity.getString(R.string.are_you_sure));
				alertDialog.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface d, int which) {
						d.dismiss();
					}
				});
				alertDialog.setPositiveButton(activity.getString(R.string.deleteBtn), new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface d, int which) {
					   try {
						   List<Entries> entries = entriesDao.queryForEq("drinkId", drink.getDrinkId());
						   for (Entries e : entries) {
							   entriesDao.delete(e);
						   }

						   drinksDao.delete(drink);
						   d.dismiss();
						   dialog.dismiss();
						   activity.initData();
					   } catch (SQLException e) {
						   Toast.makeText(activity, "Neco se posralo v DB!",Toast.LENGTH_LONG).show();
					   }
				   }
				});

				alertDialog.create().show();
			}
		});
	}
	
	private void setOkButton(final Dialog dialog)
	{
		final TextView nameText = (TextView) dialog.findViewById(R.id.drinkName);
		final TextView priceText = (TextView) dialog.findViewById(R.id.drinkPrice);

		Button buttonOK = (Button) dialog.findViewById(R.id.okButton);
		buttonOK.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				boolean ok = true;

				String newName = nameText.getText().toString();
				String newPriceString = priceText.getText().toString();
				float newPrice = 0;

				try {
					if (newPriceString.trim().equals("")) {
						newPrice = 0;
					} else {
						Float newPriceFloat = Float.parseFloat(newPriceString);
						newPrice = newPriceFloat.floatValue();
					}
				} catch (NumberFormatException ex) {
					ok = false;
					Toast.makeText(activity, "Neco se posralo pri parsovani ceny!",	Toast.LENGTH_LONG).show();
				}

				try {

					drink.setName(newName);
					drink.setPrice(newPrice);
					drinksDao.update(drink);

				} catch (SQLException e) {
					Toast.makeText(activity, "Neco se posralo v DB!", Toast.LENGTH_LONG).show();
				}

				dialog.dismiss();
				activity.initData();
			}
		});
	}

}