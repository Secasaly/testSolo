package com.example.testsolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtEmail, edtFname, edtLname;
    Button btnRegister, btnLogin;
    appDB appDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        appDB appDb = new appDB(this);

        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        edtEmail=findViewById(R.id.edtEmail);
        edtFname=findViewById(R.id.edtFname);
        edtLname=findViewById(R.id.edtLname);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edtUsername.getText().toString();
                String password=edtPassword.getText().toString();
                String email=edtEmail.getText().toString();
                String fname=edtFname.getText().toString();
                String lname=edtLname.getText().toString();
                if(username.isEmpty() && password.isEmpty() && email.isEmpty() && fname.isEmpty() && lname.isEmpty()){
                    Toast.makeText(Register.this, "ALL FIELDS ARE REQUIRED", Toast.LENGTH_SHORT).show();
                }else{
                    boolean isInserted = appDb.insertUser(username, password, email, fname, lname);
                    if(isInserted){
                        Toast.makeText(Register.this, "Registration SUCESS * * * ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Register.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Registration Failed !!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}