package com.chamoapp.githubrepoapp.repolist;

import android.text.format.DateFormat;

import com.chamoapp.githubrepoapp.data.GitHubService;


import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * MVP의 Presenter 역할을 하는 클래스
 */
public class RepositoryListPresenter implements RepositoryListContract.UserActions {
    private final RepositoryListContract.View mRepositoryListView;
    private final GitHubService mGitHubService;

    public RepositoryListPresenter(RepositoryListContract.View repositoryListView, GitHubService gitHubService) {
        this.mRepositoryListView = repositoryListView;
        this.mGitHubService = gitHubService;
    }

    @Override
    public void selectLanguage(String language) {
        loadRepositories();
    }

    @Override
    public void selectRepositoryItem(GitHubService.RepositoryItem item) {
        mRepositoryListView.startRepositoryDetailActivity(item.fullName);
    }

    /**
     * 지난 일주일간 만들어진 라이브러리의 인기순으로 가져온다
     */
    private void loadRepositories() {
        mRepositoryListView.showLoading();

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String text = DateFormat.format("yyyy-MM-dd", calendar).toString();

        Observable<GitHubService.Repositories> observable = mGitHubService.listRepos(
                "language:" + mRepositoryListView.getSelectedLanguage() + " " + "created:>" + text);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GitHubService.Repositories>() {
                    @Override
                    public void onNext(GitHubService.Repositories repositories) {
                        mRepositoryListView.hideLoading();
                        mRepositoryListView.showRepositories(repositories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRepositoryListView.showError();
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }
}
