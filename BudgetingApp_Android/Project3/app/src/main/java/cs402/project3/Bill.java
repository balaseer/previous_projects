package cs402.project3;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by itsmebadr on 4/17/17.
 */

public class Bill extends RealmObject implements Serializable{

    @Ignore
    static int count2 = 0;
    @PrimaryKey
    private int userID = 0;
    private String name;
    private double amount;
    private Date dueDate;
    private Date reminderDate;
    @Ignore
    private int imageResId;
    private boolean isMonthly;


    public Bill(){
        super();
    }

    public Bill(String name, double amount, Date dueDate, Date reminderDate){

        this.name = name;
        this.amount = amount;
        this.dueDate = dueDate;
        this.reminderDate = reminderDate;
        count2++;
        userID = count2;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public  Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public void setMonthly(boolean monthly) {
        isMonthly = monthly;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
