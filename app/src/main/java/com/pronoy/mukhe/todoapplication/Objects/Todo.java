package com.pronoy.mukhe.todoapplication.Objects;

import java.util.Date;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the object for each To_do.
 */

public class Todo {
    private String title, desc, category;
    private int priority;
    Date date;

    public Todo(String title, String desc, String category, int priority, long date) {
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.priority = priority;
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
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() { return category;  }

    public void setCategory(String category) { this.category = category; }
}
