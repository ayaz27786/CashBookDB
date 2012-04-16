package io.appstud.android.cashbook.activities;

import java.util.ArrayList;
import java.util.List;

import io.appstud.android.cashbook.R;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Setting extends Activity {

	private TextView currencyText, tagText;
	private Spinner spinner;
	private EditText Edittag;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		currencyText = (TextView) findViewById(R.id.currencytext);
		tagText = (TextView) findViewById(R.id.tagtext);
		spinner = (Spinner) findViewById(R.id.spinner1);
		Edittag = (EditText) findViewById(R.id.tagedit);

		addItemOnSpinner();

	}

	private void addItemOnSpinner() {

		List<String> list = new ArrayList<String>();

		list.add("Rupee");
		list.add("Dollor");
		list.add("Dinar");
		list.add("Pound");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);

		spinner.setAdapter(dataAdapter);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_action, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case android.R.id.home:
	// goHome();
	// return true;
	// case R.id.save_entry_menu_button:
	// saveEntry();
	// typeBar = 0;
	// showDialog(typeBar);
	// return true;
	//
	// case R.id.cancel_entry_menu_button:
	// goHome1();
	// return true;
	//
	// }
	// return super.onOptionsItemSelected(item);
	// }
}
