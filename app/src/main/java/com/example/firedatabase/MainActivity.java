package com.example.firedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edName, edSecondName, edEmail;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        edName = findViewById(R.id.edName);
        edSecondName = findViewById(R.id.edSecondName);
        edEmail = findViewById(R.id.edEmail);
        //создание базы данных
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    //кнопочки
    public void onClickSave(View view){
        String id = mDataBase.getKey(); //получаем ключ с базы данных
        //записываем введенный текст в переменные
        String name = edName.getText().toString();
        String secondName = edSecondName.getText().toString();
        String email = edEmail.getText().toString();
        //создание нового пользователя с введенными выше параметрами
        User newUser = new User(id,name,secondName,email);
        //вносим новго пользователя в базу данных
        mDataBase.push().setValue(newUser);
    }
    public void onClickRead(View view){

    }
}