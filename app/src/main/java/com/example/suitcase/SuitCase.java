package com.example.suitcase;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SuitCase {
	private static SuitCase sSuitCase;

	private List<Item> mItems;

	public static SuitCase get(Context context) {
		if (sSuitCase == null) {
			sSuitCase = new SuitCase(context);
		}

		return sSuitCase;
	}

	private SuitCase(Context context) {
		mItems = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Item item = new Item();
			item.setTitle("Item #" + i);
			item.setPrice((i*10+i)/2);
			item.setDesc("This is item #" + i);
			item.setSolved(i % 2 == 0);
			mItems.add(item);
		}
	}

	public List<Item> getItems() {
		return mItems;
	}

	public Item getItem(UUID id) {
		for (Item item : mItems) {
			if (item.getId().equals(id)) {
				return item;
			}
		}

		return null;
	}

	public void updateItem(Item item) {

	}
}
