package cs402.project3;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 4/17/17.
 */

public class BillAddedEvent {

    public ArrayList<Bill> billsAddedList;

    public BillAddedEvent(ArrayList<Bill> billsAddedList) {
        this.billsAddedList = billsAddedList;
    }
}
