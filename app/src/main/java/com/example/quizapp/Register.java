package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText name,mail1,password1,password2;
    Button signup;
    TextView signin;
    FirebaseAuth MyAuthentification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        mail1 = findViewById(R.id.email1);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        MyAuthentification = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail1.getText().toString();
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2)){
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass1.length() < 6){
                    Toast.makeText(Register.this, "Password must be atleast 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pass1.equals(pass2)){
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    signup(email, pass1);
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, MainActivity.class));
                finish();
            }
        });
    }

    public void signup(String email, String password){
        MyAuthentification.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}