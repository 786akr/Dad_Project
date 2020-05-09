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

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity  implements  View.OnClickListener {
    private Button btnLogin;
    private EditText edtEmail, edtPassword,edtUsername;
    private TextView txtcreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();// build a connection with parse server
        btnLogin = findViewById(R.id.btn_Signup);
        edtEmail = findViewById(R.id.input_email);
        txtcreate = findViewById(R.id.link_Login);
        edtPassword = findViewById(R.id.input_password);
        edtUsername=findViewById(R.id.input_Username);
          btnLogin.setOnClickListener(MainActivity.this);


    }


    @Override
    public void onClick(View v) {
        ParseUser Sign=new ParseUser();
        Sign.setPassword(edtPassword.getText().toString());
        Sign.setEmail(edtEmail.getText().toString());
        Sign.setUsername(edtUsername.getText().toString());
        Sign.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    FancyToast.makeText(MainActivity.this,"Done",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                      transfer();
                      finish();
                }
            }
        });
    }

    private void transfer() {
        startActivity(new Intent(MainActivity.this,EnterData.class));
        finish();
    }

    public void Transist(View view) {

        startActivity(new Intent(MainActivity.this,Login.class));
             finish();
    }
}
