package com.chamoapp.githubrepoapp.repodetail;


import android.util.Log;

import com.chamoapp.githubrepoapp.repodetail.RepositoryDetailContract;
import com.chamoapp.githubrepoapp.data.GitHubService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepositoryDetailPresenter implements RepositoryDetailContract.UserActions {
    final RepositoryDetailContract.View mRepositoryDetailView;
    private GitHubService mGitHubService;
    private GitHubService.RepositoryItem mRepositoryItem;

    public RepositoryDetailPresenter(RepositoryDetailContract.View detailView, GitHubService gitHubService) {
        this.mRepositoryDetailView = detailView;
        this.mGitHubService = gitHubService;
    }

    @Override
    public void prepare() {
        loadRepositories();
    }

    /**
     * 하나의 리포지토리에 관한 정보를 가져온다
     * 기본적으로 API 액세스 방법은 RepositoryListActivity#loadRepositories(String)와 같다
     */
    private void loadRepositories() {
        String fullRepoName = mRepositoryDetailView.getFullRepositoryName();
        final String[] repoData = fullRepoName.split("/");
        final String owner = repoData[0];
        final String repoName = repoData[1];
        mGitHubService.detailRepo(owner, repoName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GitHubService.RepositoryItem>() {
                    @Override
                    public void accept(GitHubService.RepositoryItem repositoryItem) {
                        mRepositoryItem = repositoryItem;
                        mRepositoryDetailView.showRepositoryInfo(mRepositoryItem);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("koo", throwable.getMessage());
                        mRepositoryDetailView.showError("읽을 수 없습니다.");
                    }
                });
    }

    @Override
    public void titleClick() {
        try {
            mRepositoryDetailView.startBrowser(mRepositoryItem.htmlUrl);
        } catch (Exception e) {
            mRepositoryDetailView.showError("링크를 열수 없습니다.");
        }
    }

}
