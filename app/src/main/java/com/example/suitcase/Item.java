package com.example.suitcase;

import java.util.UUID;

public class Item {

	private UUID mId;
	private String mUserName;
	private String mTitle;
	private float mPrice;
	private String mDesc;
	private boolean mSolved;

	public Item() {
		this(UUID.randomUUID());
	}

	public Item(UUID id) {
		mId = id;
	}

	public UUID getId() {
		return mId;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String name) {
		mUserName = name;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public float getPrice() {
		return mPrice;
	}

	public void setPrice(float price) {
		mPrice = price;
	}

	public String getDesc() {
		return mDesc;
	}

	public void setDesc(String desc) {
		mDesc = desc;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	public String getPhotoFilename() {
		return "IMG_" + getId().toString() + ".jpg";
	}
}
