package com.example.aluno2017.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText UserEmail, UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        UserEmail = findViewById(R.id.UserEmail);
        UserPassword = findViewById(R.id.UserPassword);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.Regist).setOnClickListener(this);
        findViewById(R.id.ButtonLogin).setOnClickListener(this);
    }

    private void userLogin(){

        String Email = UserEmail.getText().toString().trim();
        String Password = UserPassword.getText().toString().trim();



        if(Email.isEmpty()){
            UserEmail.setError("Valid Email Required!");
            UserEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            UserEmail.setError("Please enter a valid email.");
            UserEmail.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            UserPassword.setError("Password Required!");
            UserPassword.requestFocus();
            return;
        }

        if(UserPassword.length() < 6) {
            UserPassword.setError("Minimum length of password should be 6");
            UserPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() !=null) {
            finish();
            startActivity(new Intent(this, DrawerActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Regist:
                finish();
                startActivity(new Intent(this, MainActivity.class));

                break;

            case R.id.ButtonLogin:

                userLogin();

                break;
        }
    }
}
