package com.example.suitcase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SuitCaseBaseHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String DATABASE_NAME = "suitCaseBase.db";

	public SuitCaseBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + SuitCaseDbSchema.ItemTable.NAME + "(" +
				" _id integer primary key autoincrement, " +
				SuitCaseDbSchema.ItemTable.Cols.UUID + ", " +
				SuitCaseDbSchema.ItemTable.Cols.TITLE + ", " +
				SuitCaseDbSchema.ItemTable.Cols.PRICE + ", " +
				SuitCaseDbSchema.ItemTable.Cols.DETAIL + ", " +
				SuitCaseDbSchema.ItemTable.Cols.SOLVED +
				")"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
