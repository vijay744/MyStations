package com.vijay.mystations.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vijay.mystations.R;
import com.vijay.mystations.core.network.NetworkHelper;
import com.vijay.mystations.core.network.Result;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<Result<ArrayList<String>>> stations;
    private String resultMessage;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        stations = new MutableLiveData<>();
        resultMessage = application.getString(R.string.something_went_wrong);
    }

    public void fetchStations(String url) {
        ArrayList<String> emptyList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                if (e instanceof NetworkError) {
                    resultMessage = application.getString(R.string.please_check_network_connection);
                }
                stations.setValue(Result.error(resultMessage, emptyList));

            }
        });
        NetworkHelper.getInstance(application.getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
