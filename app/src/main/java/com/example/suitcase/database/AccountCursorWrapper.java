package com.example.suitcase.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.suitcase.Account;
import com.example.suitcase.database.SuitCaseDbSchema.AccountTable;

public class AccountCursorWrapper extends CursorWrapper {
	public AccountCursorWrapper(Cursor cursor) {
		super(cursor);
	}

	public Account getAccount() {
		String name = getString(getColumnIndex(AccountTable.Cols.NAME));
		String password = getString(getColumnIndex(AccountTable.Cols.PASSWORD));

		Account account = new Account();
		account.setName(name);
		account.setPassword(password);

		return account;
	}
}
