package com.vijay.mystations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.vijay.mystations.core.app.MyStationsApp;
import com.vijay.mystations.core.network.Result;
import com.vijay.mystations.core.utils.LoaderUtils;
import com.vijay.mystations.data.models.ModelStation;
import com.vijay.mystations.databinding.ActivityStationListBinding;
import com.vijay.mystations.view.adapters.StationsAdapter;
import com.vijay.mystations.viewModel.StationsViewModel;

import java.util.ArrayList;

public class StationListActivity extends AppCompatActivity {
    private StationsViewModel viewModel;
    private ActivityStationListBinding binding;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStationListBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();
        setContentView(rootView);

        setUpAndObserverVM();

    }

    private void setUpAndObserverVM() {
        viewModel = new ViewModelProvider(this).get(StationsViewModel.class);

        viewModel.getStations().observe(this, new Observer<Result<ArrayList<ModelStation>>>() {
            @Override
            public void onChanged(Result<ArrayList<ModelStation>> result) {
                switch (result.status) {
                    case ERROR:
                        LoaderUtils.stopLoading();
                        Snackbar.make(binding.getRoot(), result.message, Snackbar.LENGTH_LONG).show();
                        break;
                    case SUCCESS:
                        LoaderUtils.stopLoading();
                        setRecyclerAdapter(result.data);
                        break;
                    case LOADING:
                        LoaderUtils.startLoadingPleaseWait(StationListActivity.this);
                        break;
                }
            }
        });
        if (viewModel.getStations().getValue().data.isEmpty())
            viewModel.fetchStations(MyStationsApp.getInstance().url);
    }

    private void setRecyclerAdapter(ArrayList<ModelStation> list) {
        Log.d("TAG", "" + list.size());
        if (list.isEmpty()) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);

            StationsAdapter adapter = new StationsAdapter(this, list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.rvStations.setLayoutManager(linearLayoutManager);
            binding.rvStations.setAdapter(adapter);
        }
    }
}