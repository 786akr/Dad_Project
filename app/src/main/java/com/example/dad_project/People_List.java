package com.example.dad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class People_List extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ArrayList<String> List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  setContentView(R.layout.activity_people__list);
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        List = new ArrayList<>();
        listView.setOnItemClickListener(People_List.this);
        adapter = new ArrayAdapter<>(People_List.this, android.R.layout.simple_list_item_1, arrayList);
     //   EnterData   obj=new EnterData();
       // obj.edtName.getText().toString();
        //FancyToast.makeText(People_List.this, obj.edtName.getText().toString()+"", FancyToast.LENGTH_LONG, FancyToast.INFO,
          //      true).show();
        ParseQuery<ParseObject> fetch = new ParseQuery<ParseObject>("UserData");
        // 0fe0tch.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        fetch.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject user : objects) {
                      arrayList.add((String)user.get("Username"));
                         List.add(user.getObjectId());
                    }

                    listView.setAdapter(adapter);
                }
            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(People_List.this, UserPost.class);
        intent.putExtra("Username", arrayList.get(position));
        intent.putExtra("objectId",List.get(position));

        startActivity(intent);
    }
}