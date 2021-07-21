package com.example.pickle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText login, pwd;
    private static final String login_correct = "admin";
    private static final String pwd_correct = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.login_btn);
        login = (EditText) findViewById(R.id.login_et);
        pwd = (EditText) findViewById(R.id.password_et);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        login();
    }

    public void login () {

        btn.setOnClickListener(
                new View.OnClickListener() {
                    private Object Сolor;

                    @Override
                    public void onClick(View view) {
                        if(login_correct.equals(String.valueOf(login.getText())) && pwd_correct.equals(String.valueOf(pwd.getText()))) {
                            login.setTextColor(Color.parseColor("#2ea017"));
                            pwd.setTextColor(Color.parseColor("#2ea017"));
                            Context context = LoginActivity.this;
                            Intent intent = new Intent(context, MapsActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            login.setTextColor(Color.parseColor("#e96868"));
                            pwd.setTextColor(Color.parseColor("#e96868"));
                        }
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {

    }
}
