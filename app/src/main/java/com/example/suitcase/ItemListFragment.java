package com.example.suitcase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragment extends Fragment {
	private RecyclerView mCrimeRecyclerView;
	private ItemAdapter mAdapter;

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
