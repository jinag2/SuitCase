package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ItemListActivity extends AppCompatActivity {

	private static final String EXTRA_LOGIN_USERNAME =
			"com.example.suitcase.login_username";

	public static Intent newIntent(Context packageContext, String username) {
		Intent intent = new Intent(packageContext, ItemListActivity.class);
		intent.putExtra(EXTRA_LOGIN_USERNAME, username);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
	}
}