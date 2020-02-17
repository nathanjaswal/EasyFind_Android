package com.example.easyfind.store;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataParser {

    public static List<Place> getPlaces(String jsonData) throws JSONException {

        JSONArray jsonArray = null;
        JSONObject json = new JSONObject(jsonData);

        jsonArray = json.getJSONArray("results");

        List<Place> placeArr = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++) {

            String placeName = "N/A";
            String vicinity = "N/A";
            String latitude = "";
            String longtude = "";
            String refrence = "";
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (!jsonObject.isNull("name")) {
                    placeName = jsonObject.getString("name");
                }
                if (!jsonObject.isNull("vicinity")) {
                    vicinity = jsonObject.getString("vicinity");
                }
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                latitude = location.getString("lat");
                longtude = location.getString("lng");
                refrence = jsonObject.getString("reference");



                Place newplace = new Place(placeName, vicinity, latitude, longtude, refrence);


                Log.d("arr", "places" + newplace);
                placeArr.add(newplace);

            } catch (JSONException e) {
                e.getStackTrace();
            }

        }
        return  placeArr;
    }

    public static Distance parseDistance(String jsonData) throws JSONException {
        JSONArray jsonArray = null;
        JSONObject json = new JSONObject(jsonData);

        jsonArray = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
        Distance distanceModel = new Distance();
        String duration, distance = "";

        try {
            duration = jsonArray.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = jsonArray.getJSONObject(0).getJSONObject("distance").getString("text");
            distanceModel.setDistance(distance);
            distanceModel.setDuration(duration);
        } catch (JSONException e) {
            e.getStackTrace();
        }
        return distanceModel;
    }

    public static String[] parseDirections(String jsonData) throws JSONException {
        JSONArray jsonArray = null;
        JSONObject json = new JSONObject(jsonData);

        jsonArray = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

        String[] polyLines = new String[jsonArray.length()];

        for (int i=0; i<jsonArray.length(); i++) {
            try {
                polyLines[i] = jsonArray.getJSONObject(i).getJSONObject("polyline").getString("points");
            } catch (JSONException e) {
                e.getStackTrace();
            }
        }
        return polyLines;
    }

}

