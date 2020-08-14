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

import timber.log.Timber;

import static com.sankalpchauhan.topnews.util.Utility.isOnline;

public class NewsFragment extends Fragment{
    private NewsFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = NewsFragmentBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        setRetainInstance(true);
//        if(savedInstanceState != null){
//            Timber.e("Test 2: "+savedInstanceState.getInt(Constants.RV_POSITION));
//            binding.newsRv.scrollToPosition(savedInstanceState.getInt(Constants.RV_POSITION));
//        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setNewData(){
    }

    public List<Article> setArticleData(){
        return ((MainActivity) getActivity()).getArticleList();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
