package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class ItemActivity extends AppCompatActivity {

	private static final String EXTRA_ITEM_ID =
			"com.example.suitcase.item_id";

	public static Intent newIntent(Context packageContext, UUID itemId) {
		Intent intent = new Intent(packageContext, ItemActivity.class);
		intent.putExtra(EXTRA_ITEM_ID, itemId);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {
			UUID itemId = (UUID) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
			fragment = ItemFragment.newInstance(itemId);
			fm.beginTransaction()
					.add(R.id.fragment_container, fragment)
					.commit();
		}
	}
}