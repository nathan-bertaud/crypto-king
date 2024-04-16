package com.example.android_coins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_coins.databinding.ActivityCoinDetailsBinding;
import com.example.android_coins.viewmodels.IViewModelSimple;
import com.example.android_coins.viewmodels.RetrofitCoinDetails;
import com.squareup.picasso.Picasso;


public class CoinDetails extends AppCompatActivity {

    private ActivityCoinDetailsBinding binding;

    private IViewModelSimple viewModelSimple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCoinDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSimpleCoin();
        addClickListeners();
        this.binding.imageButton.setImageResource(this.viewModelSimple.getFavoriteIcon(getUuid()));
    }

    public void getSimpleCoin() {
        viewModelSimple = new ViewModelProvider(this).get(RetrofitCoinDetails.class);
        this.viewModelSimple.getSimpleCoin(getUuid());
    }

    public void addClickListeners() {
        this.binding.button.setOnClickListener(v -> finish());

        this.binding.imageButton.setOnClickListener(v -> this.binding.imageButton.setImageResource(this.viewModelSimple.getFavoriteIconOnClick(getUuid())));

    }

    public Intent returnIntent() {
        return getIntent();
    }

    public String getUuid() {
        return returnIntent().getStringExtra("coinUuid");
    }

    private void putCoinIcon(String urlStr) {
        Picasso.get().load(urlStr).into(this.binding.imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setObservers();
    }

    public void setObservers() {
        this.viewModelSimple.getErrorMessage().observe(this, erreur -> {
            Toast toast = Toast.makeText(getApplicationContext(), erreur, Toast.LENGTH_SHORT);
            toast.show();
        });
        viewModelSimple.getData().observe(this, coinDetails -> {
            this.binding.AthText.setText(coinDetails.getAllTimeHigh().getPrice());
            this.binding.symbolText.setText(coinDetails.getSymbol());
            this.binding.nameText.setText(coinDetails.getName());
            String url = coinDetails.getIconUrl();
            url = url.replace(".svg", ".png");
            this.putCoinIcon(url);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModelSimple.getData().removeObservers(this);
    }

}