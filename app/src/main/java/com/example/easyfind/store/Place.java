package com.example.easyfind.store;

public class Place {
    public Place(String name, String vicinity, String latitude, String longitude, String reference) {
        this.name = name;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reference = reference;
    }

    String name, vicinity, latitude, longitude, reference;
}
