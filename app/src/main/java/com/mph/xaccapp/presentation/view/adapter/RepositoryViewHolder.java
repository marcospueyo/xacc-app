package com.mph.xaccapp.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mph.xaccapp.R;
import com.mph.xaccapp.presentation.model.RepositoryViewModel;
import com.mph.xaccapp.presentation.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.label_title) TextView tvTitle;

    @BindView(R.id.label_descr) TextView tvDescr;

    @BindView(R.id.label_owner) TextView tvOwner;

    @NonNull
    private Context mContext;

    @NonNull
    private MainPresenter mPresenter;

    public RepositoryViewHolder(Context context, View itemView,  @NonNull MainPresenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mPresenter = presenter;
    }

    public void render(final RepositoryViewModel repository) {
        renderTitle(repository.title());
        renderDescr(repository.description());
        renderOwner(repository.login());
        renderForkState(repository.fork());
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mPresenter.onItemSelected(repository);
                return false;
            }
        });
    }

    private void renderTitle(String title) {
        tvTitle.setText(title);
    }

    private void renderDescr(String descr) {
        tvDescr.setText(descr);
    }

    private void renderOwner(String owner) {
        tvOwner.setText(owner);
    }

    private void renderForkState(boolean isForked) {
        int colour = isForked ? R.color.white : R.color.green_light;
        int contrastColour = isForked ? R.color.gray_dark : R.color.white;
        tvTitle.setTextColor(ContextCompat.getColor(mContext, contrastColour));
        tvOwner.setTextColor(ContextCompat.getColor(mContext, contrastColour));
        itemView.setBackgroundColor(ContextCompat.getColor(mContext, colour));
    }
}
