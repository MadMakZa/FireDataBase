package com.example.firedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    private TextView tvName, tvSecondName, tvEmail;
    private ImageView imBD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
        getIntentMain();
    }

    private void init(){
        tvName = findViewById(R.id.tvName);
        tvSecondName = findViewById(R.id.tvSecondName);
        tvEmail = findViewById(R.id.tvEmail);
        imBD = findViewById(R.id.imBD);
    }

    private void getIntentMain(){
        Intent i = getIntent();
        if(i != null){
            tvName.setText(i.getStringExtra(Constants.USER_NAME));
            tvSecondName.setText(i.getStringExtra(Constants.USER_SEC_NAME));
            tvEmail.setText(i.getStringExtra(Constants.USER_EMAIL));
        }
    }
}

