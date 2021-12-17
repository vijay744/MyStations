package com.vijay.mystations.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vijay.mystations.R;
import com.vijay.mystations.core.network.NetworkHelper;
import com.vijay.mystations.databinding.MyStationListItemBinding;
import com.vijay.mystations.data.models.ModelStation;

import java.util.ArrayList;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ModelStation> stationArrayList;

    public StationsAdapter(Context context, ArrayList<ModelStation> stationArrayList) {
        this.context = context;
        this.stationArrayList = stationArrayList;
    }

    @NonNull
    @Override
    public StationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyViewHolder(MyStationListItemBinding.inflate(LayoutInflater.from(context), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StationsAdapter.MyViewHolder myViewHolder, int i) {
        ModelStation station = stationArrayList.get(i);
        myViewHolder.binding.tvStationName.setText(station.getName());
        if (station.getUrl() != null)
            myViewHolder.binding.ivStationImage.setImageUrl(station.getUrl(), NetworkHelper.getInstance(context).getImageLoader());
        myViewHolder.binding.ivStationImage.setErrorImageDrawable(context.getDrawable(R.drawable.ic_error));
        myViewHolder.binding.ivStationImage.setDefaultImageDrawable(context.getDrawable(R.drawable.ic_download));
    }

    @Override
    public int getItemCount() {
        return stationArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyStationListItemBinding binding;

        public MyViewHolder(@NonNull MyStationListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
