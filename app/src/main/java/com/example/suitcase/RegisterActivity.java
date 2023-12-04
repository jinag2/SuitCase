package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suitcase.database.SuitCaseDbSchema;

public class RegisterActivity extends AppCompatActivity {

	private static final String EXTRA_REGISTER =
			"com.example.suitcase.register";
	private SuitCase mSuitCase;
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
		mSuitCase = SuitCase.get(this);

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

				Account account = new Account();
				account.setName( String.valueOf(mUserNameEditText.getText()) );
				account.setPassword( String.valueOf(mPasswordEditText.getText()) );
				mSuitCase.addAccount(account);

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
		String username = String.valueOf(mUserNameEditText.getText());
		if (username.isBlank()) {
			int messageResId = R.string.username_is_blank;
			Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		Account account = mSuitCase.getAccount(username);
		if (account == null) {
			return true;
		} else {
			int messageResId = R.string.username_exist;
			Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
	}

	private boolean checkPassword() {
		String password = String.valueOf(mPasswordEditText.getText());
		String rePassword = String.valueOf(mRePasswordEditText.getText());
		if (password.equals(rePassword)) {
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