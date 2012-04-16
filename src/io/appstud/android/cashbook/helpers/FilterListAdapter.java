package io.appstud.android.cashbook.helpers;

import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.activities.CashBookActivity;

import java.text.NumberFormat;
import java.util.ArrayList;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FilterListAdapter extends ArrayAdapter<String>{
	
	ArrayList<String> filterList = new ArrayList<String>();

	public FilterListAdapter(Context context, int textViewResourceId, ArrayList<String> filterList) {
		super(context, textViewResourceId, filterList);
		this.filterList = filterList;
		// TODO Auto-generated constructor stub
	}
	
	
	public void addItem(String listEntry) {
		filterList.add(listEntry);
		notifyDataSetChanged();
	}
	public int getCount() {
		return filterList.size();
	}

	public String getItem(int position) {
		return filterList.get(position);
	}
	public long getItemId(int position) {
		return position;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		String listEntry = filterList.get(position);
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			if (CashBookActivity.flag == 1) {
				v = vi.inflate(R.layout.debit_credit_entry_row, null);
			}

			else if (CashBookActivity.flag == 2) {
				v = vi.inflate(R.layout.debit_credit_entry_row, null);
			}

			if (listEntry != null) {
				
				NumberFormat nf = NumberFormat.getInstance();
				TextView tvDate = (TextView) v.findViewById(R.id.row_date);
				TextView tvAmt = (TextView) v.findViewById(R.id.row_amount);
				
				

				
               if (filterList != null) {
					
					

					tvDate.setText(listEntry);
				

				}
				
           	
//               if (filterList != null) {
//					
//					
//
//					
//					tvAmt.setText(listEntry);
//
//				}
//				
//				
				
				

			}
		}
		return v;

	}
}

