package cs402.project3;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 4/17/17.
 */

public class BillsController {

    private static final int icon = R.drawable.ic_attach_money_black_36dp;

    private static ArrayList<Bill> billsArray = new ArrayList<>();

    public static void addBill(Bill newBill) {
        billsArray.add(newBill);
        ArrayList<Bill> billArrayList = new ArrayList<>();
        billArrayList.add(newBill);
        EventBus.getDefault().post(new BillAddedEvent(billArrayList));
    }

    public static void addBills(ArrayList<Bill> billArrayList) {
        billsArray.addAll(billsArray.size(), billArrayList);
        EventBus.getDefault().post(new BillAddedEvent(billArrayList));
    }

    public static void removeBill(Bill billToRemove) {
        billsArray.remove(billToRemove);
        removeBillHelper(billToRemove);
    }

    private static void removeBillHelper(Bill billToRemove) {
        ArrayList<Bill> billsArrayList = new ArrayList<>();
        billsArrayList.add(billToRemove);
        EventBus.getDefault().post(new BillRemovedEvent(billsArrayList));
    }

    public static void removeBill(int indexToRemove) {
        Bill removedBill = billsArray.remove(indexToRemove);
        removeBillHelper(removedBill);
    }

    public static Bill getBill(int index){
        Bill billToReturn = billsArray.get(index);
        return billToReturn;
    }

    public static ArrayList<Bill> getBills() {

        for(int i = 0; i < billsArray.size(); i++){
            billsArray.get(i).setImageResId(icon);
        }
        return billsArray;
    }

    public static double sumOfBills(){

        double sum = 0;
        for(int i = 0; i < billsArray.size(); i++){
            sum += billsArray.get(i).getAmount();
        }

        return sum;
    }
}

