package com.sankalpchauhan.topnews.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sankalpchauhan.topnews.R;
import com.sankalpchauhan.topnews.config.Constants;
import com.sankalpchauhan.topnews.databinding.NewsFragmentBinding;
import com.sankalpchauhan.topnews.model.Article;
import com.sankalpchauhan.topnews.util.Utility;
import com.sankalpchauhan.topnews.view.adapter.NewsAdapter;
import com.sankalpchauhan.topnews.view.ui.MainActivity;
import com.sankalpchauhan.topnews.view.ui.NewsDetailActivity;

import java.util.List;

import static com.sankalpchauhan.topnews.util.Utility.isOnline;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterClickListener {
    private NewsFragmentBinding binding;
    private NewsAdapter newsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = NewsFragmentBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        setUpRecyclerView(setInvoicesData());
        setRetainInstance(true);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(newsAdapter.getItemCount()==0){
            binding.emptyText.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.VISIBLE);
            if(!Utility.isOnline()){
                binding.emptyText.setText("No Internet");
            }
        } else {
            binding.emptyText.setVisibility(View.INVISIBLE);
            binding.emptyView.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpRecyclerView(List<Article> articleList) {
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), columns, RecyclerView.VERTICAL, false);
        binding.newsRv.setLayoutManager(gridLayoutManager);
        binding.newsRv.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(this);
        binding.newsRv.setAdapter(newsAdapter);
        newsAdapter.setNewsData(articleList);
    }

    public List<Article> setInvoicesData(){
        return ((MainActivity) getActivity()).getArticleList();
    }

    @Override
    public void onNewsClick(Article article, int position) {
        if(!isOnline()){
            Utility.setSnackBar(binding.getRoot(), "No Internet Connection");
        } else {
            Intent i = new Intent(getActivity(), NewsDetailActivity.class);
            i.putExtra(Constants.NEWS_URL, article.getUrl());
            startActivity(i);
        }
    }
}
