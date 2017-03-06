package cs402_spring17.project1;

import android.graphics.Bitmap;

/**
 * Created by itsmebadr on 2/27/17.
 */

public class Building {

    String name;
    double latitude;
    double longitude;
    String description;
    Bitmap imageTaken;

    public Building(String name, double latitude, double longitude, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public Building(String name, double latitude, double longitude, String description, Bitmap imageTaken) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imageTaken = imageTaken;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Bitmap getImageTaken() {
        return imageTaken;
    }

    public void setImageTaken(Bitmap imageTaken) {
        this.imageTaken = imageTaken;
    }

}

