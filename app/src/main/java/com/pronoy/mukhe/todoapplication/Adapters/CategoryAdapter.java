package com.pronoy.mukhe.todoapplication.Adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.pronoy.mukhe.todoapplication.Objects.Category;

import java.util.ArrayList;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the adapter for the Category.
 */

public class CategoryAdapter extends ArrayAdapter {
    ArrayList<Category> categories;
    public CategoryAdapter(Activity context, ArrayList<Category> list){
        super(context,0,list);
    }

}
