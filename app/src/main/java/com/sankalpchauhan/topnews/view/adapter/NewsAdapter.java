package com.sankalpchauhan.topnews.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sankalpchauhan.topnews.databinding.NewsItemBinding;
import com.sankalpchauhan.topnews.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    final NewsAdapterClickListener newsAdapterClickListener;
    private NewsItemBinding binding;
    List<News> newsList = new ArrayList<>();

    public NewsAdapter(NewsAdapterClickListener newsAdapterClickListener){
        this.newsAdapterClickListener = newsAdapterClickListener;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = NewsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsHolder holder, int position) {
        News news = newsList.get(position);

    }

    @Override
    public int getItemCount() {
        if(newsList==null) {
            return 0;
        }
        return newsList.size();
    }

    public void setNewsData(List<News> newsData){
        newsList = newsData;
        notifyDataSetChanged();
    }

    public interface NewsAdapterClickListener{
        void onNewsClick(News news, int position);
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView thumbnail;
        TextView title;
        TextView subTitle;
        TextView date;
        TextView time;

        public NewsHolder(@NonNull NewsItemBinding itemView) {
            super(itemView.getRoot());
            thumbnail = itemView.thumbnailImage;
            title = itemView.title;
            subTitle = itemView.subtitle;
            date = itemView.dateTV;
            time = itemView.timeTV;
        }

        @Override
        public void onClick(View view) {
            newsAdapterClickListener.onNewsClick(newsList.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
