package com.example.festivalcarpet.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.festivalcarpet.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;


public class branchLocationOnMapFragment extends Fragment implements OnMapReadyCallback, OnConnectionFailedListener {


    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    String branchAddress;
    double radiusInMeters = 100.0;
    int strokeColor = 0xff005173;
    int shadeColor = 0x44005173;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;

    // TODO: Rename parameter arguments, choose names that match
    private static final String LATITUDE = "latitudeKey";
    private static final String LONGITUDE = "longitudeKey";
    private static final String BRANCHADDRESS = "branchAddressKey";

    // TODO: Rename and change types of parameters
    private double latitudeOfCompany;
    private double longitudeOfCompany;

    public branchLocationOnMapFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static branchLocationOnMapFragment newInstance(double latitudeOfCompany, double longitudeOfCompany,String branchAddress) {
        branchLocationOnMapFragment fragment = new branchLocationOnMapFragment();
        Bundle args = new Bundle();
        args.putDouble(LATITUDE, latitudeOfCompany);
        args.putDouble(LONGITUDE, longitudeOfCompany);
        args.putString(BRANCHADDRESS,branchAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitudeOfCompany = getArguments().getDouble(LATITUDE);
            longitudeOfCompany = getArguments().getDouble(LONGITUDE);
            branchAddress = getArguments().getString(BRANCHADDRESS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_location_on_map, container, false);

        getLocationPermission();


        return view;
    }

    public void Init() {
        if (mLocationPermissionsGranted) {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map_fragment_map);
            mapFragment.getMapAsync(this);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null)
                actionBar.setTitle(String.valueOf(branchAddress));
        } else {
            getLocationPermission();
        }


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        moveCamera(new LatLng(latitudeOfCompany, longitudeOfCompany), DEFAULT_ZOOM);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }


    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(2);
        mMap.addCircle(circleOptions);


    }


    public void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(getContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                Init();

            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
                Init();
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            Init();

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}