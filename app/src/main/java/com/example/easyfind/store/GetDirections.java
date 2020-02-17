package com.example.easyfind.store;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.easyfind.activities.MapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;

import java.io.IOException;

public class GetDirections extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String directionData, url;
    LatLng latLng;
    TextView distance;
    TextView duration;


    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        latLng = (LatLng) objects[2];
        distance = (TextView) objects[3];
        duration = (TextView) objects[4];
        try {
            String data = GoogleStore.readURL(url);
            if (data != null) {
                directionData = data;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return directionData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            Distance distanceModel = DataParser.parseDistance(s);
            setupMap(distanceModel, s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupMap(Distance distanceModel, String s) throws JSONException {
        //mMap.clear();

        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Duration: "+ distanceModel.getDuration())
                .snippet("Distance: "+ distanceModel.getDistance())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true);
        mMap.addMarker(markerOptions);

        if (MapActivity.isDirectionRequested) {
            String[] directionList;
            directionList = DataParser.parseDirections(s);
            displayDirections(directionList);
            distance.setText("Distance: "+distanceModel.getDistance());
            duration.setText("Duration: "+distanceModel.getDuration());
        }
    }

    private void displayDirections(String[] directionList) {
        for (int i=0; i<directionList.length; i++){
            PolylineOptions options = new PolylineOptions()
                    .color(Color.RED)
                    .width(10)
                    .addAll(PolyUtil.decode(directionList[i]));
            mMap.addPolyline(options);
        }
    }
}
