package com.sankalpchauhan.topnews.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sankalpchauhan.topnews.R;
import com.sankalpchauhan.topnews.config.Constants;
import com.sankalpchauhan.topnews.databinding.ActivityMainBinding;
import com.sankalpchauhan.topnews.model.APIResponse;
import com.sankalpchauhan.topnews.model.Article;
import com.sankalpchauhan.topnews.util.Utility;
import com.sankalpchauhan.topnews.view.adapter.NewsAdapter;
import com.sankalpchauhan.topnews.viewmodel.MainActivityViewModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

import static com.sankalpchauhan.topnews.util.Utility.isOnline;
import static com.sankalpchauhan.topnews.util.Utility.prepareHashmap;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterClickListener {
    private static final String SOURCE_ID = "ign";
    public MainActivityViewModel mainActivityViewModel;
    private List<Article> articleList;
    private ActivityMainBinding binding;
    private Map<String, String> userSelectednewsSources;
    private GridLayoutManager gridLayoutManager;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Top News");
        initViewModel();
        userSelectednewsSources = prepareHashmap();
        for (Map.Entry<String, String> entry : userSelectednewsSources.entrySet()) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setTag(entry.getValue()).setText(entry.getKey()));
        }
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadArticles((String) tab.getTag());
                binding.newsRv.setVisibility(View.VISIBLE);
                gridLayoutManager.scrollToPosition(0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                loadArticles((String) tab.getTag());
                binding.newsRv.setVisibility(View.VISIBLE);
                gridLayoutManager.scrollToPosition(0);
            }
        });
        setUpRecyclerView(articleList);

        TabLayout.Tab tab = binding.tabLayout.getTabAt(0);
        tab.select();

    }

    private void setUpRecyclerView(List<Article> articleList) {
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        gridLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), columns, RecyclerView.VERTICAL, false);
        binding.newsRv.setLayoutManager(gridLayoutManager);
        binding.newsRv.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(this);
        binding.newsRv.setAdapter(newsAdapter);
        newsAdapter.setNewsData(articleList);
    }

    private void loadArticles(String sourceId) {
        setArticleList(null);
        binding.progressHorizontal.setVisibility(View.VISIBLE);
        mainActivityViewModel.getTopNewsBySource(sourceId).observe(MainActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                binding.progressHorizontal.setVisibility(View.INVISIBLE);
                binding.shimmer.setVisibility(View.INVISIBLE);
                if (apiResponse != null) {
                    setArticleList(apiResponse.getArticles());
                    binding.emptyText.setVisibility(View.INVISIBLE);
                    binding.emptyView.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    binding.emptyText.setVisibility(View.VISIBLE);
                    binding.emptyView.setVisibility(View.VISIBLE);
                    if (!Utility.isOnline()) {
                        binding.emptyText.setText(getResources().getString(R.string.no_internet));
                    }
                }
                newsAdapter.setNewsData(articleList);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStatus();
        Timber.e("Item Count" + newsAdapter.getItemCount());
    }

    private void checkStatus() {
        if (newsAdapter.getItemCount() == 0) {
            binding.emptyText.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.VISIBLE);
            if (!Utility.isOnline()) {
                binding.emptyText.setText(getResources().getString(R.string.no_internet));
            }
        } else {
            binding.emptyText.setVisibility(View.INVISIBLE);
            binding.emptyView.setVisibility(View.INVISIBLE);
        }
    }

    private void initViewModel() {
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }


    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        gridLayoutManager.setSpanCount(columns);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putInt(Constants.TAB_POSITION, binding.tabLayout.getSelectedTabPosition());
        outState.putSerializable(Constants.ARTICLE_LIST, (Serializable) articleList);
        outState.putInt(Constants.ITEM_POSITION, gridLayoutManager.findFirstCompletelyVisibleItemPosition());
        Timber.e("Test 1 " + gridLayoutManager.findFirstCompletelyVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(savedInstanceState.getInt(Constants.TAB_POSITION)));
        binding.tabLayout.getTabAt(savedInstanceState.getInt(Constants.TAB_POSITION)).select();

    }

    @Override
    public void onNewsClick(Article article, int position) {
        if (!isOnline()) {
            Utility.setSnackBar(binding.getRoot(), "No Internet Connection");
        } else {
            Intent i = new Intent(this, NewsDetailActivity.class);
            i.putExtra(Constants.NEWS_URL, article.getUrl());
            startActivity(i);
        }
    }


}
