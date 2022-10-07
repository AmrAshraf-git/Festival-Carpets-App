package com.example.carrental.fragment.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.carrental.dataModels.Carpet;
import com.example.carrental.PriceLabel;
import com.example.carrental.R;
import com.example.carrental.dataModels.CarpetSpecs;
import com.example.carrental.adapters.HomeListAdapter;
import com.example.carrental.fragment.BookingFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeListAdapter.OnRecyclerViewClickListener {

    private static final String CATEGORY_NAME = "categoryName";
    private String mCategoryName;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    //private RecyclerView.LayoutManager layoutManager;
    private HomeListAdapter homeListAdapter;
    private ArrayList<Carpet> homeListItemArrayList;
    private ProgressBar progressBar;
    private TextView searchResult;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private boolean mAlreadyLoaded;
    private String previousQuery;
    MenuItem mMenuItem;
    SearchView searchView;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String categoryName) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState == null)
            mAlreadyLoaded = false;
        if (getArguments() != null)
            mCategoryName = getArguments().getString(CATEGORY_NAME);

        homeListItemArrayList = new ArrayList<>();
        //homeListAdapter = new HomeListAdapter(homeListItemArrayList, (id, homeListItem) -> {
        //======================================DEBUG=======================================
//            //Log.e("click","HomePage onItemClick");
//            //Log.e("ReceivedData",homeListItem.getCarModel());
//            //======================================DEBUG=======================================
//
//            //====================================SEND DATA=====================================
//            //Intent intent=new Intent(getContext(), BookingActivity.class);
//            //intent.putExtra("listItemObject",homeListItem);
//            //intent.putExtra("id",id);
//            //startActivity(intent);
//
//            //Bundle bundle=new Bundle();
//            //bundle.putParcelable("listItemObject",homeListItem);
//            //fragment.setArguments(bundle);
//
//            Fragment fragment = BookingFragment.newInstance(homeListItemArrayList.get(position));
//            getParentFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.translate_enter, R.anim.translate_exit)
//                    .addToBackStack(null)
//                    .replace(R.id.navContent_frameLayout_container, fragment).commit();
        //====================================SEND DATA=====================================
//});


        //======================================DUMMY DATA======================================
        //1st object
        Carpet carpet = new Carpet();
        carpet.setCarpetModel("Carpet1");
        //String[] book = {"150 Km/period", "Min 1 Days", "No Extra Fees", "Insurance Coverage"};
        //homeListItem.setBookDetails(book);
        carpet.setCompanyName("Festival");
        carpet.setPrice(600);
        carpet.setPriceLabel(PriceLabel.EGYPTIAN_POUND);
        carpet.setCarpetColor("Silver");
        carpet.setCarpetRate(Float.parseFloat("3.5"));
        //CarpetSpecs carpetSpecs =new CarpetSpecs();
        //carpet.setVehicleSpecs(carpetSpecs);
        int[] vehicleImg = new int[3];
        vehicleImg[0] = R.drawable.img_logo_test;
        carpet.setCarpetImg(vehicleImg);
        carpet.setCompanyAddress("Cairo,Egypt");

        //2nd object
        Carpet carpet2 = new Carpet();
        carpet2.setCarpetModel("Carpet2");
        carpet2.setCompanyName("Festival");
        carpet2.setPrice(1200);
        carpet2.setPriceLabel(PriceLabel.EGYPTIAN_POUND);
        carpet2.setCarpetColor("Black");
        //CarpetSpecs carpetSpecs2 =new CarpetSpecs();
        //carpet2.setVehicleSpecs(carpetSpecs2);
        int[] vehicleImg2 = new int[3];
        vehicleImg2[0] = R.drawable.ic_car_default_black;
        carpet2.setCarpetImg(vehicleImg2);
        carpet2.setCompanyAddress("Alex,Egypt");

        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet2);
        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet2);
        //======================================DUMMY DATA======================================


    }
/*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tool_bar_menu,menu);
        //MenuItem menuItem = menu.findItem(R.id.search_bar);
        //SearchView searchView=(SearchView) menuItem.getActionView();
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!mAlreadyLoaded) {
            mAlreadyLoaded = true;
            view = inflater.inflate(R.layout.fragment_home, container, false);

            recyclerView = view.findViewById(R.id.homePageF_recyclerView_main);
            //searchResult = view.findViewById(R.id.homePageF_txtView_searchResult);
            swipeRefreshLayout = view.findViewById(R.id.homePageF_swipeRf);
            progressBar = view.findViewById(R.id.homePageF_prgrsBar);

            //searchResult.setVisibility(View.GONE);
            //progressBar.setVisibility(View.VISIBLE);

            linearLayoutManager = new LinearLayoutManager(getContext());

            setUpRecyclerView();

        }
        return view;
    }


//    private void getByCategory() {
//        switch (mCategoryName) {
//            case "car":
//                observeCarpets();
//                break;
//            case "bus":
//                observeCarpets();
//                break;
//            case "motorcycle":
//                observeCarpets();
//                break;
//            default:
//                observeCarpets();
//                break;
//        }
//        previousQuery = null;
//    }


    private void setUpRecyclerView() {
        //if (homeListAdapter == null) {
        homeListAdapter = new HomeListAdapter(homeListItemArrayList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //performance
        //homeListAdapter.setHasStableIds(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(homeListAdapter);
        //} //else {
        //recyclerView.setAdapter(homeListAdapter);
        //homeListAdapter.updateStatus(homeItemList);
        //homeListAdapter.notifyDataSetChanged();
        //Log.e("test", "data");
        //}

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tool_bar_menu, menu);
        //MenuItem menuItem = menu.findItem(R.id.search_bar);
        //SearchView searchView=(SearchView) menuItem.getActionView();
    }

    @Override
    public void onItemClick(int position) {

        Fragment fragment = BookingFragment.newInstance(homeListItemArrayList.get(position));
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.translate_enter, R.anim.translate_exit)
                .addToBackStack(null)
                .replace(R.id.navContent_frameLayout_container, fragment).commit();
    }
}