package io.appstud.android.cashbook.helpers;

import io.appstud.android.cashbook.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryTagAdapter extends ArrayAdapter<Tag> {
	private ArrayList<Tag> tags = new ArrayList<Tag>();

	public EntryTagAdapter(Context context, int textViewResourceId,
			List<Tag> tags) {
		super(context, textViewResourceId, tags);
		this.tags = (ArrayList<Tag>) tags;

	}

	public void addItem(Tag tag) {
		tags.add(tag);
		notifyDataSetChanged();
	}

	public int getCount() {
		return tags.size();
	}

	public Tag getItem(int position) {
		return tags.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	float ft = 0;

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		float am = 0;
		Tag tag = tags.get(position);
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);

			v = vi.inflate(R.layout.budget_entry, null);
		}

		if (tag != null) {

			NumberFormat nf = NumberFormat.getInstance();
			TextView tvAmt = (TextView) v.findViewById(R.id.row_amount);
			TextView tvTag = (TextView) v.findViewById(R.id.row_tag);

			if (tvTag != null) {
				tvTag.setText(tag.getTag());
				
				

				/*if (tvAmt != null) {

				//tvAmt.setText(nf.format(Float.parseFloat(tag.getAmount())));

					// tvtotal.setText(nf.format(ft));

				}*/

			}

		}
		return v;
	}
}