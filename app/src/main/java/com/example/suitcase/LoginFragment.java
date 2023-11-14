package com.example.suitcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class LoginFragment  extends Fragment {

	private Account mAccount;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;
	private Button mLoginButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccount = new Account();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login, container, false);

		mUserNameEditText = (EditText) v.findViewById(R.id.username);
		mPasswordEditText = (EditText) v.findViewById(R.id.password);

		mLoginButton = (Button) v.findViewById(R.id.login_btn);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAccount.setName(String.valueOf(mUserNameEditText.getText()));
				mAccount.setPassword(String.valueOf(mPasswordEditText.getText()));
			}
		});

		return v;
	}
}
