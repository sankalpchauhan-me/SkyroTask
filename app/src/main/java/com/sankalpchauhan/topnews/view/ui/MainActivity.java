package com.sankalpchauhan.topnews.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.sankalpchauhan.topnews.R;
import com.sankalpchauhan.topnews.databinding.ActivityMainBinding;
import com.sankalpchauhan.topnews.model.News;
import com.sankalpchauhan.topnews.util.Utility;

import java.util.List;
import java.util.Map;

import static com.sankalpchauhan.topnews.util.Utility.prepareHashmap;

public class MainActivity extends AppCompatActivity {
    private List<News> newsList;
    private ActivityMainBinding binding;
    private Map<String, String> userSelectednewsSources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userSelectednewsSources = prepareHashmap();
        for(Map.Entry<String, String> entry: userSelectednewsSources.entrySet()){
            binding.tabLayout.addTab(binding.tabLayout.newTab().setTag(entry.getValue()).setText(entry.getKey()));

        }
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }


}
