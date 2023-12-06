package com.example.suitcase.database;

public class SuitCaseDbSchema {
	public static final class AccountTable {
		public static final String NAME = "accounts";

		public static final class Cols {
			public static final String NAME = "name";
			public static final String PASSWORD = "password";
		}
	}

	public static final class ItemTable {
		public static final String NAME = "items";

		public static final class Cols {
			public static final String UUID = "uuid";
			public static final String USER_NAME = "user_name";
			public static final String TITLE = "title";
			public static final String PRICE = "price";
			public static final String DETAIL = "detail";
			public static final String SOLVED = "solved";
		}
	}
}
