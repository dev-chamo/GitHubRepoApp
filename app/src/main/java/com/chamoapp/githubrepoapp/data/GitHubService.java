package com.chamoapp.githubrepoapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Retrofit로 GitHub의 API를 이용하기 위한 클래스
 */
public interface GitHubService {
    /**
     * GitHub의 리포지토리 검색 결과를 가져온다
     * https://developer.github.com/v3/search/
     *
     * @param query GitHub의 API로 검색할 내용
     * @return API 액세스 결과를 얻은 후의 콜백으로서 SearchResponse를 가져올 수 있는 RxJava의 Observable로 반환한다
     */
    @GET("search/repositories?sort=stars&order=desc")
    Observable<Repositories> listRepos(@Query("q") String query);

    /**
     * 리포지토리의 상세내용을 가져온다
     * https://developer.github.com/v3/repos/#get
     *
     * @return API 액세스 결과를 얻은 후의 콜백으로서 RepositoryItem을 가져올 수 있는 RxJava의 Observable로 반환한다
     */
    @GET("repos/{repoOwner}/{repoName}")
    Observable<RepositoryItem> detailRepo(@Path(value = "repoOwner") String owner, @Path(value = "repoName") String repoName);

    /**
     * API 액세스 결과가 이 클래스에 들어간다
     * GitHub의 리포지토리 목록이 들어있다
     *
     * @see GitHubService#listRepos(String)
     */
    public static class Repositories {

        public final List<RepositoryItem> items;

        public Repositories(List<RepositoryItem> items) {
            this.items = items;
        }

    }

    /**
     * API 액세스 결과가 이 클래스에 들어간다
     * GitHub 리포지터리의 데이터가 들어있다
     *
     * @see GitHubService#detailRepo(String, String)
     */
    public static class RepositoryItem {

        public final String description;
        public final Owner owner;
        public final String language;
        public final String name;

        @SerializedName("stargazers_count")
        public final String stargazersCount;

        @SerializedName("forks_count")
        public final String forksCount;

        @SerializedName("full_name")
        public final String fullName;

        @SerializedName("html_url")
        public final String htmlUrl;


        public RepositoryItem(String description, Owner owner, String language, String name, String stargazersCount, String forksCount, String fullName, String htmlUrl) {
            this.description = description;
            this.owner = owner;
            this.language = language;
            this.name = name;
            this.stargazersCount = stargazersCount;
            this.forksCount = forksCount;
            this.fullName = fullName;
            this.htmlUrl = htmlUrl;
        }
    }

    /**
     * GitHub 리포지토리에 대한 오너의 데이터가 들어있다
     *
     * @see GitHubService#detailRepo(String, String)
     */
    public static class Owner {
        @SerializedName("received_events_url")
        public final String receivedEventsUrl;

        @SerializedName("organizations_url")
        public final String organizationsUrl;

        @SerializedName("avatar_url")
        public final String avatarUrl;

        @SerializedName("gravatar_id")
        public final String gravatarId;

        @SerializedName("gists_url")
        public final String gistsUrl;

        @SerializedName("starred_url")
        public final String starredUrl;

        @SerializedName("site_admin")
        public final String siteAdmin;

        public final String type;
        public final String url;
        public final String id;

        @SerializedName("html_url")
        public final String htmlUrl;

        @SerializedName("following_url")
        public final String followingUrl;

        @SerializedName("events_url")
        public final String eventsUrl;

        public final String login;

        @SerializedName("subscriptions_url")
        public final String subscriptionsUrl;

        @SerializedName("repos_url")
        public final String reposUrl;

        @SerializedName("followers_url")
        public final String followersUrl;

        public Owner(String receivedEventsUrl, String organizationsUrl, String avatarUrl, String gravatarId, String gistsUrl, String starredUrl, String siteAdmin, String type, String url, String id, String htmlUrl, String followingUrl, String eventsUrl, String login, String subscriptionsUrl, String reposUrl, String followersUrl) {
            this.receivedEventsUrl = receivedEventsUrl;
            this.organizationsUrl = organizationsUrl;
            this.avatarUrl = avatarUrl;
            this.gravatarId = gravatarId;
            this.gistsUrl = gistsUrl;
            this.starredUrl = starredUrl;
            this.siteAdmin = siteAdmin;
            this.type = type;
            this.url = url;
            this.id = id;
            this.htmlUrl = htmlUrl;
            this.followingUrl = followingUrl;
            this.eventsUrl = eventsUrl;
            this.login = login;
            this.subscriptionsUrl = subscriptionsUrl;
            this.reposUrl = reposUrl;
            this.followersUrl = followersUrl;
        }

    }


}