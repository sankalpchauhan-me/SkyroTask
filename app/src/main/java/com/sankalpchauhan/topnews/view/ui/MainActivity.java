package com.sankalpchauhan.topnews.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sankalpchauhan.topnews.R;
import com.sankalpchauhan.topnews.config.Constants;
import com.sankalpchauhan.topnews.databinding.ActivityMainBinding;
import com.sankalpchauhan.topnews.model.APIResponse;
import com.sankalpchauhan.topnews.model.Article;
import com.sankalpchauhan.topnews.view.fragments.NewsFragment;
import com.sankalpchauhan.topnews.viewmodel.MainActivityViewModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

import static com.sankalpchauhan.topnews.util.Utility.prepareHashmap;

public class MainActivity extends AppCompatActivity {
    private List<Article> articleList;
    private ActivityMainBinding binding;
    private Map<String, String> userSelectednewsSources;
    public MainActivityViewModel mainActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        userSelectednewsSources = prepareHashmap();
        for(Map.Entry<String, String> entry: userSelectednewsSources.entrySet()){
            binding.tabLayout.addTab(binding.tabLayout.newTab().setTag(entry.getValue()).setText(entry.getKey()));
        }
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadArticles((String)tab.getTag());
                binding.fragment.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                loadArticles((String)tab.getTag());
                binding.fragment.setVisibility(View.VISIBLE);
            }
        });

        TabLayout.Tab tab = binding.tabLayout.getTabAt(0);
        tab.select();

    }

    private void loadArticles(String sourceId){
        setArticleList(null);
        binding.progressHorizontal.setVisibility(View.VISIBLE);
        mainActivityViewModel.getTopNewsBySource(sourceId).observe(MainActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                binding.progressHorizontal.setVisibility(View.INVISIBLE);
                binding.shimmer.setVisibility(View.INVISIBLE);
                if(apiResponse!=null) {
                    setArticleList(apiResponse.getArticles());
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
                loadFragment(new NewsFragment());
            }
        });
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

    public boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.TAB_POSITION,binding.tabLayout.getSelectedTabPosition());
        outState.putSerializable(Constants.ARTICLE_LIST, (Serializable) articleList);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(savedInstanceState.getInt(Constants.TAB_POSITION)));
        binding.tabLayout.getTabAt(savedInstanceState.getInt(Constants.TAB_POSITION)).select();
    }


}
