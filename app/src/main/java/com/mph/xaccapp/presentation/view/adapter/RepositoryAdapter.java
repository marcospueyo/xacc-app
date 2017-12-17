package com.mph.xaccapp.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.xaccapp.R;
import com.mph.xaccapp.presentation.model.RepositoryViewModel;
import com.mph.xaccapp.presentation.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

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
        holder.render(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setItemList(List<RepositoryViewModel> models) {
        mItemList.clear();
        addToItemList(models);
    }

    public void addToItemList(List<RepositoryViewModel> models) {
        mItemList.addAll(models);
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
