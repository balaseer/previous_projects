package cs402_spring17.project1;


import java.util.ArrayList;
/**
 * Created by itsmebadr on 2/27/17.
 */
public class BuildingAddedEvent {

    public ArrayList<Building> buildingsAddedList;

    public BuildingAddedEvent(ArrayList<Building> buildingsAddedList) {
        this.buildingsAddedList = buildingsAddedList;
    }
}
