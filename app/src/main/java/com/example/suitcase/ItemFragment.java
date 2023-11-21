package com.example.suitcase;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class ItemFragment  extends Fragment {

	private static final String ARG_ITEM_ID = "item_id";
	private static final String authority = "com.example.suitcase.fileprovider";
	private static final int REQUEST_PHOTO = 2;
	private Item mItem;
	private File mPhotoFile;
	private EditText mTitleEditText;
	private EditText mPriceEditText;
	private EditText mDescEditText;
	private CheckBox mSolvedCheckbox;
	private Button mShareButton;
	private ImageButton mPhotoButton;
	private ImageView mPhotoView;

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
		setHasOptionsMenu(true);
		assert getArguments() != null;
		UUID ItemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
		mItem = SuitCase.get(getActivity()).getItem(ItemId);
		mPhotoFile = SuitCase.get(getActivity()).getPhotoFile(mItem);
	}

	@SuppressLint("DefaultLocale")
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
				mItem.setPrice(Float.parseFloat(s.toString()));
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
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, getItemReport());
				intent.putExtra(Intent.EXTRA_SUBJECT,
						getString(R.string.item_report_subject));
				intent = Intent.createChooser(intent, getString(R.string.send_report));
				startActivity(intent);
			}
		});

		PackageManager packageManager = getActivity().getPackageManager();

		mPhotoButton = view.findViewById(R.id.item_camera);
		final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		boolean b1 = mPhotoFile != null;
		boolean b2 = captureImage.resolveActivity(packageManager) != null;
		boolean canTakePhoto = mPhotoFile != null &&
				captureImage.resolveActivity(packageManager) != null;
		mPhotoButton.setEnabled(canTakePhoto);

		mPhotoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = FileProvider.getUriForFile(getActivity(), authority, mPhotoFile);
				captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

				List<ResolveInfo> cameraActivities = getActivity()
						.getPackageManager().queryIntentActivities(captureImage,
								PackageManager.MATCH_DEFAULT_ONLY);

				for (ResolveInfo activity : cameraActivities) {
					getActivity().grantUriPermission(activity.activityInfo.packageName,
							uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				}

				startActivityForResult(captureImage, REQUEST_PHOTO);
			}
		});

		mPhotoView = view.findViewById(R.id.item_photo);
		updatePhotoView();

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();

		SuitCase.get(getActivity())
				.updateItem(mItem);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_item, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu_item) {
		int menuItemId = menu_item.getItemId();
		if (menuItemId == R.id.del_item) {
			SuitCase.get(getActivity()).delItem(mItem);
			getActivity().finish();
			return true;
		}
		else {
			return super.onOptionsItemSelected(menu_item);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		if (requestCode == REQUEST_PHOTO) {
			Uri uri = FileProvider.getUriForFile(getActivity(), authority, mPhotoFile);
			getActivity().revokeUriPermission(uri,
					Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

			updatePhotoView();
		}
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

	private void updatePhotoView() {
		if (mPhotoFile == null || !mPhotoFile.exists()) {
			mPhotoView.setImageDrawable(null);
		} else {
			Bitmap bitmap = PictureUtils.getScaledBitmap(
					mPhotoFile.getPath(), getActivity());
			mPhotoView.setImageBitmap(bitmap);
		}
	}
}
