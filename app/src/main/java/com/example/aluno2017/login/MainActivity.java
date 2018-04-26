package com.example.aluno2017.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText UserEmail, UserPassword, UserPassword2;
    Dialog myDialog;
    Button button;
    TextView txtclose;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserEmail = findViewById(R.id.UserEmail);
        UserPassword = findViewById(R.id.UserPassword);
        UserPassword2 = findViewById(R.id.UserPassword2);
        progressBar = findViewById(R.id.progressbar);
        myDialog = new Dialog(this);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.RegisterButton).setOnClickListener(this);
    }


    private void registerUser() {
        String Email = UserEmail.getText().toString().trim();
        String Password = UserPassword.getText().toString().trim();
        String CheckPassword = UserPassword2.getText().toString().trim();


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

        if (!CheckPassword.matches(Password)) {
            UserPassword2.setError("Passwords don't match");
            UserPassword2.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword (Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    sendVerificationEmail();
                    openPopUp();
                } else {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public void LoadUserInfo (){


            Intent intent = new Intent(MainActivity.this, SlideActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    public void openPopUp (){

        myDialog.setContentView(R.layout.activity_popup);
        txtclose = myDialog.findViewById(R.id.txtclose);
        button = myDialog.findViewById(R.id.Feito);


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog.dismiss();

            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                LoadUserInfo();

            }
        });

    }


    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        }else {
                            Toast.makeText(getApplicationContext(), "Couldn't Send Verification Email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }

    @Override
    public void onClick(View o) {
    switch (o.getId()) {

        case R.id.LoginActivity:
            startActivity(new Intent (MainActivity.this, LoginActivity.class));

            break;

        case R.id.RegisterButton:
        registerUser();
        sendVerificationEmail();

            break;
    }
  }
}