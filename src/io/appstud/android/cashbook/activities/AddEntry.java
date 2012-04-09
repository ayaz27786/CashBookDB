package io.appstud.android.cashbook.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.CashBookDataSource;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.Tag;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

public class AddEntry extends Activity {

	final static String TAG = "AddEntry";
	private CashBookDataSource entriesDataSource;
	Entry entry = new Entry();
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_entry);

		

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		entriesDataSource = new CashBookDataSource(this);
		entriesDataSource.open();

		setUpView();
	}

	private void setUpView() {
		DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
		datePicker.setMaxDate(System.currentTimeMillis());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			goHome();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.cancel_button:
			goHome();
			break;
		case R.id.save_button:
			goHome();
			saveEntry();

			break;

		default:
			break;
		}
	}

	private void saveEntry() {
		EditText etTag = (EditText) findViewById(R.id.tags);
		String commaSeparated = etTag.getText().toString();

		List<String> tagItems = new ArrayList<String>(
				Arrays.asList(commaSeparated.split(",")));

		EditText etDesc = (EditText) findViewById(R.id.edit_description);
		EditText etAmt = (EditText) findViewById(R.id.edit_amount);
		DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
		String date = datePicker.getDayOfMonth() + "-"
				+ (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
		Switch flagSwitch = (Switch) findViewById(R.id.switch_credit);
		String flag = "Credit";
        
        if (flagSwitch.isChecked())
	         flag = "Debit";
<<<<<<< HEAD
        if(flagSwitch.equals("Credit")){
=======
        if(flagSwitch.equals("Debit")){
>>>>>>> afa7e6e78940714137dcbda9794c92d329086c07
            if (etDesc.getText().toString().length() == 0 || etAmt.getText().toString().length() ==0) {
		      etDesc.setError(this.getString(R.string.description_required));
		         etAmt.setError(this.getString(R.string.amount_required));
	}   else {
		Tag tag;
		List<Tag> tags = new ArrayList<Tag>();
		int size = tagItems.size();
		for (int i = 0; i < size; i++) {
			tag = new Tag();
			tag.setTag(tagItems.get(i));

			tags.add(tag);
		}

		entry.setAmount(etAmt.getText().toString());
		entry.setDate(date);
		entry.setDesciption(etDesc.getText().toString());
<<<<<<< HEAD
		entry.setFlag(flag);
=======
		
		
		//entry.setFlag("Credit");
		entry.setFlag("Debit");
		entry.setFlag(entry.getFlag());
>>>>>>> afa7e6e78940714137dcbda9794c92d329086c07
		entry.setTags(tags);
		entriesDataSource.createEntry(entry);
	}
		goHome();
	}
        else {
        	if (etDesc.getText().toString().length() == 0 || etAmt.getText().toString().length() ==0) {
  		      etDesc.setError(this.getString(R.string.description_required));
  		         etAmt.setError(this.getString(R.string.amount_required));
  	}
        	else{
        		Tag tag;
        		List<Tag> tags = new ArrayList<Tag>();
        		int size = tagItems.size();
        		for (int i = 0; i < size; i++) {
        			tag = new Tag();
        			tag.setTag(tagItems.get(i));

        			tags.add(tag);
        		}

        		entry.setAmount(etAmt.getText().toString());
        		entry.setDate(date);
        		entry.setDesciption(etDesc.getText().toString());
<<<<<<< HEAD
=======
        	
        		
        		
        		//entry.setFlag("Debit");
        		entry.setFlag("Credit");
>>>>>>> afa7e6e78940714137dcbda9794c92d329086c07
        		entry.setFlag(flag);
        		entry.setTags(tags);
        		entriesDataSource.createEntry(entry);
        	}
        	goHome();
        	}
        }

	private void goHome() {
		Intent intent = new Intent(this, CashBookActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	
	
	protected void onResume() {
		entriesDataSource.open();
		super.onResume();
	}

	
<<<<<<< HEAD
//	protected void onPause() {
//		entriesDataSource.close();
//		super.onPause();
//	}
=======
	protected void onPause() {
		entriesDataSource.close();
		super.onPause();
	}
>>>>>>> afa7e6e78940714137dcbda9794c92d329086c07
}
