package com.mph.xaccapp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.xaccapp.R;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

    public static final String TAG = "RepositoryAdapter";

    public static final int resID = R.layout.repo_row;

    @NonNull
    private Context mContext;

    @NonNull
    private final MainPresenter mPresenter;

    private List<RepositoryViewModel> mItemList;

    public RepositoryAdapter(@NonNull Context context, @NonNull MainPresenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mItemList = new ArrayList<>();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(mContext, getInflatedItemView(parent), mPresenter);
    }

    private View getInflatedItemView(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(resID, viewGroup, false);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.render(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void updateItemList(List<RepositoryViewModel> collection) {
        mItemList.clear();
        mItemList.addAll(collection);
        notifyDataSetChanged();
    }

    public void addItem(RepositoryViewModel item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void updateItem(int position, RepositoryViewModel item) {
        mItemList.set(position, item);
        notifyItemChanged(position, item);
    }

}
