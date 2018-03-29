package com.chamoapp.githubrepoapp.repolist;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chamoapp.githubrepoapp.GlobalApplication;
import com.chamoapp.githubrepoapp.R;
import com.chamoapp.githubrepoapp.repodetail.RepositoryDetailActivity;
import com.chamoapp.githubrepoapp.data.GitHubService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Repository 목록을 표시하는 Activity
 * MVP의 View 역할을 가진다
 */
public class RepositoryListActivity extends AppCompatActivity implements RepositoryListContract.View,
        RepositoryListAdapter.OnRepositoryItemClickListener {

    private Context mContext;

    @BindView(R.id.coordinator_layout)
    View mRootView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.progress_bar)
    View mLoadingView;

    @BindView(R.id.recycler_repos)
    RecyclerView mRecyclerView;

    private RepositoryListAdapter mRepositoryListAdapter;

    private RepositoryListContract.UserActions mRepositoryListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);

        mContext = this;

        ButterKnife.bind(this);

        init();

        GitHubService gitHubService = ((GlobalApplication) getApplication()).getGitHubService();
        mRepositoryListPresenter = new RepositoryListPresenter(this, gitHubService);
        mRepositoryListPresenter.selectLanguage("java");
    }

    private void init() {
        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRepositoryListAdapter = new RepositoryListAdapter(mContext, (RepositoryListAdapter.OnRepositoryItemClickListener) mContext);
        mRecyclerView.setAdapter(mRepositoryListAdapter);
    }

    @Override
    public String getSelectedLanguage() {
        return null;
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showRepositories(GitHubService.Repositories repositories) {
        mRepositoryListAdapter.setItemsAndRefresh(repositories.items);
    }

    @Override
    public void showError() {
        Snackbar.make(mRootView, "서버 오류가 발생하였습니다.", Snackbar.LENGTH_LONG)
                .setAction("재시도", null).show();
    }

    @Override
    public void startRepositoryDetailActivity(String fullRepositoryName) {
        RepositoryDetailActivity.start(mContext, fullRepositoryName);
    }

    @Override
    public void onRepositoryItemClick(GitHubService.RepositoryItem item) {
        mRepositoryListPresenter.selectRepositoryItem(item);
    }
}
