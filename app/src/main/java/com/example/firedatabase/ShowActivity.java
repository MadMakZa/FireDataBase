package com.example.firedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    private TextView tvName, tvSecondName, tvEmail;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
    }

    private void init(){
        tvName = findViewById(R.id.tvName);
        tvSecondName = findViewById(R.id.tvSecondName);
        tvEmail = findViewById(R.id.tvEmail);
    }

    private void getIntentMain(){
        Intent i = getIntent();
        if(i != null){
            tvName.setText(i.getStringExtra("user_name"));
            tvSecondName.setText(i.getStringExtra("user_sec_name"));
            tvEmail.setText(i.getStringExtra("user_email"));
        }
    }
}

