package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ItemListActivity extends AppCompatActivity {

	private static final String EXTRA_LOGIN_USERNAME =
			"com.example.suitcase.login_username";

	private String mUsername;

	public static Intent newIntent(Context packageContext, String username) {
		Intent intent = new Intent(packageContext, ItemListActivity.class);
		intent.putExtra(EXTRA_LOGIN_USERNAME, username);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		mUsername = getIntent().getStringExtra(EXTRA_LOGIN_USERNAME);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			fragment = new ItemListFragment();
			fm.beginTransaction()
					.add(R.id.fragment_container, fragment)
					.commit();
		}
	}
}