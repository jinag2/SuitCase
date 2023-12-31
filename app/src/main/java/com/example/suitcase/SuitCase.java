package com.example.suitcase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.suitcase.database.AccountCursorWrapper;
import com.example.suitcase.database.ItemCursorWrapper;
import com.example.suitcase.database.SuitCaseBaseHelper;
import com.example.suitcase.database.SuitCaseDbSchema.AccountTable;
import com.example.suitcase.database.SuitCaseDbSchema.ItemTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SuitCase {
	private static SuitCase sSuitCase;

	private Context mContext;
	private SQLiteDatabase mDatabase;
	private String mLoginName;

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

	public void setLoginName(String name) {
		mLoginName = name;
	}

	public String getLoginName() {
		return mLoginName;
	}

	public void addAccount(Account account) {
		ContentValues values = getAccountContentValues(account);

		mDatabase.insert(AccountTable.NAME, null, values);
	}

	public boolean delAccount(Account account) {
		mDatabase.delete(AccountTable.NAME,
				AccountTable.Cols.NAME + " = ?",
				new String[] { account.getName() });
		return true;
	}

	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<>();
		AccountCursorWrapper cursor = queryAccounts(null, null);

		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				accounts.add(cursor.getAccount());
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}

		return accounts;
	}

	public Account getAccount(String name) {
		AccountCursorWrapper cursor = queryAccounts(
				AccountTable.Cols.NAME + " =?",
				new String[] { name }
		);

		try {
			if (cursor.getCount() == 0) {
				return null;
			}

			cursor.moveToFirst();
			return  cursor.getAccount();
		} finally {
			cursor.close();;
		}
	}

	public void updateAccount(Account account) {
		ContentValues values = getAccountContentValues(account);

		mDatabase.update(AccountTable.NAME, values,
				AccountTable.Cols.NAME + " = ?",
				new String[] { account.getName() });
	}

	private AccountCursorWrapper queryAccounts(String whereClause, String[] whereArgs) {
		Cursor cursor = mDatabase.query(
				AccountTable.NAME,
				null, // Columns - null selects all columns
				whereClause,
				whereArgs,
				null, // groupBy
				null, // having
				null  // orderBy
		);
		return new AccountCursorWrapper(cursor);
	}

	private static ContentValues getAccountContentValues(Account account) {
		ContentValues values = new ContentValues();
		values.put(AccountTable.Cols.NAME, account.getName());
		values.put(AccountTable.Cols.PASSWORD, account.getPassword());

		return values;
	}

	public void addItem(Item item) {
		ContentValues values = getItemContentValues(item);

		mDatabase.insert(ItemTable.NAME, null, values);
	}

	public boolean delItem(Item item) {
		String uuidString = item.getId().toString();

		mDatabase.delete(ItemTable.NAME,
				ItemTable.Cols.UUID + " = ?",
				new String[] { uuidString });
		return true;
	}

	public List<Item> getItems(String username) {
		List<Item> items = new ArrayList<>();
		ItemCursorWrapper cursor = queryItems(null, null);

		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Item item = cursor.getItem();
				if (item.getUserName().equals(username)) {
					items.add(item);
				}
				cursor.moveToNext();
			}
		} finally {
			cursor.close();
		}

		return items;
	}

	public Item getItem(UUID id) {
		ItemCursorWrapper cursor = queryItems(
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
		ContentValues values = getItemContentValues(item);

		mDatabase.update(ItemTable.NAME, values,
				ItemTable.Cols.UUID + " = ?",
				new String[] { uuidString });
	}

	private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
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

	private static ContentValues getItemContentValues(Item item) {
		ContentValues values = new ContentValues();
		values.put(ItemTable.Cols.UUID, item.getId().toString());
		values.put(ItemTable.Cols.USER_NAME, item.getUserName());
		values.put(ItemTable.Cols.TITLE, item.getTitle());
		values.put(ItemTable.Cols.PRICE, item.getPrice());
		values.put(ItemTable.Cols.DETAIL, item.getDesc());
		values.put(ItemTable.Cols.SOLVED, item.isSolved() ? 1 : 0);

		return values;
	}
}
