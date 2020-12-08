package com.example.firedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    private void init(){
        edName = findViewById(R.id.edName);
        edSecondName = findViewById(R.id.edSecondName);
        edEmail = findViewById(R.id.edEmail);
        //создание базы данных
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    //Запись в базу данных
    public void onClickSave(View view){
        String id = mDataBase.getKey(); //получаем ключ с базы данных
        //записываем введенный текст в переменные
        String name = edName.getText().toString();
        String secondName = edSecondName.getText().toString();
        String email = edEmail.getText().toString();
        //создание нового пользователя с введенными выше параметрами
        User newUser = new User(id,name,secondName,email);
        //вносим нового пользователя в базу данных если поля заполнены
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(secondName) && !TextUtils.isEmpty(email)){
            mDataBase.push().setValue(newUser);
            Toast.makeText(this,"Сохранено", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Пустое поле", Toast.LENGTH_SHORT).show();
        }

    //Чтение с базы данных
    }
    public void onClickRead(View view){
        Intent i = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(i);
    }
}