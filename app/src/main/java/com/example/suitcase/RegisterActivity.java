package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

	private static final String EXTRA_REGISTER =
			"com.example.suitcase.register";
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;
	private EditText mRePasswordEditText;
	private Button mRegisterButton;
	private Button mCancelButton;

	public static boolean wasRegister(Intent result) {
		return result.getBooleanExtra(EXTRA_REGISTER, false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mUserNameEditText = findViewById(R.id.register_username);
		mPasswordEditText = findViewById(R.id.register_password);
		mRePasswordEditText = findViewById(R.id.register_re_password);
		mRegisterButton = findViewById(R.id.register_btn);
		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkUsername() == false) {
					return;
				}
				if (checkPassword() == false) {
					return;
				}
				setRegisterResult(true);
			}
		});

		mCancelButton = findViewById(R.id.cancel_btn);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setRegisterResult(false);
			}
		});
	}

	private boolean checkUsername() {
		String strUsername = String.valueOf(mUserNameEditText.getText());
		if (strUsername.isBlank() == false) {
			return true;
		}

		int messageResId = R.string.username_is_blank;
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
				.show();
		return false;
	}

	private boolean checkPassword() {
		String strPassword = String.valueOf(mPasswordEditText.getText());
		String strRePassword = String.valueOf(mRePasswordEditText.getText());
		if (strPassword.equals(strRePassword)) {
			return true;
		}
		int messageResId = R.string.passwords_not_match;
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
				.show();
		return false;
	}

	private void setRegisterResult(boolean isRegister) {
		Intent data = new Intent();
		data.putExtra(EXTRA_REGISTER, isRegister);
		setResult(RESULT_OK, data);
		finish();
	}
}