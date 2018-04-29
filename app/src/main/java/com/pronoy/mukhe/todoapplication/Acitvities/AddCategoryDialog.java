package com.pronoy.mukhe.todoapplication.Acitvities;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.R;

public class AddCategoryDialog extends AppCompatActivity {
    private static final String TAG_CLASS = AddCategoryDialog.class.getSimpleName();

    AppCompatEditText _category;
    AppCompatButton _saveButton, _discardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_dialog);
        initializeViews();
        _saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!_category.getText().toString().equalsIgnoreCase("")) {
                    completeActivity(_category.getText().toString(), true);
                } else Messages.logMessage(TAG_CLASS, "Please fill in the category");
            }
        });
        _discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeActivity("", false);
            }
        });
    }

    /**
     * This is the method to initialize the views.
     */
    private void initializeViews() {
        _category = findViewById(R.id.categoryDescEnter);
        _saveButton = findViewById(R.id.addCategoryButton);
        _discardButton = findViewById(R.id.discardCategoryButton);
    }

    /**
     * Method to save or not save the Category and return to parent.
     *
     * @param category:       The New Category that is to be saved.
     * @param isCategorySave: true, if you want to save Category, else false.
     */
    private void completeActivity(String category, boolean isCategorySave) {
        Intent returnIntent = new Intent();
        if (isCategorySave) {
            ContentValues values = new ContentValues();
            values.put(Constants.CATEGORY_TABLE_DESC, category);
            if (Constants.databaseController.insertDataCategory(values) < 0) {
                Messages.toastMessage(getApplicationContext(), "Couldn't save Category", "");
                setResult(RESULT_CANCELED, returnIntent);
            } else {
                Messages.toastMessage(getApplicationContext(), "Category Saved.", "");
                setResult(RESULT_OK, returnIntent);
            }
        } else {
            setResult(RESULT_CANCELED, returnIntent);
        }
        finish();
    }
}
