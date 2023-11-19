package com.example.suitcase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ItemFragment  extends Fragment {

	private static final String ARG_ITEM_ID = "item_id";

	private Item mItem;
	private EditText mTitleEditText;
	private EditText mPriceEditText;
	private EditText mDescEditText;
	private CheckBox mSolvedCheckbox;
	private Button mShareButton;

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
		UUID ItemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
		mItem = SuitCase.get(getActivity()).getItem(ItemId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item, container, false);

		mTitleEditText = view.findViewById(R.id.item_title);
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

		mPriceEditText = view.findViewById(R.id.item_price);
		mPriceEditText.setText(String.format("%.02f", mItem.getPrice()));
		mPriceEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mItem.setPrice(Float.valueOf(s.toString()));
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mDescEditText = view.findViewById(R.id.item_desc);
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

		mSolvedCheckbox = view.findViewById(R.id.item_solved);
		mSolvedCheckbox.setChecked(mItem.isSolved());
		mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mItem.setSolved(isChecked);
			}
		});

		mShareButton = view.findViewById(R.id.item_share);
		mShareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		return view;
	}
}
