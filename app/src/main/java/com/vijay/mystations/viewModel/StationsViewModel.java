package com.vijay.mystations.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vijay.mystations.R;
import com.vijay.mystations.core.app.MyStationsApp;
import com.vijay.mystations.core.network.Result;
import com.vijay.mystations.data.repositories.FetchStationsRepository;
import com.vijay.mystations.data.models.ModelStation;

import java.util.ArrayList;

public class StationsViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<Result<ArrayList<ModelStation>>> stations;
    private FetchStationsRepository repository;

    public StationsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        stations = new MutableLiveData<>();
        stations.setValue(Result.success(new ArrayList<ModelStation>()));
        this.repository = new FetchStationsRepository();
    }

    public void fetchStations(String url) {
        repository.fetchStations(url, application, stations);
    }

    public LiveData<Result<ArrayList<ModelStation>>> getStations() {
        return stations;
    }
}
