package com.example.mobilebanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static String user, pass;
    ImageView imageView;
    EditText editTextUserName;
    EditText editTextPassword;
    static private Handler mainHandler;
    MyHandlerThread myHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.bank);



        mainHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 0){
                    Log.v("Login", "Login");
                    Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SecondaryActivity.class);
                    startActivity(intent);
                } else if(msg.what == 1) {
                    Log.v("Login", "Login Failed");
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public void login(View view){
        user = String.valueOf(editTextUserName.getText());
        pass = String.valueOf(editTextPassword.getText());
        myHandlerThread = new MyHandlerThread(getApplicationContext(), mainHandler);
        myHandlerThread.start();

    }

}