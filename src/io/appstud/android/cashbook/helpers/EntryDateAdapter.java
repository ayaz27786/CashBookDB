package io.appstud.android.cashbook.helpers;

import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.activities.CashBookActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryDateAdapter extends ArrayAdapter<Entry> {
	private ArrayList<Entry> entries = new ArrayList<Entry>();
<<<<<<< HEAD
	ArrayList<Entry> filterList = new ArrayList<Entry>();
=======

>>>>>>> 21facc0e4066b49870c762d305848768827050e4
	public EntryDateAdapter(Context context, int textViewResourceId,
			ArrayList<Entry> entries) {
		super(context, textViewResourceId, entries);
		this.entries = entries;
	}

	public void addItem(Entry entry) {
		entries.add(entry);
		notifyDataSetChanged();
	}

	public int getCount() {
		return entries.size();
	}

	public Entry getItem(int position) {
		return entries.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		Entry entry = entries.get(position);
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
<<<<<<< HEAD
			if (CashBookActivity.flag == 1) {
				v = vi.inflate(R.layout.debit_credit_entry_row, null);
			}

			else if (CashBookActivity.flag == 2) {
=======
			if (CashBookActivity.flage == 1) {
				v = vi.inflate(R.layout.debit_credit_entry_row, null);
			}

			else if (CashBookActivity.flage == 2) {
>>>>>>> 21facc0e4066b49870c762d305848768827050e4
				v = vi.inflate(R.layout.debit_credit_entry_row, null);
			}

			if (entry != null) {
				NumberFormat nf = NumberFormat.getInstance();
<<<<<<< HEAD
		        TextView tvDescription = (TextView) v.findViewById(R.id.description_show);
=======
		
>>>>>>> 21facc0e4066b49870c762d305848768827050e4
				TextView tvDate = (TextView) v.findViewById(R.id.row_date);
				TextView tvAmt = (TextView) v.findViewById(R.id.row_amount);
				

				if (entry.getFlag().equals("Credit"))
					v.setBackgroundResource(android.R.color.transparent);
				else
					v.setBackgroundResource(android.R.color.transparent);
				
				if (tvDate != null) {
					tvDate.setText(entry.getDate());
				}
				if (tvAmt != null) {

					tvAmt.setText(nf.format(Float.parseFloat(entry.getAmount())));

				}

			}
		}
		return v;

	}
}
