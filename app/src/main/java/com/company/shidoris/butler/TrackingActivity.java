package com.company.shidoris.butler;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.company.shidoris.butler.apis.APIMaps;
import com.company.shidoris.butler.apis.MapsInterface;
import com.company.shidoris.butler.model.LocationResponse;
import com.company.shidoris.butler.model.MapsDirection.DirectionRes;
import com.company.shidoris.butler.model.MapsDirection.Routes;
import com.company.shidoris.butler.model.RoadsResponse;
import com.company.shidoris.butler.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class TrackingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsInterface mapsInterface;

    private LatLng to;
    private LatLng from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        mapsInterface = APIMaps.getDirectionApi().create(MapsInterface.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        to = getIntent().getParcelableExtra("to");
        from = getIntent().getParcelableExtra("from");


    }

    private void getPoints(LatLng to,LatLng from) {
        Log.d("update",to.latitude+","+to.longitude+"|"+from.latitude+","+from.longitude);
        Call<DirectionRes> mapsCall = mapsInterface.getDirection(to.latitude+","+to.longitude,from.latitude+","+from.longitude, "AIzaSyC5VRkgiAoBNPijqz73FH4Glp12UvhqPX8");
        mapsCall.enqueue(new Callback<DirectionRes>() {
            @Override
            public void onResponse(Call<DirectionRes> call, Response<DirectionRes> response) {
                final Routes[] res = response.body().getRoutes();

                updateUI(res[0].getOverviewPolyline().getPoints());

            }

            @Override
            public void onFailure(Call<DirectionRes> call, Throwable t) {

            }
        });
    }

    private void updateUI(String points) {

        List<LatLng> decodedPath = PolyUtil.decode(points);
        mMap.addPolyline(new PolylineOptions().width(7).jointType(ROUND).color(Color.RED).addAll(decodedPath));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(to)      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().draggable(false).position(to).title("To here"));
        mMap.addMarker(new MarkerOptions().draggable(false).position(from).title("From here"));
        getPoints(to,from);
    }
}
