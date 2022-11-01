package com.example.festivalcarpet.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalcarpet.R;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ChooseDifferentLocationFragment extends Fragment {


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    //private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private ActivityResultLauncher<String[]> locationPermissionLauncher;
    private FusedLocationProviderClient mFusedLocationClient;
    private Boolean isLocationPermissionsGranted;
    private Boolean isLocationSettingsSatisfied;
    //private CurrentLocationRequest currentLocationRequest;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    //private LocationManager locationManager;

    private Context mContext;
    private Activity mActivity;
    private ActionBar mActionBar;


    private EditText customAddressFiled;
    private Button useCustomLocation;
    private Button useCurrentLocation;
    private TextView currentLocation;
    private String customAddressForPick;
    private String customAddressForDrop;
    private Bundle result;
    private String Details, subCity, Governorate, City, Country, fullAddress;
    private double Latitude, Longitude;


    public ChooseDifferentLocationFragment() {
        // Required empty public constructor
    }


    public static ChooseDifferentLocationFragment newInstance(String flag) {
        ChooseDifferentLocationFragment fragment = new ChooseDifferentLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLocationPermissionsGranted = false;
        isLocationSettingsSatisfied = false;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        locationPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Boolean fineLocationGranted = result.get(FINE_LOCATION);
                Boolean coarseLocationGranted = result.get(COURSE_LOCATION);

                if (fineLocationGranted != null && fineLocationGranted && coarseLocationGranted != null && coarseLocationGranted) {
                    isLocationPermissionsGranted = true;

                    Toast.makeText(mContext, "permission Granted", Toast.LENGTH_SHORT).show();
                } else
                    checkLocationPermission();
            }
        });


        mActionBar = ((AppCompatActivity) mActivity).getSupportActionBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_different_location, container, false);

        customAddressFiled = view.findViewById(R.id.chooseDiffLocation_EditText_addressManually);
        useCustomLocation = view.findViewById(R.id.chooseDiffLocation_btn_useCustomLocation);
        useCurrentLocation = view.findViewById(R.id.chooseDiffLocation_btn_useCurrentLocation);
        currentLocation = view.findViewById(R.id.chooseDiffLocation_TextView_location);

        useCustomLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customAddressForPick = customAddressFiled.getText().toString().trim();
                if (customAddressForPick.equals("")) {
                    Toast.makeText(getContext(), "Field is Empty", Toast.LENGTH_SHORT).show();
                } else {

                    result = new Bundle();
                    customAddressForPick = customAddressFiled.getText().toString();
                    result.putString("customAddressForPickKey", customAddressForPick);
                    getParentFragmentManager().setFragmentResult("requestKey", result);
                    HideSoftKeyboard();
                    getActivity().onBackPressed();

                }
            }
        });

        useCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getLocationPermission();
                //getLastLocation();
                locationPermissionLauncher.launch(new String[]{FINE_LOCATION, COURSE_LOCATION});
                checkLocationSettings();
                //getLastLocation();
                getCurrentLocation();
            }
        });

        if (mActionBar != null)
            mActionBar.setTitle("Choose Delivery Location");
        return view;
    }

    private void HideSoftKeyboard() {
        final InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

/*
    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionsGranted = true;
            getLastLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{FINE_LOCATION, COURSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
*/


/*
    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        if (mLocationPermissionsGranted) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                            fullAddress = getMoreDetailsFromLatLng(Latitude, Longitude);
                            currentLocation.setText(fullAddress);
                            ActiveContinueBtn();
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please Turn On Your Location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }
*/


    private void checkLocationSettings() {
        LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isLocationSettingsSatisfied = true;
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage("To continue, turn on device location, Which uses Google's location service")
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(getContext(), "Settings don't satisfy", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        }
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(mContext, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext,
                COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionsGranted = true;
            //getLastLocation();
        } else if (shouldShowRequestPermissionRationale(FINE_LOCATION) || shouldShowRequestPermissionRationale(COURSE_LOCATION)) {
            //User explanation, why we need this permission.
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission Denied")
                    .setMessage("This app needs this permission to pick up your location successfully")
                    .setPositiveButton("Give permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            locationPermissionLauncher.launch(new String[]{FINE_LOCATION, COURSE_LOCATION});
                            //locationPermissionLauncher.launch(COURSE_LOCATION);
                            //dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        } else {
            //Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        if (isLocationPermissionsGranted) {
            if (isLocationSettingsSatisfied) {
                mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                }).addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //Log.e("Location","long"+location.getLongitude());
                            //Log.e("Location","lat"+location.getLatitude());
                            //Geocoder geocoder = new Geocoder(getContext());
                            //List<Address> addresses;

                            //addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            //Log.e("AddressLocation",""+addresses.get(0).getLocality()+"\n"+addresses.get(0).getSubAdminArea()+"\n"+addresses.get(0).getAdminArea()+"\n"+addresses.get(0).getCountryName());
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                            fullAddress = getMoreDetailsFromLatLng(Latitude, Longitude);
                            currentLocation.setText(fullAddress);
                            ActiveContinueBtn();
                            //getMoreDetailsFromLatLng(location.getLatitude(), location.getLongitude());
                        }
                        else
                            Toast.makeText(mContext, "An error occurred, Please try again !", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //checkLocationSettings();
            }
        } else {
            //checkLocationPermission();
        }
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        if (isLocationPermissionsGranted) {
            if (isLocationSettingsSatisfied) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.e("TAG", "LocationOnSuccess");
                        if (location != null) {
                            Log.e("Location", "long" + location.getLongitude());
                            Log.e("Location", "lat" + location.getLatitude());

                            Geocoder geocoder = new Geocoder(getContext());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                Log.e("AddressLocation", "" + addresses.get(0).getLocality() + "\n" + addresses.get(0).getSubAdminArea() + "\n" + addresses.get(0).getAdminArea() + "\n" + addresses.get(0).getCountryName());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        //else
                        //startLocationRequestUpdate();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please Turn On Your Location...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            checkLocationPermission();
        }
    }










    private void setupLocationRequest() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(1000)
                .build();
    }

    private void setupLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data

                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    public void startLocationRequestUpdate() {
        if (isLocationPermissionsGranted) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    public void stopLocationRequestUpdate() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }


/*
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
 */

/*
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }*/

/*
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            getMoreDetailsFromLatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };
*/


    public String getMoreDetailsFromLatLng(double Latitude, double Longitude) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            //List<Address> addresses;
            //addresses = geocoder.getFromLocation(Latitude, Longitude, 1);

            subCity = geocoder.getFromLocation(Latitude, Longitude, 1).get(0).getLocality();
            City = geocoder.getFromLocation(Latitude, Longitude, 1).get(0).getSubAdminArea();
            Governorate = geocoder.getFromLocation(Latitude, Longitude, 1).get(0).getAdminArea();
            Country = geocoder.getFromLocation(Latitude, Longitude, 1).get(0).getCountryName();
            Details = subCity + ", " + City + ", " + Governorate + ", " + Country;
            return Details;
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }


    private void ActiveContinueBtn() {
        useCurrentLocation.setText("Continue");
        useCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                result = new Bundle();
                customAddressForPick = fullAddress;
                result.putString("customAddressForPickKey", customAddressForPick);
                getParentFragmentManager().setFragmentResult("requestKey", result);
                HideSoftKeyboard();
                getActivity().onBackPressed();
            }
        });
    }

}