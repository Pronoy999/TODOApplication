package com.pronoy.mukhe.todoapplication.Acitvities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ListView;

import com.pronoy.mukhe.todoapplication.Adapters.CategoryAdapter;
import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.Objects.Category;
import com.pronoy.mukhe.todoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ListView todoList;
    FloatingActionButton floatingActionButton;
    CategoryAdapter categoryAdapter;
    private static final String TAG_CLASS = CategoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        setTitle( getResources().getString(R.string.categoryTitle));
        initializeViews();
        getDataFromDatabase();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCategoryIntent=new Intent(CategoryActivity.this,AddCategoryDialog.class);
                startActivityForResult(addCategoryIntent,Constants.ADD_CATEGORY_DIALOG_REQUEST_CODE);
            }
        });
    }

    private void initializeViews() {
        todoList = findViewById(R.id.categoryList);
        floatingActionButton = findViewById(R.id.addCategory);
    }

    /**
     * This is the method to get the data from the database and display it.
     */
    private void getDataFromDatabase() {
        JSONArray categories = Constants.databaseController.getAllCategories();
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        Category category;
        try {
            for (int i = 0; i < categories.length(); i++) {
                JSONObject jsonObject = categories.getJSONObject(i);
                category = new Category(jsonObject.getString(Constants.CATEGORY_TABLE_DESC),
                        jsonObject.getInt(Constants.CATEGORY_TABLE_ID));
                categoryArrayList.add(category);
            }
        } catch (JSONException e) {
            Messages.logMessage(TAG_CLASS, e.toString());
        }
        categoryAdapter = new CategoryAdapter(CategoryActivity.this,
                categoryArrayList);
        todoList.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.ADD_CATEGORY_DIALOG_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                getDataFromDatabase();
            }
            else{
                Messages.toastMessage(getApplicationContext(),"Couldn't add Category.","");
            }
        }
    }
}
