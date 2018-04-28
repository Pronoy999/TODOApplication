package com.pronoy.mukhe.todoapplication.Acitvities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import com.pronoy.mukhe.todoapplication.Adapters.TodoAdapter;
import com.pronoy.mukhe.todoapplication.Helper.Constants;
import com.pronoy.mukhe.todoapplication.Helper.Messages;
import com.pronoy.mukhe.todoapplication.Objects.Todo;
import com.pronoy.mukhe.todoapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Calendar;

public class TodoActivity extends AppCompatActivity {

    ListView todoList;
    FloatingActionButton floatingActionButton;

    private static final String TAG_CLASS = TodoActivity.class.getSimpleName();

    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> priorityList = new ArrayList<>();

    AppCompatSpinner _category;
    AppCompatSpinner _priority;
    String priority, category;
    private static boolean isDateSelected = false;
    private static boolean isTimeSelected = false;
    int yearSelected, monthSelected, daySelected, hourSelected, minuteSelected;

    Calendar reminder = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initializeViews();
        getAllData();
        addCategoryToList();
        addPriorityToList();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAddTodoDialog();
                showDialog(69);
                Messages.logMessage(TAG_CLASS,"Button PRESSED.");
            }
        });
    }

    private void initializeViews() {
        todoList = findViewById(R.id.todoList);
        floatingActionButton = findViewById(R.id.addTodo);
    }

    /**
     * This is the method to get all the TO_DO from the Database.
     */
    private void getAllData() {
        JSONArray todoArray = Constants.databaseController.getAllTodo();
        ArrayList<Todo> arrayList = new ArrayList<>();
        Todo todo;
        for (int i = 0; i < todoArray.length(); i++) {
            try {
                JSONObject todoObject = todoArray.getJSONObject(i);
                todo = new Todo(todoObject.getString(Constants.TODO_TABLE_TITLE),
                        todoObject.getString(Constants.TODO_TABLE_DESC),
                        todoObject.getString(Constants.CATEGORY_TABLE_DESC),
                        todoObject.getInt(Constants.TODO_TABLE_PRIOROTY),
                        todoObject.getInt(Constants.TODO_TABLE_ID),
                        todoObject.getInt(Constants.TODO_TABLE_TIME_MILIS));
                arrayList.add(todo);

            } catch (JSONException e) {
                Messages.logMessage(TAG_CLASS, e.toString());
            }
        }
        TodoAdapter adapter = new TodoAdapter(TodoActivity.this, arrayList);
        todoList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * This is the method to display the add TO_DO dialog.
     */
    private void showAddTodoDialog() {
        final Dialog dialog = new Dialog(TodoActivity.this);

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
     * Method to set the TO_DO without the reminder.
     *
     * @param title: The title of the TO_DO.
     * @param desc:  The Description of the TO_DO.
     */
    private void setTodoWithoutReminder(String title, String desc) {
        JSONObject category=Constants.databaseController
                .getCategoryID(_category.getSelectedItem().toString());
        int categoryID=-1;
        try {
            categoryID = category.getInt(Constants.CATEGORY_TABLE_ID);
        }catch (JSONException e){
            Messages.logMessage(TAG_CLASS,e.toString());
        }
        ContentValues values=new ContentValues();
        values.put(Constants.TODO_TABLE_TITLE,title);
        values.put(Constants.TODO_TABLE_DESC,desc);
        values.put(Constants.TODO_TABLE_PRIOROTY,Integer.valueOf(_priority.getSelectedItem().toString()));
        values.put(Constants.TODO_TABLE_CATEGORYID,categoryID);
        reminder.set(yearSelected,monthSelected,daySelected,hourSelected,minuteSelected);
        values.put(Constants.TODO_TABLE_TIME_MILIS,String.valueOf(reminder.getTimeInMillis()));
        long id=Constants.databaseController.insertDataTodo(values);
        if(id<0){
            Messages.snackbar(getCurrentFocus(),"Sorry, Couldn't add todo.","");
            return;
        }
        Messages.toastMessage(getApplicationContext(),"Todo Added.","");
    }

    private void setTodoWithReminder(String title, String desc) {

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.add_category_dialog,null);
        builder.setView(view);
        final AppCompatEditText _title = view.findViewById(R.id.todoTitleEnter);
        final AppCompatEditText _desc = view.findViewById(R.id.todoDescEnter);
        final AppCompatCheckBox _isReminder = view.findViewById(R.id.isReminder);
        final ConstraintLayout pickerLayout = view.findViewById(R.id.showPicker);
        //pickerLayout.setVisibility(View.GONE);
        final AppCompatTextView _date = view.findViewById(R.id.date);
        final AppCompatTextView _time = view.findViewById(R.id.time);
        _category = view.findViewById(R.id.categoryEnter);
        _priority = view.findViewById(R.id.priorityEnter);
        AppCompatButton _saveButton =view.findViewById(R.id.saveButton);
        AppCompatButton _discardButton = view.findViewById(R.id.discardButton);
        setAdapters();
        _isReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    pickerLayout.setVisibility(View.VISIBLE);
                } else
                    pickerLayout.setVisibility(View.GONE);
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
                new DatePickerDialog(TodoActivity.this, dateSetListener,
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
                new TimePickerDialog(TodoActivity.this, timeSetListener,
                        calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE), true);
            }
        });
        _saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_isReminder.isChecked()) {
                    if (!_date.getText().toString().equalsIgnoreCase("") &&
                            !_time.getText().toString().equalsIgnoreCase("")) {
                        setTodoWithReminder(_title.getText().toString(), _desc.getText().toString());
                    }
                } else {
                    setTodoWithoutReminder(_title.getText().toString(), _desc.getText().toString());
                }
            }
        });
        _discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog(69);
            }
        });
        return builder.create();
    }
}