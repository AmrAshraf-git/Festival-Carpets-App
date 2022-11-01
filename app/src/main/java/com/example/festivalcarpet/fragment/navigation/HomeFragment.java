package com.example.festivalcarpet.fragment.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.festivalcarpet.dataModels.Carpet;
import com.example.festivalcarpet.R;
import com.example.festivalcarpet.adapters.HomeListAdapter;
import com.example.festivalcarpet.fragment.BookingFragment;

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
        carpet.setCarpetCategory("Silk Carpet");
        carpet.setCarpetModel("Jaddor");
        carpet.setCarpetSize("1.6 * 2.3");
        carpet.setCarpetColor("Gray / Blue");
        carpet.setPrice(10300);
        int[] vehicleImg = new int[3];
        vehicleImg[0] = R.drawable.ic_baseline_image;
        carpet.setCarpetImg(vehicleImg);

        //2nd object
        Carpet carpet2 = new Carpet();
        carpet2.setCarpetCategory("Acrylic Carpet");
        carpet2.setCarpetModel("Tuana");
        carpet2.setCarpetSize("2 * 2.8");
        carpet2.setCarpetColor("Beige / Gold");
        carpet2.setPrice(4200);
        int[] vehicleImg2 = new int[3];
        vehicleImg2[0] = R.drawable.ic_baseline_image;
        carpet2.setCarpetImg(vehicleImg);


        //3nd object
        Carpet carpet3 = new Carpet();
        carpet3.setCarpetCategory("Shag Carpet");
        carpet3.setCarpetModel("Calypso");
        carpet3.setCarpetSize("1.33 * 1.9");
        carpet3.setCarpetColor("Beige / Black");
        carpet3.setPrice(4200);
        int[] vehicleImg3 = new int[3];
        vehicleImg3[0] = R.drawable.ic_baseline_image;
        carpet2.setCarpetImg(vehicleImg);


        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet3);
        homeListItemArrayList.add(carpet2);
        homeListItemArrayList.add(carpet3);
        homeListItemArrayList.add(carpet3);
        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet2);
        homeListItemArrayList.add(carpet2);
        homeListItemArrayList.add(carpet2);
        homeListItemArrayList.add(carpet);
        homeListItemArrayList.add(carpet2);
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

        Log.e("tesg",String.valueOf(position));
        Fragment fragment = BookingFragment.newInstance(homeListItemArrayList.get(position));
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.translate_enter, R.anim.translate_exit)
                .addToBackStack(null)
                .replace(R.id.navContent_frameLayout_container, fragment).commit();
    }
}