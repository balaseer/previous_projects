package cs402_spring17.project1;


import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by itsmebadr on 2/27/17.
 */

public class WebServicesController {

    private static RequestQueue queue = null;
    private static final String NAME = "name";

    public static void loadBuildings(Context context){
        if( queue == null){
            queue = Volley.newRequestQueue(context);
        }
        String url ="https://s3-us-west-2.amazonaws.com/electronic-armory/buildings.json";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("MZ", response);
                        ArrayList<Building> buildings = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MZ", "That didn't work!");
            }
        });
        queue.add(stringRequest);
        queue.start();
    }
}
