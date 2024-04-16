package com.example.android_coins;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_coins.databinding.RecyclerViewLayoutBinding;
import com.example.android_coins.interfaces.ListenerCoin;
import com.example.android_coins.models.PriceResponseData;
import com.example.android_coins.viewmodels.IViewModel;

import java.util.ArrayList;

public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<PriceResponseData> localDataSet;

    private ListenerCoin listener;

    private final IViewModel viewModel;

    public SampleRecyclerViewAdapter(ArrayList<PriceResponseData> dataSet, IViewModel viewModel) {
        setLocalDataSet(dataSet);
        this.viewModel = viewModel;
    }

    public void setListener(ListenerCoin listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        updateTextView(viewHolder, position);
        viewHolder.getStarImage().setVisibility(this.viewModel.checkFavorite(localDataSet, position));
        setClickListener(viewHolder, position);
    }

    public void setClickListener(MyViewHolder viewHolder, final int position) {
        viewHolder.getView().setOnClickListener(v -> this.listener.onClick(localDataSet.get(position)));
    }

    public void updateTextView(MyViewHolder viewHolder, final int position) {
        viewHolder.getCoinName().setText(localDataSet.get(position).getName());
        viewHolder.getCoinPrice().setText(localDataSet.get(position).getPrice());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MyViewHolder(RecyclerViewLayoutBinding.inflate(inflater, viewGroup, false));
    }

    public void setLocalDataSet(ArrayList<PriceResponseData> newDataSet) {
        this.localDataSet = newDataSet;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (this.localDataSet != null) {
            size = this.localDataSet.size();
        }
        return size;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView coinName;

        private final TextView coinPrice;

        private final ImageView starImage;

        private final View view;

        public MyViewHolder(@NonNull RecyclerViewLayoutBinding binding) {
            super(binding.getRoot());
            this.coinName = binding.coinName;
            this.coinPrice = binding.coinPrice;
            this.starImage = binding.starImage;
            this.view = itemView;
        }

        public TextView getCoinName() {
            return this.coinName;
        }

        public ImageView getStarImage() {
            return starImage;
        }

        public TextView getCoinPrice() {
            return this.coinPrice;
        }

        public View getView() {
            return this.view;
        }
    }
}
