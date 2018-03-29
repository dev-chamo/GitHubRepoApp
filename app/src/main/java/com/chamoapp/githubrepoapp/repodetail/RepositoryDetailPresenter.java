package com.chamoapp.githubrepoapp.repodetail;



import com.chamoapp.githubrepoapp.data.GitHubService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RepositoryDetailPresenter implements RepositoryDetailContract.UserActions {
    private RepositoryDetailContract.View mRepositoryDetailView;
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
                .subscribe(new DisposableObserver<GitHubService.RepositoryItem>() {
                    @Override
                    public void onNext(GitHubService.RepositoryItem repositoryItem) {
                        mRepositoryItem = repositoryItem;
                        mRepositoryDetailView.showRepositoryInfo(mRepositoryItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRepositoryDetailView.showError("읽을 수 없습니다.");
                    }

                    @Override
                    public void onComplete() {

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
