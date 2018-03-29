package com.chamoapp.githubrepoapp.repodetail;


import com.chamoapp.githubrepoapp.data.GitHubService;

/**
 * 각각 역할을 가진 Contract를 정의해두는 인터페이스
 */
public interface RepositoryDetailContract {

    interface View {
        String getFullRepositoryName();

        void showRepositoryInfo(GitHubService.RepositoryItem response);

        void startBrowser(String url);

        void showError(String message);
    }

    interface UserActions {
        void titleClick();

        void prepare();
    }
}


