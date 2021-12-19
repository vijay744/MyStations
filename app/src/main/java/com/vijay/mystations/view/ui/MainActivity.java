package com.vijay.mystations.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.vijay.mystations.R;
import com.vijay.mystations.core.app.MyStationsApp;
import com.vijay.mystations.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private View rootView;
    private final String URL = "http://phorus.vtuner.com/setupapp/phorus/asp/browsexml/navXML.asp?gofile=LocationLevelFourCityUS-North%20America-California-ExtraDir-1-Inner-11&bkLvl=9237&mac=eacc7109f843b7fad7b7dd2e2b6ebafbd64cb9b9b2134e2f24933c865ff6e94a&dlang=eng&fver=7.1.0.4697%20(debug-19:09:47)&hw=CAP:%207.1.0.082%20MCU:%202.135";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();

        setContentView(rootView);
        initListener();
    }

    private void initListener() {

        binding.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = binding.etEnterUrl.getText().toString().trim();
                if (url.isEmpty()) {
                    binding.tipUrl.setError(getString(R.string.url_should_not_be_empty));
                } else if (!Patterns.WEB_URL.matcher(url).matches()) {
                    binding.tipUrl.setError(getString(R.string.enter_valid_url));
                } else {
                    binding.tipUrl.setErrorEnabled(false);
                    MyStationsApp.getInstance().url = url;
                    startActivity(new Intent(MainActivity.this, StationListActivity.class));
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
}