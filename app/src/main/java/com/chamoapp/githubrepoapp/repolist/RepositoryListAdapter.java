package com.chamoapp.githubrepoapp.repolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chamoapp.githubrepoapp.R;
import com.chamoapp.githubrepoapp.data.GitHubService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView에서 리포지토리 목록을 표시하기 위한 Adapter 클래스
 * 이 클래스에 의해 RecyclerView 아이템의 View를 생성하고, View에 데이터를 넣는다
 */
public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepoViewHolder> {
    private final OnRepositoryItemClickListener mOnRepositoryItemClickListener;
    private final Context mContext;
    private List<GitHubService.RepositoryItem> mItems;

    public RepositoryListAdapter(Context context, OnRepositoryItemClickListener onRepositoryItemClickListener) {
        this.mContext = context;
        this.mOnRepositoryItemClickListener = onRepositoryItemClickListener;
    }

    public void setItemsAndRefresh(List<GitHubService.RepositoryItem> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public GitHubService.RepositoryItem getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.repo_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, final int position) {
        final GitHubService.RepositoryItem item = getItemAt(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRepositoryItemClickListener.onRepositoryItemClick(item);
            }
        });

        holder.repoName.setText(item.name);
        holder.repoDetail.setText(item.description);
        holder.starCount.setText(item.stargazersCount);

        Glide.with(mContext)
                .load(item.owner.avatarUrl)
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(holder.repoImage);

    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    interface OnRepositoryItemClickListener {
        void onRepositoryItemClick(GitHubService.RepositoryItem item);
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_name)
        TextView repoName;

        @BindView(R.id.repo_detail)
        TextView repoDetail;

        @BindView(R.id.repo_image)
        ImageView repoImage;

        @BindView(R.id.repo_star)
        TextView starCount;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

