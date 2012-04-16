package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.R;

import io.appstud.android.cashbook.helpers.CashBookDataSource;
import io.appstud.android.cashbook.helpers.CashBookSQLiteOpenHelper;

import io.appstud.android.cashbook.helpers.EntryTagAdapter;
import io.appstud.android.cashbook.helpers.FilterListAdapter;
import io.appstud.android.cashbook.helpers.ListEntry;

import io.appstud.android.cashbook.helpers.Entry;

import io.appstud.android.cashbook.helpers.EntryDateAdapter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TabActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class CashBookActivity extends TabActivity implements
		OnTabChangeListener {
	private CashBookDataSource cashBookDataSource;
	ArrayList<Entry> entries = new ArrayList<Entry>();
	List<io.appstud.android.cashbook.helpers.Tag> tags = new ArrayList<io.appstud.android.cashbook.helpers.Tag>();
	ArrayList<Entry> entries1 = new ArrayList<Entry>();
	private EntryDateAdapter entryDateAdapter;
	private EntryTagAdapter entryTagAdapter;
	private FilterListAdapter filterListAdapter;
	private static final int DELETE_ID = 1;
	private static final int DETAIL_ID = 2;
	private static final int UPDATE_ID = 0;
	Entry entry = new Entry();
	long entry_id;
	// Tag tag = new Tag();
	private Spinner spinner;
	ListView tagList;
	public static int flag, tag_date;
	float total;
	public static long entry_row_Id;

	private static final String LIST_TAB_DEBIT = "Debit";

	private static final String LIST_TAB_CREDIT = "Credit";
	protected static final List<Tag> ArrayList = null;

	// The two views in our tabbed example
	private ListView listView1;
	private ListView listView2;
	public static Date date;
	Button add_data;

	private TabHost tabHost;
	static String freqSelected;

	public static String tag;
	ListView sp1;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		cashBookDataSource = new CashBookDataSource(this);
		cashBookDataSource.open();

		// setup list view 1
		listView1 = (ListView) findViewById(R.id.list1);
		listView2 = (ListView) findViewById(R.id.list2);

		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec(LIST_TAB_CREDIT)
				.setIndicator(LIST_TAB_CREDIT)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {

						return listView1;
					}
				}));

		tabHost.addTab(tabHost.newTabSpec(LIST_TAB_DEBIT)
				.setIndicator(LIST_TAB_DEBIT)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {

						return listView2;

					}
				}));

	}

	/**
	 * Implement logic here when a tab is selected
	 */
	public void onTabChanged(String tabName) {
		if (tabName.equals(LIST_TAB_CREDIT)) {
			displayCreditData();

		}

		else if (tabName.equals(LIST_TAB_DEBIT)) {
			// displayDebitData();

		}

	}

	private void displayCreditData() {
		ArrayList<String> filterList = (ArrayList<String>) new ArrayList<String>();

		flag = 1;
		entries = (ArrayList<Entry>) cashBookDataSource.getAllEntries();
		int size = entries.size();
		for (int i = 0; i < size; i++) {
			if (!filterList.contains(entries.get(i).getDate())) {

				filterList.add(entries.get(i).getDate());

				// filterList.add(entries.get(i).getAmount());
				// position = entries.get(i).getId();

			}

			// else {
			// int index = filterList.indexOf(entries.get(i).getDate());
			// filterList.set(
			// index + 1,
			// (Integer.valueOf(filterList.get(index + 1)) + Integer
			// .valueOf(entries.get(i).getAmount())) + "");
			//
			// // entry_id = entries.get(i).getId();
			//
			// }

			// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1, filterList);
			filterListAdapter = new FilterListAdapter(this,
					R.layout.debit_credit_entry_row, filterList);
			listView1.setAdapter(filterListAdapter);
			listView1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long entry_id) {

					Toast.makeText(getApplicationContext(),
							"Date has selected with id:  " + (position),
							Toast.LENGTH_SHORT).show();

					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							CashBookActivity.this);
					builder1.setTitle("Tag                Amount")
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
										}
									});

					tagList = new ListView(CashBookActivity.this);

					tags = cashBookDataSource.getTagsByEntryId((position + 1));
					entryTagAdapter = new EntryTagAdapter(
							getApplicationContext(), R.layout.budget_entry,
							tags);
					if (CashBookSQLiteOpenHelper.COL_TAG_ID.contains(String
							.valueOf(position + 1)))
						;
					tagList.setAdapter(entryTagAdapter);

					builder1.setView(tagList);
					final Dialog dialog1 = builder1.create();

					dialog1.show();

					tagList.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long entry_Id) {
							try {
								entry_row_Id = entry_Id;
								Intent i = new Intent(getApplicationContext(),
										AddEntry.class);
								startActivity(i);
							} catch (Exception e) {
								Log.d("CashBook Activity",
										"there is some exception");
								e.printStackTrace();
							}
						}
					});
				}

			});
		}
	}

	// private void displayDebitData() {
	//
	// ArrayList<String> filterList = new ArrayList<String>();
	// flag = 2;
	// entries = (ArrayList<Entry>) cashBookDataSource.getAllDistinctDate();
	//
	// int size = entries.size();
	//
	// for (int i = 0; i < size; i++) { if
	// (!filterList.contains(entries.get(i).getDate())) {
	//
	//
	// filterList.add(entries.get(i).getDate());
	//
	// filterList.add(entries.get(i).getAmount());
	//
	// } else { int index = filterList.indexOf(entries.get(i).getDate());
	// filterList.set( index + 1, (Integer.valueOf(filterList.get(index +
	// 1)) + Integer .valueOf(entries.get(i).getAmount())) + ""); } }
	//
	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	// android.R.layout.simple_list_item_1, filterList);
	//
	// entryDateAdapter = new EntryDateAdapter(this,
	// R.layout.debit_credit_entry_row, entries);
	// listView2.setAdapter(entryDateAdapter);
	// }

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.context_menu, menu);

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long itemID = info.position;

		String date_Date = entries.get(info.position).getDate();
		// entries=cashBookDataSource.getAllTagDateEntry();
		int count = entries1.size();
		for (int i = 0; i < count; i++) {

			menu.add(i, DETAIL_ID, i, entries1.get(i).getDate()
					+ "              " + entries1.get(i).getAmount());
		}
		float total = 0;
		// for(int i=0;i<count;i++){

		// total=total+Float.parseFloat(entries1.get(i).getAmount()) ;
		// total1=total;
	}

	// menu.setHeaderTitle("" +
	// entries.get(info.position).getTag()+"                   "+total1);}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_home_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.time_span:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select Time Stamp").setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			ListView modeList = new ListView(this);
			String[] stringArray = new String[] { "All", "Current Month",
					"Last 30 days", "Custom" };
			ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					stringArray);
			modeList.setAdapter(modeAdapter);

			builder.setView(modeList);
			final Dialog dialog = builder.create();

			dialog.show();
			sp1 = modeList;
			sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// flagg = 2;

					freqSelected = (String) sp1.getSelectedItem();
					Toast.makeText(getApplicationContext(), freqSelected,
							Toast.LENGTH_SHORT).show();
					Log.i("dsfasf", freqSelected);
				}

				public void onNothingSelected(AdapterView<?> parent) {
					Toast.makeText(getApplicationContext(),
							"Please select some value", Toast.LENGTH_SHORT)
							.show();
				}
			});

			return true;

		case R.id.date_tag_span:
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setTitle("Short By").setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});

			ListView modeList1 = new ListView(this);
			String[] stringArray1 = new String[] { "Date", "Tag" };
			ArrayAdapter<String> modeAdapter1 = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					stringArray1);
			modeList1.setAdapter(modeAdapter1);

			builder1.setView(modeList1);
			final Dialog dialog1 = builder1.create();

			dialog1.show();
			sp1 = modeList1;
			sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// flagg = 2;

					freqSelected = (String) sp1.getSelectedItem();
					Toast.makeText(getApplicationContext(), freqSelected,
							Toast.LENGTH_SHORT).show();
					Log.i("dsfasf", freqSelected);
				}

				public void onNothingSelected(AdapterView<?> parent) {
					Toast.makeText(getApplicationContext(),
							"Please select some value", Toast.LENGTH_SHORT)
							.show();
				}
			});

			return true;
		case R.id.add_data:
			Intent intent = new Intent(getApplicationContext(), AddEntry.class);
			startActivity(intent);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

}