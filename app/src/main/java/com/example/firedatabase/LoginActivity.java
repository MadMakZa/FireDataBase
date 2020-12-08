package com.example.firedatabase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edLogin, edPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
//        onClickSignUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //проверка - зарегистрирован пользователь с таким именем или нет
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null){
            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User null!", Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){
        edLogin = findViewById(R.id.edLogin);
        edPassword = findViewById(R.id.edPass);
        mAuth = FirebaseAuth.getInstance();
    }
    //Зарегистрироваться
    public void onClickSignUp(View view){
        //если поля не пустые то создаем нового юсера
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())){
            mAuth.createUserWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //если всё норм, то пользователь регается
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "User SignUp Successeful", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "User SignUp Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
        }

    }
    //Войти
    public void onClickSignIn(View view){
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            mAuth.signInWithEmailAndPassword(edLogin.getText().toString(), edPassword.getText().toString()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "User SignIn Successeful", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "User SignIn Failed", Toast.LENGTH_SHORT).show();
                            }
                }
            });
        }
    }
}
