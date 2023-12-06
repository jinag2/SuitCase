package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private SuitCase mSuitCase;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;
	private Button mLoginButton;
	private Button mSignUpButton;

	private static final int REQUEST_CODE_REGISTER = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSuitCase = SuitCase.get(this);

		mUserNameEditText = findViewById(R.id.login_username);
		mPasswordEditText = findViewById(R.id.login_password);

		mLoginButton = findViewById(R.id.login_btn);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = String.valueOf(mUserNameEditText.getText());
				String password = String.valueOf(mPasswordEditText.getText());
				if (checkAccount(username, password)) {
					mSuitCase.setLoginName(username);
					Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
					startActivity(intent);
				}
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

	private boolean checkAccount(String username, String password) {
		Account account = SuitCase.get(this).getAccount(username);
		if (account != null) {
			if (account.getName().equals(username) && account.getPassword().equals(password)) {
				return true;
			}
		}

		int messageResId = R.string.account_error;
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
				.show();
		return false;
	}
}
