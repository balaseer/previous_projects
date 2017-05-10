package cs402.project3;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by itsmebadr on 5/5/17.
 */

public class DataBaseController {

    private static Realm realm;

    public static void getBillsFromDB() {

        realm = Realm.getDefaultInstance();

        RealmResults<Bill> result = realm.where(Bill.class).findAll();
        ArrayList<Bill> buildingsFromDB = new ArrayList<>();

        for (int i = 0; i < result.size(); i++){
            String tmpName = result.get(i).getName();
            double tmpAmount = result.get(i).getAmount();
            Date tmpDueDate = result.get(i).getDueDate();
            Date tmpReminder = result.get(i).getReminderDate();
            Bill tmp = new Bill(tmpName, tmpAmount, tmpDueDate, tmpReminder);
            buildingsFromDB.add(tmp);
        }

        BillsController.addBills(buildingsFromDB);
        return;

    }

    public static User getUserFromDatabase(){

        realm = Realm.getDefaultInstance();
        RealmResults<User> result = realm.where(User.class).equalTo("userID", User.getMonthlyWorth()).findAll();
        User user = new User(result.get(0).getMonthlyWorth(), result.get(0).isSalary(), result.get(0).isHourly(), result.get(0).isTips());
        return user;

    }


    }
