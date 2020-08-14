package com.sankalpchauhan.topnews.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sankalpchauhan.topnews.R;
import com.sankalpchauhan.topnews.databinding.NewsItemBinding;
import com.sankalpchauhan.topnews.model.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.sankalpchauhan.topnews.util.Utility.toCalendar;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    final NewsAdapterClickListener newsAdapterClickListener;
    private NewsItemBinding binding;
    List<Article> articleList = new ArrayList<>();

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
        Article article = articleList.get(position);
        if(article.getTitle()!=null) {
            holder.title.setText(article.getTitle());
        }
        if(article.getDescription()!=null) {
            holder.subTitle.setText(article.getDescription());
        }
        Picasso.get().load(article.getUrlToImage()).error(R.drawable.ic_broken_image_black_24dp).into(holder.thumbnail, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
        });
        if(article.getPublishedAt()!=null) {
            String dtStart = article.getPublishedAt();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date date = format.parse(dtStart);
                Calendar cal = toCalendar(date);
                holder.date.setText(String.format("%d/%d/%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
                String ampm;
                if(cal.get(Calendar.AM_PM)==Calendar.AM){
                    ampm = "AM";
                } else {
                    ampm = "PM";
                }
                holder.time.setText(String.format("%d:%d %s", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), ampm));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        if(articleList ==null) {
            return 0;
        }
        return articleList.size();
    }

    public void setNewsData(List<Article> articleData){
        articleList = articleData;
        notifyDataSetChanged();
    }

    public interface NewsAdapterClickListener{
        void onNewsClick(Article article, int position);
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView thumbnail;
        TextView title;
        TextView subTitle;
        TextView date;
        TextView time;
        ProgressBar progressBar;

        public NewsHolder(@NonNull NewsItemBinding itemView) {
            super(itemView.getRoot());
            thumbnail = itemView.thumbnailImage;
            title = itemView.title;
            subTitle = itemView.subtitle;
            date = itemView.dateTV;
            time = itemView.timeTV;
            progressBar = itemView.progressHorizontal;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            newsAdapterClickListener.onNewsClick(articleList.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
