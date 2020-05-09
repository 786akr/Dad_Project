package com.example.dad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class UserPost extends AppCompatActivity {

   private ImageView profile;
     private  TextView  Interest,edtName,edtPercentage,edtPhoneNumber, edtAmount, edtAddress;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_post);
        edtName = findViewById(R.id.name);
        edtAmount = findViewById(R.id.Amount);
        edtAddress = findViewById(R.id.Address);
        edtPhoneNumber = findViewById(R.id.PhoneNumber);
        edtPercentage = findViewById(R.id.percentage);
        profile = findViewById(R.id.imageView);
        Interest=findViewById(R.id.inter);
        Intent receviedIntentobject = getIntent();
        final String receviedUsername = receviedIntentobject.getStringExtra("Username");
        Intent done=getIntent();
        final String Objectid = done.getStringExtra("objectId");
        setTitle(receviedUsername + "'post");
        //   FancyToast.makeText(UserPost.this,Objectid+"done",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
        query.getInBackground(Objectid+"", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score


                                String name = (String) object.get("Username");
                                String address = (String) object.get("Address");
                                double phoneNumber = (Double) object.getDouble("PhoneNumber");
                                int percent = (int) object.getInt("Percentage");
                                double amount = (double) object.getDouble("Amount");
                                double  interest =amount*percent/100;
                                //  FancyToast.makeText(UserPost.this, name + "SUCCESSFULLY UPLOAD", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                edtName.setText(name);
                                edtPhoneNumber.setText("" + phoneNumber);
                                edtPercentage.setText(percent + "");
                                edtAmount.setText(amount + "");
                                edtAddress.setText(address + "");
                                Interest.setText(interest+"");

                    ParseFile postPicture = (ParseFile) object.get("Image");
                    postPicture.getDataInBackground(new GetDataCallback() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (data != null && e == null) {
                                Bitmap decoding = BitmapFactory.decodeByteArray(data,
                                        0, data.length);
                                   profile.setImageBitmap(decoding);
                            } else {
                                // something went wrong
                                FancyToast.makeText(UserPost.this, "Something Went Wrong", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            }
                        }
                    });
                }
            }
        });
    }
}