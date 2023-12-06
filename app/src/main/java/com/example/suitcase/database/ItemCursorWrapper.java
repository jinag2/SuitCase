package com.example.suitcase.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.suitcase.Item;
import com.example.suitcase.database.SuitCaseDbSchema.ItemTable;

import java.util.UUID;

public class ItemCursorWrapper extends CursorWrapper {
	public ItemCursorWrapper(Cursor cursor) {
		super(cursor);
	}

	public Item getItem() {
		String uuidString = getString(getColumnIndex(ItemTable.Cols.UUID));
		String name = getString(getColumnIndex(ItemTable.Cols.USER_NAME));
		String title = getString(getColumnIndex(ItemTable.Cols.TITLE));
		float price = getFloat(getColumnIndex(ItemTable.Cols.PRICE));
		String detail = getString(getColumnIndex(ItemTable.Cols.DETAIL));
		int isSolved = getInt(getColumnIndex(ItemTable.Cols.SOLVED));

		Item item = new Item(UUID.fromString(uuidString));
		item.setUserName(name);
		item.setTitle(title);
		item.setPrice(price);
		item.setDesc(detail);
		item.setSolved(isSolved != 0);

		return item;
	}
}
