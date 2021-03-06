package com.example.firedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText edName, edSecondName, edEmail;
    private DatabaseReference mDataBase;
    private StorageReference mStorageRef;
    private String USER_KEY = "User";
    private ImageView imImage;
    private Uri uploadUri;

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
        imImage = findViewById(R.id.imImage);
        //создание базы данных
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        //хранилище ссылок
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDB");
    }
    //Сохранить пользователя с введенными данными
    private void saveUser(){
        String id = mDataBase.push().getKey(); //получаем ключ с базы данных
        //записываем введенный текст в переменные
        String name = edName.getText().toString();
        String secondName = edSecondName.getText().toString();
        String email = edEmail.getText().toString();
        //создание нового пользователя с введенными выше параметрами
        User newUser = new User(id,name,secondName,email, uploadUri.toString());
        //вносим нового пользователя в базу данных если поля заполнены
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(secondName) && !TextUtils.isEmpty(email)){
            if(id != null) mDataBase.child(id).setValue(newUser);
            Toast.makeText(this,"Сохранено", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Пустое поле", Toast.LENGTH_SHORT).show();
        }
    }
    //Запись в базу данных
    public void onClickSave(View view){
        uploadImage();

    //Чтение с базы данных
    }
    public void onClickRead(View view){
        Intent i = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(i);
    }

    /**
     * Методы по управлению загрузкой картинок
     */

    //кнопка выбрать картинку
    public void onClickChooseImage(View view){
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData() != null){
            if(resultCode == RESULT_OK){
                //получаем путь картинки если всё ок
                Log.d("MyLog", "Image URL : " + data.getData());
                imImage.setImageURI(data.getData());

            }

        }
    }

    private void uploadImage(){
        Bitmap bitmap = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child(System.currentTimeMillis() + "my_image");
        //метод загрузки
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>(){
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult(); //ссылку на картинку сохраняем в переменную
                saveUser();
            }
        });


    }

    //метод выбора картинки
    private void getImage(){
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

}