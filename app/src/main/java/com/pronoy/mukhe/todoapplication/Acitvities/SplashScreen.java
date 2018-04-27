package com.pronoy.mukhe.todoapplication.Acitvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.DatabaseController;
import com.pronoy.mukhe.todoapplication.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Constants.databaseController=new DatabaseController(getApplicationContext());
    }
}
