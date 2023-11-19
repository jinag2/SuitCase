package com.example.suitcase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import java.util.UUID;
import androidx.fragment.app.Fragment;

public class ItemFragment  extends Fragment {

	private static final String ARG_ITEM_ID = "item_id";
	private Item mItem;


	public static ItemFragment newInstance(UUID itemId) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_ITEM_ID, itemId);

		ItemFragment fragment = new ItemFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		assert getArguments() != null;
		UUID ItemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
		mItem = SuitCase.get(getActivity()).getItem(ItemId);
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item, container, false);

		EditText mTitleEditText = view.findViewById(R.id.item_title);
		mTitleEditText.setText(mItem.getTitle());
		mTitleEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mItem.setTitle(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		EditText mPriceEditText = view.findViewById(R.id.item_price);
		mPriceEditText.setText(String.format("%.02f", mItem.getPrice()));
		mPriceEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mItem.setPrice(Float.parseFloat(s.toString()));
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		EditText mDescEditText = view.findViewById(R.id.item_desc);
		mDescEditText.setText(mItem.getDesc());
		mDescEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mItem.setDesc(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		CheckBox mSolvedCheckbox = view.findViewById(R.id.item_solved);
		mSolvedCheckbox.setChecked(mItem.isSolved());
		mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mItem.setSolved(isChecked);
			}
		});

		Button mShareButton = view.findViewById(R.id.item_share);
		mShareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, getItemReport());
				intent.putExtra(Intent.EXTRA_SUBJECT,
						getString(R.string.item_report_subject));
				intent = Intent.createChooser(intent, getString(R.string.send_report));
				startActivity(intent);
			}
		});

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();

		SuitCase.get(getActivity())
				.updateItem(mItem);
	}

	private String getItemReport() {
		String solvedString = null;
		if (mItem.isSolved()) {
			solvedString = getString(R.string.item_report_solved);
		} else {
			solvedString = getString(R.string.item_report_unsolved);
		}

		@SuppressLint("DefaultLocale") String price = String.format("%.02f", mItem.getPrice());
		return getString(R.string.item_report, mItem.getTitle(), price, solvedString);
	}
}
