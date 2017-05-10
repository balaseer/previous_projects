package cs402.project3;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by itsmebadr on 4/29/17.
 */

public class User extends RealmObject implements Serializable{

    @Ignore
    static int count = 0;
    @PrimaryKey
    int userID = 0;
    private static double monthlyWorth = 0;
    boolean salary;
    boolean hourly;
    boolean tips;



    public User(){
        super();
    }

    public User(double monthlyWorth, boolean salary, boolean hourly, boolean tips){
        this.monthlyWorth = monthlyWorth;
        this.salary = salary;
        this.hourly = hourly;
        this.tips = tips;
        count++;
        userID = count;
    }

    public  int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static double getMonthlyWorth() {
        return monthlyWorth;
    }

    public void setMonthlyWorth(double monthlyWorth) {
        this.monthlyWorth = monthlyWorth;
    }

    public boolean isSalary() {
        return salary;
    }

    public void setSalary(boolean salary) {
        this.salary = salary;
    }

    public boolean isHourly() {
        return hourly;
    }

    public void setHourly(boolean hourly) {
        this.hourly = hourly;
    }

    public boolean isTips() {
        return tips;
    }

    public void setTips(boolean tips) {
        this.tips = tips;
    }


    public boolean isUser(){

        if(userID == 0){
            return false;
        }

        if(salary == false && hourly == false && tips == false){
            return false;
        }

        return true;
    }

}
