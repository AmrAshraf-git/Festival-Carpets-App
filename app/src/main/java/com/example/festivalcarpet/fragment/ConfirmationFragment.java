package com.example.festivalcarpet.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festivalcarpet.DateConverter;
import com.example.festivalcarpet.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;


public class ConfirmationFragment extends Fragment implements DatePickerDialog.OnDateSetListener{


    View view;
    ImageView carpetImageImgView;
    TextView carpetModelTxtView;
    TextView carpetPriceTxtView;
    int dayOfWeekAsNumber;
    String dayOfWeek;
    String monthOfYear;
    String deliveryDate;
    TextView deliveryDayOfMonthTxtView;
    TextView deliveryDayOfWeekTxtView;
    TextView deliveryMonthTxtView;
    TextView deliveryYearTxtView;

    CardView deliveryDateCard;
    Calendar cal;

    RadioButton abbasBranch,hassaneinBranch, differentLocation;
    CardView PickUpLocation;
    TextView pickUpLocation;

    TextView branchNameTxtView, branchAddressTxtView, branchPhoneNumberTxtView;
    ImageView mapCover;

    LinearLayout branchInfoSec;
    String CustomAddressPick;
    String branchAddress;
    ActionBar actionBar;

    private String branchLatitude;
    private String branchLongitude;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VEHICLE_IMAGE = "imageKey";
    private static final String VEHICLE_MODEL = "modelKey";
    private static final String VEHICLE_ID = "idKey";
    private static final String VEHICLE_PRICE = "priceKey";

    private String vehicleImage;
    private String vehicleModel;
    private String vehicleId;
    private String vehiclePrice;

    public ConfirmationFragment() {
        // Required empty public constructor
    }


