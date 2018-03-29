package com.chamoapp.githubrepoapp.repolist;

import com.chamoapp.githubrepoapp.data.GitHubService;

/**
 * 각자의 역할이 가진 Contract를 정의해 둘 인터페이스
 */
public interface RepositoryListContract {

    interface View {
        String getSelectedLanguage();

        void showLoading();

        void hideLoading();

        void showRepositories(GitHubService.Repositories repositories);

        void showError();

        void startRepositoryDetailActivity(String fullRepositoryName);
    }

    interface UserActions {
        void selectLanguage(String language);

        void selectRepositoryItem(GitHubService.RepositoryItem item);
    }
}
