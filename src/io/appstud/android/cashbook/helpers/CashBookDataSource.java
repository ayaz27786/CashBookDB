package io.appstud.android.cashbook.helpers;

import io.appstud.android.cashbook.activities.CashBookActivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CashBookDataSource {

	private static final String TAG = "CashBookDataSource";
	public static String b[];
	private SQLiteDatabase database;
	private CashBookSQLiteOpenHelper cashBookSQLiteOpenHelper;

	public CashBookDataSource(Context context) {
		cashBookSQLiteOpenHelper = new CashBookSQLiteOpenHelper(context);
	}

	public void open() throws SQLException {
		database = cashBookSQLiteOpenHelper.getWritableDatabase();
	}

	public void close() {
		cashBookSQLiteOpenHelper.close();
	}
	
	

	private long createTag(String tag) {
		ContentValues values = new ContentValues();
		values.put(CashBookSQLiteOpenHelper.COL_TAG, tag);
		long tagId = 0;
		try {
			tagId = database.insertOrThrow(
					CashBookSQLiteOpenHelper.TABLE_NAME_TAGS, null, values);
			Log.d(TAG, "Tag inserted : " + tagId + " - " + tag);
		} catch (SQLiteConstraintException e) {
			Log.e(TAG,
					"Tag cannot be inserted due to constraints issue. Tag already Exists.");
		}
		return tagId;
	}

	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_TAGS, null, null, null,
				null, null, null);
		Log.d(TAG, "No of Tags : " + String.valueOf(cursor.getCount()));
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tag tag = cursorToTag(cursor);
			tags.add(tag);
			cursor.moveToNext();
		}
		return tags;
	}

	private long createEHT(long entryId, long tagId) {
		ContentValues values = new ContentValues();
		values.put(CashBookSQLiteOpenHelper.COL_ENTRY_ID, entryId);
		values.put(CashBookSQLiteOpenHelper.COL_TAG_ID, tagId);
		long ehtId = database
				.insert(CashBookSQLiteOpenHelper.TABLE_NAME_ENTRY_HAS_TAG,
						null, values);
		Log.d(TAG, "Row inserted in EntryHasTags table : " + ehtId + " - "
				+ entryId + " - " + tagId);
		return ehtId;
	}

	private long findTagIdByTag(String tag) {
		// String.valueOf(tag) is mandatory
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_TAGS, null,
				CashBookSQLiteOpenHelper.COL_TAG + " = " + "?",
				new String[] { String.valueOf(tag) }, null, null, null);
		cursor.moveToFirst();
		Tag t = cursorToTag(cursor);
		long tagId = t.getId();
		return tagId;
	}

	private String findTagByTagId(long tagId) {
		// String.valueOf(tag) is mandatory
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_TAGS, null,
				CashBookSQLiteOpenHelper.COL_ID + " = " + "?",
				new String[] { String.valueOf(tagId) }, null, null, null);
		cursor.moveToFirst();
		Tag t = cursorToTag(cursor);
		String tag = t.getTag();
		Log.d(TAG, "Tag : " + t.getId() + " - " + t.getTag());
		return tag;
	}

	public List<Tag> getTagsByEntryId(long entryId) {
		List<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRY_HAS_TAG, null,
				CashBookSQLiteOpenHelper.COL_ENTRY_ID + " = " + "?",
				new String[] { String.valueOf(entryId) }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long tagId = cursor.getLong(2);
			String tag = findTagByTagId(tagId);
			Tag t = new Tag();
			t.setId(tagId);
			t.setTag(tag);
			tags.add(t);
			cursor.moveToNext();
		}
		return tags;
	}

	public List<Tag> getTagsByEntryDate(Date entrydate) {
		List<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRY_HAS_TAG, null,
				CashBookSQLiteOpenHelper.COL_DATE + " = " + "?",
				new String[] { String.valueOf(entrydate) }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long tagId = cursor.getLong(2);
			String tag = findTagByTagId(tagId);
			Tag t = new Tag();
			t.setId(tagId);
			t.setTag(tag);
			tags.add(t);
			cursor.moveToNext();
		}
		return tags;
	}

	
	public long createEntry(Entry entry) {
		ContentValues values = new ContentValues();
		values.put(CashBookSQLiteOpenHelper.COL_AMT, entry.getAmount());
		values.put(CashBookSQLiteOpenHelper.COL_DATE, entry.getDate());
		values.put(CashBookSQLiteOpenHelper.COL_DESC, entry.getDesciption());
		values.put(CashBookSQLiteOpenHelper.COL_FLAG, entry.getFlag());
		

		if (entry.getTags() != null) {
			long entryId = database.insert(
					CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES, null, values);
			Log.d(TAG, "Entry Created with id : " + String.valueOf(entryId));
			for (Tag tag : entry.getTags()) {
				long tagId = createTag(tag.getTag());
				if (tagId < 1) {
					Log.d(TAG, "Tags which already exists : " + tag.getTag());
					tagId = findTagIdByTag(tag.getTag());
				}
				createEHT(entryId, tagId);
			}
			return entry.getId();
		} else
			return -1;
	}

	public List<Entry> getAllEntries() {
		List<Entry> entries = new ArrayList<Entry>();
<<<<<<< HEAD

		if (CashBookActivity.flag == 2) {
			String a[] = { "Debit" };
			b = a;
		} else if (CashBookActivity.flag == 1) {
			String a[] = { "Credit" };
			b = a;
		}

		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES, null,
				CashBookSQLiteOpenHelper.COL_FLAG + " = ? ", b, null, null,
				null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Entry entry = cursorToEntry(cursor);
			Log.d(TAG,
					"Entry Found : " + entry.getId() + " - "
							+ entry.getAmount());
			List<Tag> tags = new ArrayList<Tag>();
			tags = getTagsByEntryId(entry.getId());
			entry.setTags(tags);
			entries.add(entry);
			cursor.moveToNext();
		}
		return entries;
	}

	public List<Entry> getAllDistinctDate() {
		List<Entry> entries = new ArrayList<Entry>();
		if (CashBookActivity.flag == 2) {
			String a[] = { "Debit" };
			b = a;
		} else if (CashBookActivity.flag == 1) {
			String a[] = { "Credit" };
			b = a;
		}

		Cursor cursor;
		String sql = ("select" + CashBookSQLiteOpenHelper.COL_AMT + ","

		+ "distinct" + CashBookSQLiteOpenHelper.COL_DATE + "from"
				+ CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES + "where"
				+ CashBookSQLiteOpenHelper.COL_FLAG + "=" + b);
		cursor = database.rawQuery(sql, null);
=======
		
<<<<<<< HEAD
		if (CashBookActivity.flage == 2) {
			String a[] = { "Debit" };
			b = a;
		} else if(CashBookActivity.flage == 1){
=======
		if (CashBookActivity.flage == 1) {
			String a[] = { "Debit" };
			b = a;
		} else if(CashBookActivity.flage == 2){
>>>>>>> afa7e6e78940714137dcbda9794c92d329086c07
			String a[] = { "Credit" };
			b = a;
		}
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES, null, CashBookSQLiteOpenHelper.COL_FLAG + " = ? ", b,
				null, null, null);
>>>>>>> 21facc0e4066b49870c762d305848768827050e4
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Entry entry = cursorToEntry(cursor);
			Log.d(TAG,
					"Entry Found : " + entry.getId() + " - "
							+ entry.getAmount());
			List<Tag> tags = new ArrayList<Tag>();
			tags = getTagsByEntryId(entry.getId());
			entry.setTags(tags);
			entries.add(entry);
			cursor.moveToNext();
		}
		return entries;
	}

	public List<Entry> getEntriesByDate(Date startDate, Date lastDate) {
		List<Entry> entries = new ArrayList<Entry>();
		Date currentDate = new Date(System.currentTimeMillis());

		lastDate = new Date(112, 02, 03);

		Log.d(TAG, "Current Date : " + lastDate);
		Log.d(TAG, "Last Date : " + lastDate);
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES, null,
				CashBookSQLiteOpenHelper.COL_DATE + " > ? ",
				new String[] { String.valueOf(lastDate) }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Entry entry = cursorToEntry(cursor);
			Log.d(TAG,
					"Date_Entry Found : " + entry.getId() + " - "
							+ entry.getAmount());
			List<Tag> tags = new ArrayList<Tag>();
			entry.setTags(tags);
			entries.add(entry);
			cursor.moveToNext();
		}
		return entries;
	}
	
	public List<Entry> getEntriesByDate(Date startDate, Date lastDate){
		List<Entry> entries = new ArrayList<Entry>();
		Date currentDate = new Date(System.currentTimeMillis());

		 lastDate=new Date(112,02,03);
	
		Log.d(TAG,
				"Current Date : " + lastDate);
		Log.d(TAG,
				"Last Date : " + lastDate);
		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES,null,
				CashBookSQLiteOpenHelper.COL_DATE + " > ? ",
				 new String[] { String.valueOf(lastDate) }, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Entry entry = cursorToEntry(cursor);
			Log.d(TAG,
					"Date_Entry Found : " + entry.getId() + " - "
							+ entry.getAmount());
			List<Tag> tags = new ArrayList<Tag>();
			entry.setTags(tags);
			entries.add(entry);
			cursor.moveToNext();
		}
		return entries;
	}
	