    public static ConfirmationFragment newInstance(String vehicleImage, String vehicleModel, @NonNull String vehicleId,
                                                   String vehiclePrice) {
        ConfirmationFragment fragment = new ConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(VEHICLE_IMAGE, vehicleImage);
        args.putString(VEHICLE_MODEL, vehicleModel);
        args.putString(VEHICLE_ID, vehicleId);
        args.putString(VEHICLE_PRICE, vehiclePrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicleImage = getArguments().getString(VEHICLE_IMAGE);
            vehicleModel = getArguments().getString(VEHICLE_MODEL);
            vehicleId = getArguments().getString(VEHICLE_ID);
            vehiclePrice = getArguments().getString(VEHICLE_PRICE);
        }
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                CustomAddressPick = bundle.getString("customAddressForPickKey");
                if(CustomAddressPick != null && !CustomAddressPick.equals("".trim())){
                    PickUpLocation.setVisibility(View.VISIBLE);
                    pickUpLocation.setText(CustomAddressPick);
                }
            }
        });

        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        carpetImageImgView = view.findViewById(R.id.confirmation_imgView_imageOfItem);
        carpetModelTxtView = view.findViewById(R.id.confirmation_txtView_ItemModel);
        carpetPriceTxtView = view.findViewById(R.id.confirmation_txtView_ItemPrice);

        deliveryDayOfMonthTxtView = view.findViewById(R.id.confirmation_txtView_DayOfMonth);
        deliveryDayOfWeekTxtView = view.findViewById(R.id.confirmation_txtView_DayOfWeek);
        deliveryMonthTxtView = view.findViewById(R.id.confirmation_txtView_Month);
        deliveryYearTxtView = view.findViewById(R.id.confirmation_txtView_Year);
        deliveryDateCard = view.findViewById(R.id.confirmation_cardView_ChooseDeliveryDay);

        abbasBranch = view.findViewById(R.id.confirmation_radioBtn_Abbas);
        hassaneinBranch = view.findViewById(R.id.confirmation_radioBtn_Hassanein);
        differentLocation = view.findViewById(R.id.confirmation_radioBtn_Other);
        PickUpLocation = view.findViewById(R.id.confirmation_cardView_PickUpLocation);
        pickUpLocation = view.findViewById(R.id.confirmation_txtView_pickUpLocation);

        branchNameTxtView = view.findViewById(R.id.confirmation_txtView_branchName);
        branchAddressTxtView = view.findViewById(R.id.confirmation_txtView_branchAddress);
        branchPhoneNumberTxtView = view.findViewById(R.id.confirmation_txtView_branchPhoneNumber);
        mapCover = view.findViewById(R.id.confirmation_imgView_mapCover);
        branchInfoSec = view.findViewById(R.id.confirmation_LinLayout_branchInfoSec);


        Picasso.get().load(vehicleImage).fit().centerInside().error(R.drawable.ic_baseline_image).into(carpetImageImgView);
        carpetModelTxtView.setText(vehicleModel);
        carpetPriceTxtView.setText(vehiclePrice+" L.E");

        branchInfoSec.setVisibility(View.GONE);
        PickUpLocation.setVisibility(View.GONE);

        deliveryDateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        //Set Current Date for (Pick-up Date and Drop-off Date) as Initialize Date
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 2);

        /////////////dp.setMinDate(cal.getTimeInMillis()); // where  DatePicker dp

        DateConverter dateConverter = new DateConverter(cal.get(Calendar.DAY_OF_WEEK),
                cal.get(Calendar.MONTH));
        dayOfWeek = dateConverter.getDayOfWeek();
        monthOfYear = dateConverter.getMonthOfYear();

        deliveryDate = cal.get(Calendar.YEAR) +
                "-" + (cal.get(Calendar.MONTH) + 1) +
                "-" + cal.get(Calendar.DAY_OF_MONTH);

        deliveryDayOfMonthTxtView.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        deliveryDayOfWeekTxtView.setText(dayOfWeek);
        deliveryMonthTxtView.setText(monthOfYear);
        deliveryYearTxtView.setText(String.valueOf(cal.get(Calendar.YEAR)));

        abbasBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickUpLocation.setVisibility(View.GONE);
                branchInfoSec.setVisibility(View.VISIBLE);

                branchNameTxtView.setText("Name: Abbas Al-Akkad");
                branchAddressTxtView.setText("Address: 102 Abbas Al Akkad St, Nasr City, Cairo.");
                branchPhoneNumberTxtView.setText("Phone Number: 01272077373");
                branchLatitude="30.0551127";
                branchLongitude="31.3391156";
                branchAddress = "102 Abbas Al Akkad St, Nasr City, Cairo.";
            }
        });

        hassaneinBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickUpLocation.setVisibility(View.GONE);
                branchInfoSec.setVisibility(View.VISIBLE);

                branchNameTxtView.setText("Name: Hassanein Heilkal");
                branchAddressTxtView.setText("Address: 65 Mohamed Hassanein Heikal St, Nasr City, Cairo.");
                branchPhoneNumberTxtView.setText("Phone Number: 0121222238");
                branchLatitude="30.0593143";
                branchLongitude="31.3385988";
                branchAddress = "65 Mohamed Hassanein Heikal St, Nasr City, Cairo.";
            }
        });

        differentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickUpLocation.setVisibility(View.VISIBLE);
                branchInfoSec.setVisibility(View.GONE);



            }
        });

        PickUpLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ChooseDifferentLocationFragment.newInstance("Pick");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navContent_frameLayout_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        mapCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isServicesOK()) {
                    Fragment fragment = branchLocationOnMapFragment.newInstance(Double.parseDouble(branchLatitude),Double.parseDouble(branchLongitude),branchAddress);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.navContent_frameLayout_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        if (actionBar != null)
            actionBar.setTitle("Booking Details");

        return view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYearAsNumber, int dayOfMonth) {

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYearAsNumber, dayOfMonth);
        dayOfWeekAsNumber = gregorianCalendar.get(Calendar.DAY_OF_WEEK);

        DateConverter dateConverter = new DateConverter(dayOfWeekAsNumber, monthOfYearAsNumber);
        dayOfWeek = dateConverter.getDayOfWeek();
        monthOfYear = dateConverter.getMonthOfYear();

        monthOfYearAsNumber++;
        deliveryDate = year + "-" + monthOfYearAsNumber + "-" + dayOfMonth;
        deliveryDayOfMonthTxtView.setText(String.valueOf(dayOfMonth));
        deliveryDayOfWeekTxtView.setText(dayOfWeek);
        deliveryMonthTxtView.setText(monthOfYear);
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getContext(), "You Cannot Make Map Request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}