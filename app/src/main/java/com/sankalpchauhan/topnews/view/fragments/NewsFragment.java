package com.sankalpchauhan.topnews.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sankalpchauhan.topnews.databinding.NewsFragmentBinding;
import com.sankalpchauhan.topnews.model.News;
import com.sankalpchauhan.topnews.view.adapter.NewsAdapter;
import com.sankalpchauhan.topnews.view.ui.MainActivity;

import java.util.List;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterClickListener {
    private NewsFragmentBinding binding;
    private NewsAdapter newsAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = NewsFragmentBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        setUpRecyclerView(setInvoicesData());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(newsAdapter.getItemCount()==0){
            binding.emptyText.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.emptyText.setVisibility(View.INVISIBLE);
            binding.emptyView.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpRecyclerView(List<News> newsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        binding.newsRv.setLayoutManager(linearLayoutManager);
        binding.newsRv.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(this);
        binding.newsRv.setAdapter(newsAdapter);
        newsAdapter.setNewsData(newsList);
    }

    public List<News> setInvoicesData(){
        return ((MainActivity) getActivity()).getNewsList();
    }

    @Override
    public void onNewsClick(News news, int position) {

    }
}
