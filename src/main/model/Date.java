package model;

import java.util.Calendar;

// represents a calendar date
public class Date {
    private Calendar calendar = Calendar.getInstance();
    private int month;
    private int day;
    private int year;

    // EFFECTS: constructs a new Date with current month, day, and year
    public Date() {
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    // EFFECTS: constructs a day with given month, day, year
    // NOTE: to be used when constructing a date from file
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // EFFECTS: returns the month the carbon footprint log was recorded
    public int getMonth() {
        return month;
    }

    // EFFECTS: returns the day of the month the carbon footprint log was recorded
    public int getDay() {
        return day;
    }

    // EFFECTS: returns the year the carbon footprint log was recorded
    public int getYear() {
        return year;
    }

    // MODIFIES: this
    // EFFECTS: update the carbon log with current date
    public void updateDate() {
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
    }



}
