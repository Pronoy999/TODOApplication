package com.pronoy.mukhe.todoapplication.Acitvities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTodoDialog extends AppCompatActivity {
    private static final String TAG_CLASS = AddTodoDialog.class.getSimpleName();
    AppCompatEditText _title, _desc, _date, _time;
    AppCompatCheckBox _isReminder;
    AppCompatSpinner _category, _priority;
    AppCompatButton _saveButton, _discardButton;
    ConstraintLayout constraintLayout;

    String priority, category;
    boolean isDateSelected, isTimeSelected = false;
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> priorityList = new ArrayList<>();

    int yearSelected, monthSelected, daySelected, hourSelected, minuteSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_dialog);
        initializeViews();
        constraintLayout.setVisibility(View.GONE);
        addCategoryToList();
        addPriorityToList();
        _isReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    constraintLayout.setVisibility(View.VISIBLE);
                } else constraintLayout.setVisibility(View.GONE);
            }
        });
        _priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priority = adapterView.getItemAtPosition(i).toString();
                Messages.snackbar(view, "Priority Selected: " + priority, "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Messages.toastMessage(getApplicationContext(), "Please select a Priority.", "");
            }
        });
        _category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
                Messages.snackbar(view, "Category selected: " + category, "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Messages.toastMessage(getApplicationContext(), "Please select a Priority.", "");
            }
        });
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                isDateSelected = true;
                yearSelected = year;
                monthSelected = monthOfYear;
                daySelected = dayOfMonth;
                isDateSelected = true;
                _date.setText(String.valueOf(dayOfMonth + "-" + monthOfYear + "-" + year));
            }
        };
        _date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(AddTodoDialog.this, dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
        final TimePickerDialog.OnTimeSetListener timeSetListener = new
                TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        isTimeSelected = true;
                        hourSelected = hour;
                        minuteSelected = minute;
                        _time.setText(String.valueOf(hour + ":" + minute));
                    }
                };
        _time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(AddTodoDialog.this, timeSetListener,
                        calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE), true);
            }
        });
        _saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_isReminder.isChecked()) {
                    if (!_date.getText().toString().equalsIgnoreCase("") &&
                            !_time.getText().toString().equalsIgnoreCase("") &&
                            !category.equalsIgnoreCase("") &&
                            !priority.equalsIgnoreCase("")) {
                        saveReminder(true);
                    } else {
                        Messages.toastMessage(getApplicationContext(),
                                "Please select a date and time.", "");
                    }
                } else if (!category.equalsIgnoreCase("") &&
                        !priority.equalsIgnoreCase("")) {
                    saveReminder(false);
                }
                else {
                    Messages.toastMessage(getApplicationContext(),
                            "Please select all the fields.","");
                }
            }
        });
        _discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: finish.
            }
        });
    }

    private void initializeViews() {
        _title = findViewById(R.id.todoTitleEnter);
        _desc = findViewById(R.id.todoDescEnter);
        _isReminder = findViewById(R.id.isReminder);
        _date = findViewById(R.id.date);
        _time = findViewById(R.id.time);
        _category = findViewById(R.id.categoryEnter);
        _priority = findViewById(R.id.priorityEnter);
        _saveButton = findViewById(R.id.saveButton);
        _discardButton = findViewById(R.id.discardButton);
        constraintLayout = findViewById(R.id.showPicker);
    }

    /**
     * This is the method to set all the categories to the Array List.
     */
    private void addCategoryToList() {
        JSONArray categories = Constants.databaseController.getAllCategories();
        categoryList.add("Categories:");//Add the heading.
        try {
            for (int i = 0; i < categories.length(); i++) {
                JSONObject category = categories.getJSONObject(i);
                categoryList.add(category.getString(Constants.CATEGORY_TABLE_DESC));
            }
        } catch (JSONException e) {
            Messages.logMessage(TAG_CLASS, e.toString());
        }
    }

    /**
     * This is the method to add the Priority to the list.
     */
    private void addPriorityToList() {
        priorityList.add("Priority:");//Adding the first heading.
        for (int i = 0; i < 5; i++) {
            priorityList.add(String.valueOf(i + 1));
        }
    }

    /**
     * Method to set the adapters for the spinners.
     */
    private void setAdapters() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, categoryList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView,
                                        @NonNull ViewGroup parent) {
                View view1 = getDropDownView(position, convertView, parent);
                AppCompatTextView textView = (AppCompatTextView) view1;
                if (position == 0)
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);
                return view1;
            }
        };
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item);
        _category.setAdapter(categoryAdapter);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, priorityList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView,
                                        @NonNull ViewGroup parent) {
                View view = getDropDownView(position, convertView, parent);
                AppCompatTextView textView = (AppCompatTextView) view;
                if (position == 0)
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        priorityAdapter.setDropDownViewResource(R.layout.spinner_item);
        _priority.setAdapter(priorityAdapter);
    }

    /**
     * Method to insert data to Database and save the reminder.
     * @param isReminder: true, if there will be a notification, else false.
     */
    private void saveReminder(boolean isReminder) {
        try {
            String title = _title.getText().toString();
            String desc = _desc.getText().toString();
            int categoryId = Constants.databaseController.getCategoryID(category)
                    .getInt(Constants.CATEGORY_TABLE_ID);
            ContentValues values = new ContentValues();
            values.put(Constants.TODO_TABLE_TITLE, title);
            values.put(Constants.TODO_TABLE_DESC, desc);
            values.put(Constants.TODO_TABLE_PRIOROTY, Integer.valueOf(priority));
            values.put(Constants.TODO_TABLE_CATEGORYID, categoryId);
            if (isReminder) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(yearSelected, monthSelected, daySelected, hourSelected, minuteSelected);
                long reminderTime = calendar.getTimeInMillis();
                values.put(Constants.TODO_TABLE_TIME_MILIS,reminderTime);
            }
            else{
                values.put(Constants.TODO_TABLE_TIME_MILIS,0);
            }
            if(Constants.databaseController.insertDataTodo(values)<0){
                Messages.toastMessage(getApplicationContext(),"Couldn't save Reminder.","");
                return;
            }
            Messages.toastMessage(getApplicationContext(),"Reminder saved.","");
        }catch (NullPointerException | JSONException e){
            Messages.logMessage(TAG_CLASS,e.toString());
            Messages.toastMessage(getApplicationContext(),"Couldn't save Reminder.","");
        }
        //TODO: Set Intent and finish.
    }
}
