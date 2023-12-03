package com.example.suitcase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.suitcase.database.ItemCursorWrapper;
import com.example.suitcase.database.SuitCaseBaseHelper;
import com.example.suitcase.database.SuitCaseDbSchema.ItemTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SuitCase {
	private static SuitCase sSuitCase;

	private Context mContext;
	private SQLiteDatabase mDatabase;

	public static SuitCase get(Context context) {
		if (sSuitCase == null) {
			sSuitCase = new SuitCase(context);
		}

		return sSuitCase;
	}

	private SuitCase(Context context) {
		mContext = context.getApplicationContext();
		mDatabase = new SuitCaseBaseHelper(mContext).getWritableDatabase();
	}

	public void addItem(Item item) {
		ContentValues values = getContentValues(item);

		mDatabase.insert(ItemTable.NAME, null, values);
	}

	public boolean delItem(Item item) {
		String uuidString = item.getId().toString();
		ContentValues values = getContentValues(item);

		mDatabase.delete(ItemTable.NAME,
				ItemTable.Cols.UUID + " = ?",
				new String[] { uuidString });
		return true;
	}

	public List<Item> getItems() {
		List<Item> items = new ArrayList<>();
		ItemCursorWrapper cursor = queryCrimes(null, null);

		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				items.add(cursor.getItem());
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}

		return items;
	}

	public Item getItem(UUID id) {
		ItemCursorWrapper cursor = queryCrimes(
				ItemTable.Cols.UUID + " =?",
				new String[] { id.toString() }
		);

		try {
			if (cursor.getCount() == 0) {
				return null;
			}

			cursor.moveToFirst();
			return  cursor.getItem();
		} finally {
			cursor.close();;
		}
	}

	public File getPhotoFile(Item item) {
		File filesDir = mContext.getFilesDir();
		return new File(filesDir, item.getPhotoFilename());
	}
	public void updateItem(Item item) {
		String uuidString = item.getId().toString();
		ContentValues values = getContentValues(item);

		mDatabase.update(ItemTable.NAME, values,
				ItemTable.Cols.UUID + " = ?",
				new String[] { uuidString });
	}

	private ItemCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
		Cursor cursor = mDatabase.query(
				ItemTable.NAME,
				null, // Columns - null selects all columns
				whereClause,
				whereArgs,
				null, // groupBy
				null, // having
				null  // orderBy
		);
		return new ItemCursorWrapper(cursor);
	}

	private static ContentValues getContentValues(Item item) {
		ContentValues values = new ContentValues();
		values.put(ItemTable.Cols.UUID, item.getId().toString());
		values.put(ItemTable.Cols.TITLE, item.getTitle());
		values.put(ItemTable.Cols.PRICE, item.getPrice());
		values.put(ItemTable.Cols.DETAIL, item.getDesc());
		values.put(ItemTable.Cols.SOLVED, item.isSolved() ? 1 : 0);

		return values;
	}

}
