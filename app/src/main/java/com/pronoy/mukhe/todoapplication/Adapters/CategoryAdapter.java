package com.pronoy.mukhe.todoapplication.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Objects.Category;
import com.pronoy.mukhe.todoapplication.R;

import java.util.ArrayList;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the adapter for the Category.
 */

public class CategoryAdapter extends ArrayAdapter {
    ArrayList<Category> categories;
    public CategoryAdapter(Activity context, ArrayList<Category> list){
        super(context,0,list);
        this.categories=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View categoryItem=convertView;
        if(categoryItem==null){
            categoryItem= LayoutInflater.from(getContext()).inflate(R.layout.category_item,
                    parent,
                    false);
            AppCompatTextView _categoryDesc=categoryItem.findViewById(R.id.categoryDesc);
            AppCompatImageView imageView=categoryItem.findViewById(R.id.deleteCategory);
            final Category category=categories.get(position);
            _categoryDesc.setText(category.getCategoryDesc());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categories.remove(category);
                    Constants.databaseController.deleteCategoryByID(category.getCategoryID());
                    notifyDataSetChanged();
                }
            });
        }
        return categoryItem;
    }
}
