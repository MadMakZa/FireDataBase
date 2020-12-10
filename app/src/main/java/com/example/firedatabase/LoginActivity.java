package com.example.firedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    private Button bStart, bSignUp, bSignIn, bSignOut;
    private TextView tvUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
    }

    private void init(){
        edLogin = findViewById(R.id.edLogin);
        edPassword = findViewById(R.id.edPassword);
        mAuth = FirebaseAuth.getInstance();
        bStart = findViewById(R.id.bStart);
        bSignIn = findViewById(R.id.bSignIn);
        bSignUp = findViewById(R.id.bSignUp);
        bSignOut = findViewById(R.id.bSignOut);
        tvUserName = findViewById(R.id.tvUserName);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //проверка - зарегистрирован пользователь с таким именем или нет
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null){
            showSigned();
            String userName = "Вы вошли как : " + cUser.getEmail();
            tvUserName.setText(userName);

            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        } else {
            notSigned();

            Toast.makeText(this, "User null!", Toast.LENGTH_SHORT).show();
        }
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
                                showSigned();
                                sendEmailVer();
                                Toast.makeText(getApplicationContext(), "User SignUp Successeful", Toast.LENGTH_SHORT).show();

                            } else {
                                notSigned();
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
                                showSigned();
                                Toast.makeText(getApplicationContext(), "User SignIn Successeful", Toast.LENGTH_SHORT).show();

                            } else {
                                notSigned();
                                Toast.makeText(getApplicationContext(), "User SignIn Failed", Toast.LENGTH_SHORT).show();
                            }
                }
            });
        }
    }
    //выйти из аккаунта
    public void onClickSignOut(View view){
        FirebaseAuth.getInstance().signOut();
        notSigned();
    }
    //если пользователь зарегистрирован
    private void showSigned(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        if(user.isEmailVerified()) {
            String userName = "Вы вошли как : " + user.getEmail();
            tvUserName.setText(userName);

            bStart.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.VISIBLE);
            bSignOut.setVisibility(View.VISIBLE);
            edLogin.setVisibility(View.GONE);
            edPassword.setVisibility(View.GONE);
            bSignIn.setVisibility(View.GONE);
            bSignUp.setVisibility(View.GONE);
        }else{
            Toast.makeText(getApplicationContext(), "Проверьте вашу почту для подтверждения Email", Toast.LENGTH_SHORT).show();
        }
    }
    //если полльзователь не зарегестрирован
    private void notSigned(){
        bStart.setVisibility(View.GONE);
        tvUserName.setVisibility(View.GONE);
        bSignOut.setVisibility(View.GONE);
        edLogin.setVisibility(View.VISIBLE);
        edPassword.setVisibility(View.VISIBLE);
        bSignIn.setVisibility(View.VISIBLE);
        bSignUp.setVisibility(View.VISIBLE);
    }
    public void onClickStart(View view){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
    //проверка верификации
    private void sendEmailVer(){
        FirebaseUser user = mAuth.getCurrentUser();

        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Проверьте вашу почту для подтверждения Email", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Send Enail failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
