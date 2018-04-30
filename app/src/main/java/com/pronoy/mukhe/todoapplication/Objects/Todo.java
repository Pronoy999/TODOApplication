package com.pronoy.mukhe.todoapplication.Objects;

import com.pronoy.mukhe.todoapplication.Helper.Constants;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the object for each To_do.
 */

public class Todo {
    private String title, desc, category;
    private int priority, id;
    Date date;

    public Todo(String title, String desc, String category, int priority, int id, long date) {
        this.title = title;
        this.desc = desc;
        this.id = id;
        this.category =Constants.databaseController.getCategoryByID(Integer.valueOf(category));
        this.priority = priority;
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(date);
        this.date = new Date(date);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDate() {
        String d=date.toString();
        String parts[]=d.split(":");
        String newDate=parts[0]+parts[1];
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
