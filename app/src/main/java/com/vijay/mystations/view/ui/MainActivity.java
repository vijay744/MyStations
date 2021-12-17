package com.vijay.mystations.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.vijay.mystations.R;
import com.vijay.mystations.databinding.ActivityMainBinding;
import com.vijay.mystations.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private View rootView;
    private final String URL = "http://phorus.vtuner.com/setupapp/phorus/asp/browsexml/navXML.asp?gofile=LocationLevelFourCityUS-North%20America-California-ExtraDir-1-Inner-11&bkLvl=9237&mac=eacc7109f843b7fad7b7dd2e2b6ebafbd64cb9b9b2134e2f24933c865ff6e94a&dlang=eng&fver=7.1.0.4697%20(debug-19:09:47)&hw=CAP:%207.1.0.082%20MCU:%202.135";
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();

        setContentView(rootView);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initListener();
    }

    private void initListener() {
        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = binding.etEnterUrl.getText().toString().trim();
                if (url.isEmpty()) {
                    binding.tipUrl.setError(getString(R.string.url_should_not_be_empty));
                } else {
                    binding.tipUrl.setErrorEnabled(false);
                    viewModel.fetchStations(URL);
                }
            }
        });
        binding.etEnterUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    binding.tipUrl.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleBackEvent() {
        if (binding.rlUrlView.getVisibility() == View.VISIBLE) {
        } else {
            binding.rlUrlView.setVisibility(View.VISIBLE);
            binding.rvStations.setVisibility(View.GONE);
            binding.ibBack.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handleBackEvent();
    }
}