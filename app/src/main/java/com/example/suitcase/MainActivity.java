package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

	private Account mAccount;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;
	private Button mLoginButton;
	private Button mSignUpButton;

	private static final int REQUEST_CODE_REGISTER = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAccount = new Account("chenon", "1234");

		mUserNameEditText = findViewById(R.id.login_username);
		mPasswordEditText = findViewById(R.id.login_password);

		mLoginButton = findViewById(R.id.login_btn);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Account loginAccount = new Account();
				loginAccount.setName(String.valueOf(mUserNameEditText.getText()));
				loginAccount.setPassword(String.valueOf(mPasswordEditText.getText()));
			}
		});

		mSignUpButton = findViewById(R.id.sign_up_btn);
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivityForResult(intent, REQUEST_CODE_REGISTER);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == REQUEST_CODE_REGISTER) {
			if (data == null) {
				return;
			}
			Boolean register = RegisterActivity.wasRegister(data);
		}
	}
}
