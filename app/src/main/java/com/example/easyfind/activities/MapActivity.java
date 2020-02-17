package com.example.easyfind.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyfind.R;
import com.example.easyfind.store.GetDirections;
import com.example.easyfind.store.GetNearByPlace;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private Button sBtn;
    private EditText edTxt;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private static final int REQUEST_CODE = 1;

    private Double latitude, longitude, dest_lat, dest_lng;
    public static boolean isDirectionRequested = true;

    public final int RADIUS = 1500;

    public TextView distance;
    public TextView duration;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initView();

        getUserLocation();
        initMap();

    }

    private void initView() {

        edTxt = findViewById(R.id.searchET);

        //
        sBtn = findViewById(R.id.searchBtn);
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchClicked();
            }
        });

        distance = findViewById(R.id.distance);
        duration = findViewById(R.id.duration);
    }

    private String getDirectionURL(double lat, double lng) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        url.append("origin="+lat+","+lng);
        url.append("&destination="+dest_lat+","+dest_lng);
        url.append("&key="+getString(R.string.api_key));
        return url.toString();
    }

    private String getURL(double lat, double lng, String nearBy) {
        StringBuilder placeURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        placeURL.append("location="+lat+","+lng);
        placeURL.append("&radius="+1500);
        placeURL.append("&type="+nearBy);
        placeURL.append("&key="+getString(R.string.api_key));
        return placeURL.toString();
    }

    private void initMap() {
        isDirectionRequested = true;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mMap);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();

        LatLng latLng = new LatLng(intent.getDoubleExtra("lat", 0.00), intent.getDoubleExtra("long", 0.00));

        dest_lat = latLng.latitude;
        dest_lng = latLng.longitude;
        //
        LatLng lo = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(lo)
                .title("Your Location")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.amu_bubble_mask))
                .snippet("Save Location");


        mMap.addMarker(markerOptions);


        //
        setMarker(latLng);


        // set directions
        Object[] data;
        data = new Object[5];
        String url = getDirectionUrl();
        data[0] = mMap;
        data[1] = url;
        data[2] = new LatLng(dest_lat, dest_lng);
        data[3] = distance;
        data[4] = duration;
        GetDirections getDirectionsData = new GetDirections();
        // execute asynchronously
        getDirectionsData.execute(data);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setOnMarkerDragListener(this);

        if(!checkPermission())
            requestPermission();
        else
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void setMarker(LatLng location) {
        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .title("Your Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                //.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_action_marker_foreground))
                .draggable(true);
        mMap.addMarker(markerOptions);
    }

    private String getDirectionUrl() {
        StringBuilder googleDirectionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionUrl.append("origin="+latitude+","+longitude);
        googleDirectionUrl.append("&destination="+dest_lat+","+dest_lng);
        googleDirectionUrl.append("&key="+getString(R.string.api_key_places));
        //  Log.d("nojo", "getDirectionUrl: "+googleDirectionUrl);
        return googleDirectionUrl.toString();
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&key=" + getString(R.string.api_key_places));
        // Log.d("", "getUrl: " + googlePlaceUrl);
        return googlePlaceUrl.toString();

    }

    private void getUserLocation() {
        // init location client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // init location request
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);
        setHomeMarker();
    }

    private boolean checkPermission() {
        int isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return isGranted == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE);
    }


    private void setHomeMarker() {
        Log.i("Noje", "DD");
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //super.onLocationResult(locationResult);
                for (Location location: locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraPosition cameraPosition = CameraPosition.builder()
                            .target(userLocation)
                            .zoom(15)
                            .bearing(0)
                            .tilt(45)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                    MarkerOptions markerOptions = new MarkerOptions().position(userLocation)
                            .title("Your Location")
                            .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.amu_bubble_mask))
                            .snippet("You are here");
                    mMap.addMarker(markerOptions);
                }
            }
        };
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setHomeMarker();
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        mMap.clear();

        dest_lat = marker.getPosition().latitude;
        dest_lng = marker.getPosition().longitude;

        //
        LatLng lo = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(lo)
                .title("Your Location")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.amu_bubble_mask))
                .snippet("You are here");
        mMap.addMarker(markerOptions);

        //
        setMarker(lo);

        Object[] data;
        data = new Object[3];
        String url = getDirectionUrl();
        data[0] = mMap;
        data[1] = url;
        data[2] = new LatLng(dest_lat, dest_lng);
        GetDirections getDirectionsData = new GetDirections();
        // execute asynchronously
        getDirectionsData.execute(data);
    }

    /** ACTION
     * */
    public void searchClicked() {
        //
        hideKeyboard(this);

        //
        mMap.clear();

        LatLng lo = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(lo)
                .title("Your Location")
                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.amu_bubble_mask))
                .snippet("You are here");
        mMap.addMarker(markerOptions);

        Object[] data;
        data = new Object[2];
        data[0] = mMap;
        data[1] = getURL(latitude, longitude, edTxt.getText().toString());
        GetNearByPlace getNearByPlace = new GetNearByPlace();
        getNearByPlace.execute(data);
        Toast.makeText(this, "Restaurant", Toast.LENGTH_SHORT).show();

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
