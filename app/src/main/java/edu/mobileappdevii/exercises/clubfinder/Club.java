package edu.mobileappdevii.exercises.clubfinder;

/**
 * Created by majdu on 12/3/2015.
 */
public class Club {
    @com.google.gson.annotations.SerializedName("id")
    public String Id;

    @com.google.gson.annotations.SerializedName("name")
    public String Name;

    @com.google.gson.annotations.SerializedName("description")
    public String Description;

    @com.google.gson.annotations.SerializedName("details")
    public String Details;

    public String getId() { return Id; }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getDetails() {
        return Details;
    }
}
