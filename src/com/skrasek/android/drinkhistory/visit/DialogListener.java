package com.skrasek.android.drinkhistory.visit;

import java.sql.SQLException;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.skrasek.android.drinkhistory.R;
import com.skrasek.android.drinkhistory.VisitActivity;
import com.skrasek.android.drinkhistory.db.entity.Drinks;

public class DialogListener implements View.OnLongClickListener {

	Drinks drink;
	Dao<Drinks, Integer> drinksDao;
	VisitActivity activity;

	public DialogListener(VisitActivity activity, Dao<Drinks, Integer> drinksDao, Drinks drink) {
		this.activity = activity;
		this.drink = drink;
		this.drinksDao = drinksDao;
	}

	public boolean onLongClick(View v) {

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
		}


		setOkButton(dialog);
		setDeleteButton(dialog);
		dialog.show();
	    dialog.getWindow().setAttributes(lp);

		return true;
	}
	
	private void setDeleteButton(Dialog dialog)
	{
		Button buttonDelete = (Button) dialog.findViewById(R.id.deleteButton);
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// delete drink
				final Dialog deleteDialog = new Dialog(v.getContext());
				deleteDialog.setContentView(R.layout.deletedialog);
				deleteDialog.setTitle("");
				
				// set the Yes button
				Button buttonYes = (Button) deleteDialog.findViewById(R.id.yesButton);
				buttonYes.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						try {
							drinksDao.delete(drink);
						} catch (SQLException e) {
							Toast.makeText(activity, "Neco se posralo v DB!",Toast.LENGTH_LONG).show();
						}
						deleteDialog.dismiss();
					}
				});
				
				// set the No button
				Button buttonNo = (Button) deleteDialog.findViewById(R.id.noButton);
				buttonNo.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						deleteDialog.dismiss();
						
					}
				});				
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

			}
		});
	}

}