package com.example.dad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity  implements View.OnClickListener{
    private Button btnLogin;
    private EditText edtEmail, edtPassword,edtUsername;
    private TextView txtcreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_Signup);
        edtEmail = findViewById(R.id.input_email);
        txtcreate = findViewById(R.id.link_Login);
        edtPassword = findViewById(R.id.input_password);
        edtUsername=findViewById(R.id.input_Username);
        btnLogin.setOnClickListener(Login.this);

    }

    @Override
    public void onClick(View v) {
        ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), (new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null && user != null) {
                    FancyToast.makeText(Login.this, "Logged in", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    trasnist();


                } else {
                    FancyToast.makeText(Login.this, "Error"
                            , FancyToast.LENGTH_LONG, FancyToast.ERROR,
                            true).show();

                }
            }
        }));


    }
    private void trasnist()
    {
        startActivity(new Intent(Login.this,EnterData.class));
             finish();
    }


}

