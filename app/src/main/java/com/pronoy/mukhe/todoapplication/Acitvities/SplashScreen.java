package com.pronoy.mukhe.todoapplication.Acitvities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.DatabaseController;
import com.pronoy.mukhe.todoapplication.Objects.Category;
import com.pronoy.mukhe.todoapplication.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Constants.databaseController=new DatabaseController(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
                boolean isLoggedIn=sharedPreferences.getBoolean(Constants.LOG_IN_STATUS,false);
                Intent changeActivityIntent;
                if(!isLoggedIn){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean(Constants.LOG_IN_STATUS,true);
                    editor.apply();
                    changeActivityIntent=new Intent(SplashScreen.this, AddCategoryActivity.class);
                }
        else
                changeActivityIntent=new Intent(SplashScreen.this,TodoActivity.class);
                startActivity(changeActivityIntent);
            }
        },2000);
    }
}
