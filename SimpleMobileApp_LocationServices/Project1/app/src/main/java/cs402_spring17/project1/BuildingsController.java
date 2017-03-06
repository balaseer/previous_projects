package cs402_spring17.project1;



import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;

/**
 * Created by itsmebadr on 2/27/17.
 */

public class BuildingsController {

    private static ArrayList<Building> buildingsArray = new ArrayList<>();

    public static void addBuilding(Building newBuilding) {
        buildingsArray.add(newBuilding);
        ArrayList<Building>buildingArrayList = new ArrayList<>();
        buildingArrayList.add(newBuilding);
        EventBus.getDefault().post(new BuildingAddedEvent(buildingArrayList));
    }
    public static void addBuildings(ArrayList<Building> buildingArrayList) {
        buildingsArray.addAll(buildingsArray.size(), buildingArrayList);
        EventBus.getDefault().post(new BuildingAddedEvent(buildingArrayList));
    }
    public static void removeBuilding(Building buildingToRemove){
        buildingsArray.remove(buildingToRemove);
        removeBuildingHelper(buildingToRemove);
    }
    private static void removeBuildingHelper(Building buildingToRemove) {
        ArrayList<Building> buildingsArrayList = new ArrayList<>();
        buildingsArrayList.add(buildingToRemove);
        EventBus.getDefault().post(new BuildingRemovedEvent(buildingsArrayList));
    }
    public static void removeBuilding(int indexToRemove){
        Building removedBuilding = buildingsArray.remove(indexToRemove);
        removeBuildingHelper(removedBuilding);
    }
    public static ArrayList<Building> getBuildings() {
        return buildingsArray;
    }
}