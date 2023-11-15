package com.example.suitcase;

import java.util.Objects;

public class Account {

	private String mName;
	private String mPassword;

	public Account() {
	}

	public Account(String name, String password) {
		mName = name;
		mPassword = password;
	}
	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	public String getPassword() {
		return mPassword;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return mName.equals(account.mName) && mPassword.equals(account.mPassword);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mName, mPassword);
	}
}
