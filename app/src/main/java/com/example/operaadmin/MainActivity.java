package com.example.operaadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    Spinner spinner;
    Button upload;
    TextView showPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        spinner = findViewById(R.id.category_name);
        upload = findViewById(R.id.upload_btn);
        showPath = findViewById(R.id.title_view);

        final List<String> song_category = new ArrayList<>();
        song_category.add("HipHop");
        song_category.add("Rock");
        song_category.add("Sad");
        song_category.add("Punjabi");
        song_category.add("Back In 90's");
        song_category.add("Love");
        song_category.add("Hindi");
        song_category.add("English");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,song_category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setHasTransientState(true);
        spinner.setAdapter(dataAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("audio/*");
                startActivityForResult(myFileIntent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();
                    showPath.setText(path);
                }
                break;
        }
    }
}