package com.chamoapp.githubrepoapp.repodetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chamoapp.githubrepoapp.GlobalApplication;
import com.chamoapp.githubrepoapp.R;
import com.chamoapp.githubrepoapp.data.GitHubService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Repository 상세화면을 표시하는 Activity
 */
public class RepositoryDetailActivity extends AppCompatActivity implements RepositoryDetailContract.View {
    private static final String EXTRA_FULL_REPOSITORY_NAME = "EXTRA_FULL_REPOSITORY_NAME";

    private Context mContext;

    @BindView(R.id.fullname)
    TextView mFullNameTextView;

    @BindView(R.id.detail)
    TextView mDetailTextView;

    @BindView(R.id.repo_star)
    TextView mRepoStarTextView;

    @BindView(R.id.repo_fork)
    TextView mRepoForkTextView;

    @BindView(R.id.owner_image)
    ImageView mOwnerImageView;

    private RepositoryDetailContract.UserActions mRepositoryDetailPresenter;
    private String mFullRepoName;

    public static void start(Context context, String fullRepositoryName) {
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        mContext = this;

        ButterKnife.bind(this);

        Intent intent = getIntent();
        mFullRepoName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME);

        GitHubService gitHubService = ((GlobalApplication) getApplication()).getGitHubService();
        mRepositoryDetailPresenter = new RepositoryDetailPresenter(this, gitHubService);
        mRepositoryDetailPresenter.prepare();
    }

    @Override
    public String getFullRepositoryName() {
        return mFullRepoName;
    }

    @Override
    public void showRepositoryInfo(GitHubService.RepositoryItem response) {
        mFullNameTextView.setText(response.fullName);
        mDetailTextView.setText(response.description);
        mRepoStarTextView.setText(response.stargazersCount);
        mRepoForkTextView.setText(response.forksCount);

        Glide.with(mContext)
                .load(response.owner.avatarUrl)
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(mOwnerImageView);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepositoryDetailPresenter.titleClick();
            }
        };
        mFullNameTextView.setOnClickListener(listener);
        mOwnerImageView.setOnClickListener(listener);
    }

    @Override
    public void startBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .show();
    }
}
