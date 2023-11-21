package com.example.suitcase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragment extends Fragment {

	private static final String ARG_USERNAME = "username";
	private static String mUsername;
	private RecyclerView mCrimeRecyclerView;
	private ItemAdapter mAdapter;

	public static ItemListFragment newInstance(String username) {
		Bundle args = new Bundle();
		args.putString(ARG_USERNAME, username);

		ItemListFragment fragment = new ItemListFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		assert getArguments() != null;
		mUsername = getArguments().getString(ARG_USERNAME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_list, container, false);

		mCrimeRecyclerView = view.findViewById(R.id.item_recycler_view);
		mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		updateUI();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_item_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu_item) {
		int menuItemId = menu_item.getItemId();
		if (menuItemId == R.id.add_item) {
			Item item = new Item();
			SuitCase.get(getActivity()).addItem(item);
			Intent intent = ItemActivity.newIntent(getActivity(), item.getId());
			startActivity(intent);
			return true;
		}
		else {
			return super.onOptionsItemSelected(menu_item);
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private void updateUI() {
		SuitCase suitCase = SuitCase.get(getActivity());
		List<Item> crimes = suitCase.getItems();

		if (mAdapter == null) {
			mAdapter = new ItemAdapter(crimes);
			mCrimeRecyclerView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	private class ItemHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener {

		private Item mItem;

		private TextView mTitleTextView;
		private TextView mPriceTextView;
		private TextView mDescTextView;
		private ImageView mSolvedImageView;

		public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
			super(inflater.inflate(R.layout.view_holder_item, parent, false));
			itemView.setOnClickListener(this);

			mTitleTextView = itemView.findViewById(R.id.item_title);
			mPriceTextView = itemView.findViewById(R.id.item_price);
			mDescTextView = itemView.findViewById(R.id.item_desc);
			mSolvedImageView = itemView.findViewById(R.id.item_solved);
		}

		@SuppressLint("SetTextI18n")
		public void bind(Item item) {
			mItem = item;
			mTitleTextView.setText(mItem.getTitle());
			mPriceTextView.setText(Float.toString(mItem.getPrice()));
			mDescTextView.setText(mItem.getDesc());
			mSolvedImageView.setVisibility(mItem.isSolved() ? View.VISIBLE : View.GONE);
		}

		@Override
		public void onClick(View view) {
			Intent intent = ItemActivity.newIntent(getActivity(), mItem.getId());
			startActivity(intent);
		}
	}

	private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

		private List<Item> mItems;

		public ItemAdapter(List<Item> items) {
			mItems = items;
		}

		@Override
		public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			return new ItemHolder(layoutInflater, parent);
		}

		@Override
		public void onBindViewHolder(ItemHolder holder, int position) {
			Item item = mItems.get(position);
			holder.bind(item);
		}

		@Override
		public int getItemCount() {
			return mItems.size();
		}
	}
}
