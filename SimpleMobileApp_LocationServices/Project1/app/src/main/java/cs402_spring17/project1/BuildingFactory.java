package cs402_spring17.project1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by itsmebadr on 2/27/17.
 */

public class BuildingFactory {

    private static final String NAME = "name";

    public static ArrayList createLocationsFromJSON(String locations){

        ArrayList<Building> buildings = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(locations);
            for(int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                String name = jsonObject.getString(NAME);
                JSONObject locationJSONObject = jsonObject.getJSONObject("location");
                double latitude = locationJSONObject.getDouble("latitude");
                double longitude = locationJSONObject.getDouble("longitude");
                String description = jsonObject.getString("description");
                Building building = new Building(name, latitude, longitude, description);
                buildings.add(building);
            }
            BuildingsController.addBuildings(buildings);
            Log.d("MZ", "HERE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return buildings;
    }

    public LocationModel createLocationFromJSON(String locations){

        return null;
    }
}
