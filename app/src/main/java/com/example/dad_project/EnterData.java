package com.example.dad_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Retention;

public class EnterData extends AppCompatActivity  implements View.OnClickListener {

    private EditText edtName, edtPhoneNumber, edtAmount, edtPercentage, edtAddress;
    Button btnsave;
    ImageView profile;
    Bitmap reeciveImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        edtName = findViewById(R.id.name);
        edtAmount = findViewById(R.id.Amount);
        edtAddress = findViewById(R.id.Address);
        edtPhoneNumber = findViewById(R.id.PhoneNumber);
        edtPercentage = findViewById(R.id.percentage);
        btnsave = findViewById(R.id.Save);
        profile = findViewById(R.id.imageView);
        profile.setOnClickListener(EnterData.this);
        btnsave.setOnClickListener(EnterData.this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                if (android.os.Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(EnterData.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                } else {
                    getChosenImage();

                }
                break;

            case R.id.Save:
                if (reeciveImageBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    reeciveImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] image = stream.toByteArray();
                    // Create the ParseFile
                    ParseFile file = new ParseFile( edtName.getText().toString()+".png", image);
                    // Upload the image into Parse Cloud
                    file.saveInBackground();
                    // Create a New Class called "ImageUpload" in Parse
                    ParseObject imgupload = new ParseObject("UserData");
                    imgupload.put("Image", file);
                    imgupload.put("Username", edtName.getText().toString());
                    imgupload.put("Address", edtAddress.getText().toString());
                    imgupload.put("PhoneNumber", Integer.parseInt(edtPhoneNumber.getText().toString()));
                    imgupload.put("Amount", Integer.parseInt(edtAmount.getText().toString()));
                    imgupload.put("Percentage", Integer.parseInt(edtPercentage.getText().toString()));
                    final ProgressDialog process = new ProgressDialog(EnterData.this);
                    process.setMessage("Please wait");
                    process.show();
                    imgupload.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(EnterData.this, "IMAGE IS UPLOAD ", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                process.dismiss();
                                FancyToast.makeText(EnterData.this,""+Integer.parseInt(edtAmount.getText().toString())*Integer.parseInt(edtPercentage.getText().toString())
                            }
                        }
                    });

                }else {
                FancyToast.makeText(EnterData.this, "you Must Select an Image", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            }
        }

    }
    private void getChosenImage() {
        Intent intent=new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);


    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode==1000&&grantResults.length >=0 &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getChosenImage();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000 && resultCode== Activity.RESULT_OK){
            try{
                Uri SelectImage=data.getData();
                String[] filepath={MediaStore.Images.Media.DATA};
                Cursor cursor=getContentResolver().query(SelectImage,filepath,
                        null,null,null);
                cursor.moveToFirst();
                int IndexColum=cursor.getColumnIndex(filepath[0]);
                String PicturePath=cursor.getString(IndexColum);
                cursor.close();
                reeciveImageBitmap= BitmapFactory.decodeFile(PicturePath);
                profile.setImageBitmap(reeciveImageBitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
