package com.android.exemplo.projectone.empresa;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.exemplo.projectone.R;
import com.android.exemplo.projectone.helper.Dados;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    List<Address> geocodeMatches = null;
    String address = null;
    String address_num;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();

        address_num = intent.getExtras().getString("key");

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        List<Address> geocodeMatches = null;
        double latitude=0, longitude=0;

        if (address_num.equals("")) {
            address = "Portugal, Faro";
        }
        else {
            switch (Integer.parseInt(address_num)) {
                case 0:
                    address = "Rua das Laranjeiras, N 33,  Faro";
                    break;
                case 3:
                    address = "Rua Doutor Jose de Matos, Faro";
                    break;
                case 6:
                    address ="Zona Ind Lt 41, 8100-000 LOULÉ";
//                    latitude    = 37.137957;
//                    longitude   = -8.020237;
                    break;
                default:
                    address="Portugal, Faro";

            }

//            try {


//            if (address!="") {

//                    geocodeMatches =
//                            new Geocoder(this).getFromLocationName(address, 1);
            new GetAddressPositionTask().execute(address);
//                    if (!geocodeMatches.isEmpty()) {
//                        latitude = geocodeMatches.get(0).getLatitude();
//                        longitude = geocodeMatches.get(0).getLongitude();
//                    }
//            }
//            }
//            catch (IOException e) {
//                // TODO Auto-generated catch block
//                Log.i("", "TGB ERROR: " + e.toString());
//                e.printStackTrace();
//            }
        }


//        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).alpha(0.7f).title(Dados.Empresas[Integer.parseInt(address_num)]));
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(latitude, longitude)), 15));
//        // Zoom in, animating the camera.
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
//        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }


    private class GetAddressPositionTask extends
            AsyncTask<String, Integer, LatLng> {


        String TAG = "GetAddressPositionTask";
        LatLng position;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LatLng doInBackground(String... plookupString) {

            String lookupString = plookupString[0];
            final String lookupStringUriencoded = Uri.encode(lookupString);
            position = null;

            Log.i(TAG, "lookupStringUriencoded: " + lookupStringUriencoded);
            if ( lookupStringUriencoded.equals("")){

                return (new LatLng(10, 10));
            }

            // best effort zoom
            try {
//                if (geocoder != null) {
                List<Address> addresses = new Geocoder(getApplicationContext()).getFromLocationName(
                        lookupString, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address first_address = addresses.get(0);
                    position = new LatLng(first_address.getLatitude(),
                            first_address.getLongitude());
                }
//                } else {
//                    Log.e(TAG, "geocoder was null, is the module loaded? "
//                            + "isLoaded");
//                }

            } catch (IOException e) {
                Log.e(TAG, "geocoder failed, moving on to HTTP");
            }
            // try HTTP lookup to the maps API
            if (position == null) {
                HttpGet httpGet = new HttpGet(
                        "http://maps.google.com/maps/api/geocode/json?address="
                                + lookupStringUriencoded + "&sensor=true");
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    response = client.execute(httpGet);
                    HttpEntity entity = response.getEntity();
                    InputStream stream = entity.getContent();
                    int b;
                    while ((b = stream.read()) != -1) {
                        stringBuilder.append((char) b);
                    }
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    // Log.d("MAPSAPI", stringBuilder.toString());

                    jsonObject = new JSONObject(stringBuilder.toString());
                    if (jsonObject.getString("status").equals("OK")) {
                        jsonObject = jsonObject.getJSONArray("results")
                                .getJSONObject(0);
                        jsonObject = jsonObject.getJSONObject("geometry");
                        jsonObject = jsonObject.getJSONObject("location");
                        String lat = jsonObject.getString("lat");
                        String lng = jsonObject.getString("lng");

                        Log.d("MAPSAPI", "latlng " + lat + ", "
                                + lng);

                        position = new LatLng(Double.valueOf(lat),
                                Double.valueOf(lng));
                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                }

            }
            Log.i(TAG, "tgb position: " + position);
            return position;
        }

        @Override
        protected void onPostExecute(LatLng result) {

            if (!result.toString().equals("lat/lng: (37.0193548,-7.9304397)")) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            mMap.addMarker(new MarkerOptions().position(result).alpha(0.7f).title(Dados.Empresas[Integer.parseInt(address_num)]));


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((result), 15));
                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
            else{
                Toast.makeText(getApplication(), "Sem localização definida.", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(result);
        }

    };

}