//	public List<Entry> getLast30daysEntries(){
//		List<Entry> entries = new ArrayList<Entry>();
//		Date currentDate = new Date(System.currentTimeMillis());
//		String date=  currentDate.toString();
//		
//		Date lastDate=new Date(112,02,03);
//		String s1=lastDate.toString();
//		Date ld= new Date(00,00,30);
//		String s2=ld.toString();
//	
//		Log.d(TAG,
//				"Current Date : " + date);
//		Log.d(TAG,
//				"Last Date : " + lastDate);
//		Cursor cursor = database.query(
//				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES,null,
//				CashBookSQLiteOpenHelper.COL_DATE + " > ? ",
//				 new String[] { String.valueOf(lastDate) }, null, null, null);
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			Entry entry = cursorToEntry(cursor);
//			Log.d(TAG,
//					"Date_Entry Found : " + entry.getId() + " - "
//							+ entry.getAmount());
//			List<Tag> tags = new ArrayList<Tag>();
//			entry.setTags(tags);
//			entries.add(entry);
//			cursor.moveToNext();
//		}
//		return entries;
//	}

	// public List<Entry> getLast30daysEntries(){
	// List<Entry> entries = new ArrayList<Entry>();
	// Date currentDate = new Date(System.currentTimeMillis());
	// String date= currentDate.toString();
	//
	// Date lastDate=new Date(112,02,03);
	// String s1=lastDate.toString();
	// Date ld= new Date(00,00,30);
	// String s2=ld.toString();
	//
	// Log.d(TAG,
	// "Current Date : " + date);
	// Log.d(TAG,
	// "Last Date : " + lastDate);
	// Cursor cursor = database.query(
	// CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES,null,
	// CashBookSQLiteOpenHelper.COL_DATE + " > ? ",
	// new String[] { String.valueOf(lastDate) }, null, null, null);
	// cursor.moveToFirst();
	// while (!cursor.isAfterLast()) {
	// Entry entry = cursorToEntry(cursor);
	// Log.d(TAG,
	// "Date_Entry Found : " + entry.getId() + " - "
	// + entry.getAmount());
	// List<Tag> tags = new ArrayList<Tag>();
	// entry.setTags(tags);
	// entries.add(entry);
	// cursor.moveToNext();
	// }
	// return entries;
	// }

	public Entry getEntryById(long entryId) {

		Cursor cursor = database.query(
				CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES, null,
				CashBookSQLiteOpenHelper.COL_ID + " = ? ",
				new String[] { String.valueOf(entryId) }, null, null, null);
		cursor.moveToFirst();
		Entry entry = cursorToEntry(cursor);
		List<Tag> tags = new ArrayList<Tag>();
		tags = getTagsByEntryId(entryId);
		entry.setTags(tags);
		return entry;
	}

	public Tag updateTag(long tagId, Tag tag) {
		ContentValues values = new ContentValues();
		values.put(CashBookSQLiteOpenHelper.COL_TAG, tag.getTag());
		database.update(CashBookSQLiteOpenHelper.TABLE_NAME_TAGS, values,
				CashBookSQLiteOpenHelper.COL_ID + " = ?",
				new String[] { String.valueOf(tagId) });
		tag.setTag(findTagByTagId(tagId));
		return tag;
	}

	public void deleteEntry(long entryId) {
		database.delete(CashBookSQLiteOpenHelper.TABLE_NAME_ENTRIES,
				CashBookSQLiteOpenHelper.COL_ID + " = ?",
				new String[] { String.valueOf(entryId) });
		database.delete(CashBookSQLiteOpenHelper.TABLE_NAME_ENTRY_HAS_TAG,
				CashBookSQLiteOpenHelper.COL_ENTRY_ID + " = ?",
				new String[] { String.valueOf(entryId) });
	}

	public long updateEntry(long entryId, Entry entry) {
		deleteEntry(entryId);
		long updatedEntryId = createEntry(entry);
		return updatedEntryId;
	}

	private Tag cursorToTag(Cursor cursor) {
		Tag tag = new Tag();
		tag.setId(cursor.getLong(0));
		tag.setTag(cursor.getString(1));
		return tag;
	}

	private Entry cursorToEntry(Cursor cursor) {
		Entry entry = new Entry();
		entry.setId(cursor.getLong(0));
		entry.setAmount(cursor.getString(1));
		entry.setFlag(cursor.getString(2));
		entry.setDate(cursor.getString(3));
		entry.setDesciption(cursor.getString(4));
		return entry;
	}
}
