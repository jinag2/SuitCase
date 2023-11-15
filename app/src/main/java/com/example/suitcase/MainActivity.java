package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

	private Account mAccount;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;
	private Button mLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAccount = new Account("chenon", "1234");

		mUserNameEditText = findViewById(R.id.username);
		mPasswordEditText = findViewById(R.id.password);

		mLoginButton = findViewById(R.id.login_btn);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Account loginAccount = new Account();
				loginAccount.setName(String.valueOf(mUserNameEditText.getText()));
				loginAccount.setPassword(String.valueOf(mPasswordEditText.getText()));
			}
		});
	}
}
