package cs402_spring17.project1;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by itsmebadr on 2/27/17.
 */

public class LocationModel {

    String locationName;
    String locationDescription;
    LatLng locationCoordinates;
    Bitmap imageTaken;

    public LocationModel(String locationName, String locationDescription, LatLng locationCoordinates){
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        this.locationCoordinates = locationCoordinates;
    }

    public LocationModel(String locationName, String locationDescription, LatLng locationCoordinates, Bitmap imageTaken){
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        this.locationCoordinates = locationCoordinates;
        this.imageTaken = imageTaken;
    }

    public Bitmap getImageTaken() {
        return imageTaken;
    }

    public void setImageTaken(Bitmap imageTaken) {
        this.imageTaken = imageTaken;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public LatLng getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(LatLng locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }
}
