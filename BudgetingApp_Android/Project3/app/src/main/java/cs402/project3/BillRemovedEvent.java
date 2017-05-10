package cs402.project3;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 4/17/17.
 */

public class BillRemovedEvent {

    public BillRemovedEvent(ArrayList<Bill> removedBills) {
        this.removedBills = removedBills;
    }
    public ArrayList<Bill>removedBills;
}
