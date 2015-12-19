package kr.ac.kookmin.eomjenogho.finalproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class myLocation extends Service implements LocationListener {
    private final Context mcontext;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    Location location;
    double lat;
    double lng;

    protected LocationManager locationManager;

    public myLocation(Context context){
        this.mcontext = context;
        getLocation();
    }

    private Location getLocation() {
        locationManager = (LocationManager) mcontext.getSystemService(LOCATION_SERVICE);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isNetworkEnabled){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1,1,this);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
        else if (isGPSEnabled){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1,this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        return location;
    }

    public double getLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongitude() {
        if (location != null) {
            lng = location.getLongitude();
        }
        return lng;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onLocationChanged(Location location) {
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }
}
