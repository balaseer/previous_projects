package cs402_spring17.project1;

import java.util.ArrayList;
/**
 * Created by itsmebadr on 2/27/17.
 */
public class BuildingRemovedEvent {
    public BuildingRemovedEvent(ArrayList<Building> removedBuildings) {
        this.removedBuildings = removedBuildings;
    }
    public ArrayList<Building>removedBuildings;
}
