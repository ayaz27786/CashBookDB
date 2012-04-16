package io.appstud.android.cashbook.activities;

import java.util.ArrayList;


import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.R.id;
import io.appstud.android.cashbook.helpers.CashBookDataSource;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.EntryTagAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class DetailByTag extends ListActivity {
	private CashBookDataSource cashBookDataSource;
	private EntryTagAdapter entryTagAdapter;
	ArrayList<Entry> entries = new ArrayList<Entry>();
  
	private ListView tagList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_by_tag);
		tagList = (ListView) findViewById(R.id.detailtag);
		
		cashBookDataSource = new CashBookDataSource(this);
		cashBookDataSource.open();
		entries = (ArrayList<Entry>) cashBookDataSource.getAllEntries();
		 // entryTagAdapter = new EntryTagAdapter(this, R.layout.budget_entry, entries);
		  tagList.setAdapter(entryTagAdapter);
	}
	
	
}
