package com.example.festivalcarpet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.festivalcarpet.dataModels.Carpet;
import com.example.festivalcarpet.R;
import com.example.festivalcarpet.adapters.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.security.InvalidParameterException;


public class BookingFragment extends Fragment {


    private static final String HOME_LIST_ITEM = "listItemObject";
    private TextView vehicleModel;
    private SliderView vehicleImg;
    private TextView vehicleColor;
    private TextView price;
    private TextView size;
    private Button bookNow;
    private Carpet carpet;
    private ActionBar actionBar;

    //private ImageView carImage;
    //private HomeListItem homeListItem;

    public BookingFragment() {
        // Required empty public constructor
    }


    public static BookingFragment newInstance(Carpet Carpet) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putParcelable(HOME_LIST_ITEM, Carpet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carpet = getArguments().getParcelable(HOME_LIST_ITEM);
        }
        else throw new InvalidParameterException("Incompatible argument");
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking, container, false);

        //1 ->  Brand Image
        vehicleModel=view.findViewById(R.id.bookingActivity_txtView_carpetModel);
        vehicleImg = view.findViewById(R.id.bookingActivity_imageSlider_imagesOfvehicle);
        vehicleColor=view.findViewById(R.id.bookingActivity_txtView_color);
        size =view.findViewById(R.id.bookingActivity_txtView_carpetSize);
        price=view.findViewById(R.id.bookingActivity_txtView_pricePerHour);
        bookNow = view.findViewById(R.id.bookingActivity_btn_bookNow);

        //carImage=findViewById(R.id.viewAllDetails_imgView_carImage);


        //====================================RECEIVE DATA=====================================
        //Bundle bundle=this.getArguments();
        //if(bundle!=null) {

            //======================================DEBUG=======================================
            //Log.d("click","data Received successfully");
            //Log.d("id",String.valueOf(id));
            //Log.d("ReceivedData",String.valueOf(bundle.getParcelable("listItemObject")));
            //======================================DEBUG=======================================

            //homeListItem=bundle.getParcelable("listItemObject");
            //int id=bundle.getInt("id");
        //}
        //else
            //throw new InvalidParameterException("Bundle is empty");
        //====================================RECEIVE DATA=====================================



        // TODO: Get data from 'homeListDataModel' or 'homeListDataModel.getVehicleSpecs' and pass them into their proper view
        //1 ->  Brand Image

        vehicleModel.setText(carpet.getCarpetCategory()+": "+carpet.getCarpetModel());
        vehicleColor.setText(carpet.getCarpetColor());
        size.setText(carpet.getCarpetSize());
        price.setText(String.valueOf("Price: "+carpet.getPrice())+" L.E");
        //carImage.setImageResource(homeListItem.getCarImg());

        //==================Image Slider Show=============================
        int[] images = {carpet.getCarpetImg()[0],
                carpet.getCarpetImg()[1],
                carpet.getCarpetImg()[2]};
        //==================Image Slider Show=============================


        //==================Image Slider Show=============================
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        vehicleImg.setSliderAdapter(sliderAdapter);
        vehicleImg.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        vehicleImg.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        vehicleImg.setAutoCycle(false);
        //==================Image Slider Show=============================

        setAvailableSizes();


        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Transfer Required Data from Booking fragment to Confirmation fragment
                Fragment fragment = ConfirmationFragment.newInstance(String.valueOf(carpet.getCarpetImg()[0]),
                        String.valueOf(carpet.getCarpetModel()), String.valueOf(carpet.getId()),
                        String.valueOf(carpet.getPrice()));

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navContent_frameLayout_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        if (actionBar != null)
            actionBar.setTitle("More Details");

        return view;
    }

    private void setAvailableSizes()
    {

 /*
        if( carpet.getVehicleSpecs().getAirBag() == null || !(carpet.getVehicleSpecs().getAirBag()))
            airBag.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getSeatBelts() == null || !(carpet.getVehicleSpecs().getSeatBelts()))
            seatBelts.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getABS() == null || !(carpet.getVehicleSpecs().getABS()))
            ABS.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getSunRoof() == null || !(carpet.getVehicleSpecs().getSunRoof()))
            sunRoof.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getParkingSensors() == null || !(carpet.getVehicleSpecs().getParkingSensors()))
            parkingSensors.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getRadio() == null || !(carpet.getVehicleSpecs().getRadio()))
            radio.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getBluetooth() == null || !(carpet.getVehicleSpecs().getBluetooth()))
            bluetooth.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getNavSystem() == null || !(carpet.getVehicleSpecs().getNavSystem()))
            navSystem.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getRemoteStart() == null || !(carpet.getVehicleSpecs().getRemoteStart()))
            remoteStart.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getAC() == null || !(carpet.getVehicleSpecs().getAC()))
            AC.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getMusicPlayer() == null || !(carpet.getVehicleSpecs().getMusicPlayer()))
            musicPlayer.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getExtraTyre() == null || !(carpet.getVehicleSpecs().getExtraTyre()))
            extraTyre.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getCharger() == null || !(carpet.getVehicleSpecs().getCharger()))
            charger.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getFireExtinguisher() == null || !(carpet.getVehicleSpecs().getFireExtinguisher()))
            fireExtinguisher.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getFirstAidKit() == null || !(carpet.getVehicleSpecs().getFirstAidKit()))
            firstAidKit.setVisibility(View.GONE);

        if( carpet.getVehicleSpecs().getCarSeat() == null || !(carpet.getVehicleSpecs().getCarSeat()))
            carSeat.setVisibility(View.GONE);

        if(carpet.getVehicleSpecs().getSmokingPreferences() == null || !(carpet.getVehicleSpecs().getSmokingPreferences()))
        {
            //No Smoking Action
            noSmoking.setVisibility(View.VISIBLE);
            Smoking.setVisibility(View.GONE);
        }
        else
        {
            //Smoking Action
            noSmoking.setVisibility(View.GONE);
            Smoking.setVisibility(View.VISIBLE);
        }

*/
    }



}