package com.example.suitcase;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SuitCase {
	private static SuitCase sSuitCase;
	private Context mContext;
	private List<Item> mItemList;

	public static SuitCase get(Context context) {
		if (sSuitCase == null) {
			sSuitCase = new SuitCase(context);
		}

		return sSuitCase;
	}

	private SuitCase(Context context) {
		mContext = context.getApplicationContext();
		mItemList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Item item = new Item();
			item.setTitle("Item #" + i);
			item.setPrice((i*10+i)/2);
			item.setDesc("This is item #" + i);
			item.setSolved(i % 2 == 0);
			mItemList.add(item);
		}
	}

	public void addItem(Item item) {
		int no = mItemList.size();
		item.setTitle("Item #" + no);
		item.setPrice((no*10+no)/2);
		item.setDesc("This is item #" + no);
		item.setSolved(no % 2 == 0);
		mItemList.add(item);
	}

	public boolean delItem(Item item) {
		return mItemList.remove(item);
	}

	public List<Item> getItems() {
		return mItemList;
	}

	public Item getItem(UUID id) {
		for (Item item : mItemList) {
			if (item.getId().equals(id)) {
				return item;
			}
		}

		return null;
	}

	public File getPhotoFile(Item item) {
		File filesDir = mContext.getFilesDir();
		return new File(filesDir, item.getPhotoFilename());
	}

	public void updateItem(Item item) {

	}
}
