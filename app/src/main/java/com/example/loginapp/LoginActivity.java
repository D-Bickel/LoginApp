package com.example.loginapp;

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

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText reEnter;

    Button login;
    Button register;
    Button goBack;

    User tempUser;
    DatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        tempUser = new User();

        dbHelper = new DatabaseHandler(this);

        findAll();
        setListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void findAll() {

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        reEnter = findViewById(R.id.reEnter);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
    }

    void setListeners() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tempUser.setUsername(username.getText().toString());
                tempUser.setPassword(password.getText().toString());
                String reEnterTemp = reEnter.getText().toString();

                boolean success;

                if (tempUser.getUsername().isEmpty() || tempUser.getPassword().isEmpty() || reEnterTemp.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if (!tempUser.getPassword().equals(reEnterTemp)){

                    Toast.makeText(LoginActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                }
                else if (dbHelper.tryLogin(tempUser.getUsername(), tempUser.getPassword())) {

                    setContentView(R.layout.activity_home);
                    goBack = findViewById(R.id.go_back);

                    goBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            setContentView(R.layout.activity_login);
                            findAll();
                            setListeners();
                        }
                    });
                }
                else {

                    Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tempUser.setUsername(username.getText().toString());
                tempUser.setPassword(password.getText().toString());
                String reEnterTemp = reEnter.getText().toString();

                boolean success;

                if (tempUser.getUsername().isEmpty() || tempUser.getPassword().isEmpty() || reEnterTemp.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if (!tempUser.getPassword().equals(reEnterTemp)){

                    Toast.makeText(LoginActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                }
                else if (dbHelper.checkUsername(tempUser.getUsername())) {

                    Toast.makeText(LoginActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                }
                else {

                    success = dbHelper.insertData(tempUser.getUsername(), tempUser.getPassword());

                    if (success) {

                        Toast.makeText(LoginActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(LoginActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}