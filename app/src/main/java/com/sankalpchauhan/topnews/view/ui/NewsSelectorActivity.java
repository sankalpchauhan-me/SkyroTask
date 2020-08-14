package com.sankalpchauhan.topnews.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sankalpchauhan.topnews.databinding.ActivityNewsSelectorBinding;

public class NewsSelectorActivity extends AppCompatActivity {
    private ActivityNewsSelectorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsSelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
