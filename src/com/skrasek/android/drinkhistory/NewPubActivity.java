package com.skrasek.android.drinkhistory;

import com.skrasek.android.drinkhistory.db.entity.Pubs;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NewPubActivity extends BaseGPSActivity {
	private Pubs mPub = null;
	private final int MODE_NEW = 1;
	private final int MODE_UPDATE = 2;
	int mMode = MODE_NEW;
	private double[] localGPS = new double[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpub);
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey("ID")) {
			mMode = MODE_UPDATE;
			setData(Integer.valueOf(extras.get("ID").toString()));
		} else {
			setData();
		}

	}

	@Override
	protected void serveGPS(double d, double e) {
		// TODO Auto-generated method stub
		findViewById(R.id.newPubUpdateGPS).setEnabled(true);
		localGPS[0] = d;
		localGPS[1] = e;
		((TextView) findViewById(R.id.newpublat)).setText(String
				.valueOf(localGPS[0]));
		((TextView) findViewById(R.id.newpublon)).setText(String
				.valueOf(localGPS[1]));

	}

	private void setData(Integer id) {
		try {
			initConnection();
			Pubs mPub = pubsDao.queryForId(id);
			// GPS=new double[2];
			localGPS[0] = mPub.getLat();
			localGPS[1] = mPub.getLon();

			((TextView) findViewById(R.id.newpublat)).setText(String
					.valueOf(localGPS[0]));
			((TextView) findViewById(R.id.newpublon)).setText(String
					.valueOf(localGPS[1]));
			((TextView) findViewById(R.id.pubName)).setText(String.valueOf(mPub
					.getName()));

		} catch (Exception e) {

		}

	}

	private void setData() {
		if (GPS!=null){
		localGPS[0] = GPS[0];
		localGPS[1] = GPS[1];
		((TextView) findViewById(R.id.newpublat)).setText(String
				.valueOf(localGPS[0]));
		((TextView) findViewById(R.id.newpublon)).setText(String
				.valueOf(localGPS[1]));
		} else {
			initGPSNoUpdate();
			
		}
	}

	public void UpdateGPS(View v) {
		v.setEnabled(false);
		initGPSNoUpdate();
	}

	private Pubs getData(Pubs p) {
		p.setLat((float) localGPS[0]);
		p.setLon((float) localGPS[1]);
		p.setName(((TextView) findViewById(R.id.pubName)).getText().toString());
		return p;
	}

	public void savePub(View v) {
		if (!conditionCheck()) {
			try {
				initConnection();

				if (mMode == MODE_NEW) {
					mPub = new Pubs();
					pubsDao.create(mPub);

				} else {
					mPub = getData(mPub);
					pubsDao.update(mPub);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private boolean conditionCheck() {
		// TODO Auto-generated method stub
		if (((TextView) findViewById(R.id.pubName)).getText() == null
				|| ((TextView) findViewById(R.id.pubName)).getText().toString()
						.equals("")) {
			Toast.makeText(this, "Vyplòte prosím název restaurace",
					Toast.LENGTH_LONG).show();
			return false;
		}

		if (((TextView) findViewById(R.id.newpublon)).getText() == null
				|| ((TextView) findViewById(R.id.newpublon)).getText()
						.toString().equals("")
				||

				((TextView) findViewById(R.id.newpublat)).getText() == null
				|| ((TextView) findViewById(R.id.newpublat)).getText()
						.toString().equals("")) {
			Toast.makeText(this, "Vyèkejte prosím na naètení GPS souøadnic",
					Toast.LENGTH_LONG).show();
			
			return false;
		}
		return true;
	}
}
