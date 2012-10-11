package com.shivam.gps;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class main extends Activity
{
private static final String TAG = "Test GPS";
private LocationManager mLocationManager = null;
private static final int LOCATION_INTERVAL = 0;
private static final float LOCATION_DISTANCE = 0;

@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    Log.e(TAG, "onCreate");
    initializeLocationManager();
    
    /*
    try 
    {
        mLocationManager.requestLocationUpdates(
        LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
        mLocationListeners[1]);
    } 
    catch (java.lang.SecurityException ex) 
    {
        Log.i(TAG, "fail to request location update, ignore", ex);
    } catch (IllegalArgumentException ex) {
        Log.d(TAG, "network provider does not exist, " + ex.getMessage());
    }
    */
    
    try 
    {
        mLocationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
        mLocationListeners[0]);
    } 
    catch (java.lang.SecurityException ex) 
    {
        Log.i(TAG, "fail to request location update, ignore", ex);
    } 
    catch (IllegalArgumentException ex) 
    {
        Log.d(TAG, "gps provider does not exist " + ex.getMessage());
    }
}

@Override
public void onDestroy()
{
    Log.e(TAG, "onDestroy");
    super.onDestroy();
    if (mLocationManager != null) 
    {
        for (int i = 0; i < mLocationListeners.length; i++) 
        {
            try 
            {
                mLocationManager.removeUpdates(mLocationListeners[i]);
            } 
            catch (Exception ex) 
            {
                Log.i(TAG, "fail to remove location listners, ignore", ex);
            }
        }
    }
} 

private void initializeLocationManager() {
    Log.e(TAG, "initializeLocationManager");
    if (mLocationManager == null) 
    {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }
}


private class LocationListener implements android.location.LocationListener
{
    Location mLastLocation;
    public LocationListener(String provider)
    {
        Log.e(TAG, "LocationListener " + provider);
        mLastLocation = new Location(provider);
    }
    
    @Override
    public void onLocationChanged(Location location)
    {
    	    Log.e(TAG, "onLocationChanged: " + location);
	        String lat = String.valueOf(location.getLatitude());
			String lng = String.valueOf(location.getLongitude());
			String accu = String.valueOf(location.getAccuracy());
			Log.e(TAG, lat);
			Log.e(TAG, lng);
			Log.e(TAG,accu);
	        mLastLocation.set(location);
        	Toast.makeText(getApplicationContext(), lat + " " + lng, Toast.LENGTH_LONG).show();

        
    }
    
    @Override
    public void onProviderDisabled(String provider)
    {
        Log.e(TAG, "onProviderDisabled: " + provider);            
    }
    
    @Override
    public void onProviderEnabled(String provider)
    {
        Log.e(TAG, "onProviderEnabled: " + provider);
    }
    
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.e(TAG, "onStatusChanged: " + provider);
    }
} 
		
	LocationListener[] mLocationListeners = 
		new LocationListener[]{new LocationListener(LocationManager.GPS_PROVIDER),
			new LocationListener(LocationManager.NETWORK_PROVIDER)};


}
    