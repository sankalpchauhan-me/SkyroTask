package com.sankalpchauhan.topnews.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.sankalpchauhan.topnews.databinding.ActivityMainBinding;
import com.sankalpchauhan.topnews.model.APIResponse;
import com.sankalpchauhan.topnews.model.Article;
import com.sankalpchauhan.topnews.viewmodel.MainActivityViewModel;

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
                mainActivityViewModel.getTopNewsBySource((String)tab.getTag()).observe(MainActivity.this, new Observer<APIResponse>() {
                    @Override
                    public void onChanged(APIResponse apiResponse) {

                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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


}
