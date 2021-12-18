package com.vijay.mystations.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vijay.mystations.R;
import com.vijay.mystations.core.network.NetworkHelper;
import com.vijay.mystations.core.network.Result;
import com.vijay.mystations.core.utils.XmlParserUtils;
import com.vijay.mystations.data.models.ModelStation;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class FetchStationsRepository {
    private String resultMessage;

    public void fetchStations(String url, Application application, MutableLiveData<Result<ArrayList<ModelStation>>> stations) {
        resultMessage = application.getString(R.string.something_went_wrong);

        ArrayList<ModelStation> emptyList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<ModelStation> list = XmlParserUtils.parseStationData(response);
                            stations.postValue(Result.success(list));
                        } catch (XmlPullParserException | IOException | SAXException e) {
                            stations.postValue(Result.error(application.getString(R.string.something_went_wrong_url), emptyList));

                        }
                    }
                }).start();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d("TAG",e.toString());

                stations.setValue(Result.error(resultMessage, emptyList));

            }
        });
        NetworkHelper.getInstance(application.getApplicationContext()).addToRequestQueue(stringRequest);
        stations.setValue(Result.loading(null));
    }
}
