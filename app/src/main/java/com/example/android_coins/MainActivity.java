package com.example.android_coins;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android_coins.databinding.ActivityMainBinding;
import com.example.android_coins.models.PriceResponseData;
import com.example.android_coins.storage.PreferencesHelper;
import com.example.android_coins.viewmodels.IViewModel;
import com.example.android_coins.viewmodels.RetrofitViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private IViewModel viewModel;
    private SampleRecyclerViewAdapter adapter;

    private String sortingMode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createViewModel();
        setupRecyclerView();
        Intent intent = new Intent().setClass(this, CoinDetails.class);
        setClickListener(intent);
        initializeSorting();
    }

    public void createViewModel() {
        viewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);
        viewModel.getCoinsList();
    }

    public void setupRecyclerView() {
        binding.coinRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new SampleRecyclerViewAdapter(new ArrayList<>(), viewModel);
        binding.coinRecyclerView.setAdapter(this.adapter);
    }

    public void initializeSorting() {
        this.viewModel.initializeDataSource();
        this.setSortClickListener();
    }

    public void setClickListener(Intent intent) {
        this.adapter.setListener(coin -> {
            viewModel.setSelectedCoin(coin.getName());
            intent.putExtra(viewModel.PREFERENCES_KEY, coin.getUuid());
            startActivity(intent);
        });
        this.binding.refreshButton.setOnClickListener(v -> this.viewModel.refreshData(this.sortingMode));
    }

    public void setSorting(String sortingMode) {
        switch (sortingMode) {
            case "":
                this.binding.imageSort.setImageResource(R.drawable.caret_down);
                this.sortingMode = "DESC";
                break;
            case "DESC":
                this.binding.imageSort.setImageResource(R.drawable.sortup);
                this.sortingMode = "ASC";
                break;
            case "ASC":
                this.binding.imageSort.setImageResource(R.drawable.sort_arrows_couple_pointing_up_and_down);
                this.sortingMode = "";
                break;
        }
    }

    public void setSortClickListener() {
        this.binding.layoutSort.setOnClickListener(v -> {
            this.setSorting(this.sortingMode);
            this.viewModel.sort(sortingMode);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setObservers();
        Toast toast = Toast.makeText(getApplicationContext(), "Last coin selected : " + PreferencesHelper.getInstance().getSelected(), Toast.LENGTH_SHORT);
        toast.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setObservers() {
        viewModel.getData().observe(this, coinList -> {
            this.adapter.setLocalDataSet((ArrayList<PriceResponseData>) coinList);
            this.adapter.notifyDataSetChanged();
        });
        viewModel.getErrorMessage().observe(this, erreur -> {
            Toast toast = Toast.makeText(getApplicationContext(), erreur, Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.getData().removeObservers(this);
    }
}

