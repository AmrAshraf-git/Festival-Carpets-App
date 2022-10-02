package com.example.carrental.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrental.dataModels.Carpet;
import com.example.carrental.R;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private final ArrayList<Carpet> arrayList;
    private final OnRecyclerViewClickListener onRecyclerViewClickListener;

    public HomeListAdapter(ArrayList<Carpet> arrayList, OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.arrayList = arrayList;
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    //Same as "getView method" but, this method includes if(view==null){Save the ViewHolder object in cached view} and else{get the ViewHolder object from cached view}.
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public Carpet getItem(final int pos) {
        return arrayList.get(pos);
    }

    @Override
    public long getItemId(final int position) {
        return (getItem(position).getId()+position);
    }

    @Override
    public int getItemViewType(final int position) {
        return position;
        //return R.layout.activity_home_list_row;
    }

    @Override
    //What should do in each object of the ViewHolder
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        Carpet carpet;
        ImageView vehicleImage;
        RatingBar compRate;
        TextView compLocation,companyName, vehicleModel, vehicleColor, doorsNum, seatingCapacity, transmission,priceLabel,price;

        public ViewHolder(final View view)
        {
            super(view);

            //Inflating
            vehicleImage =view.findViewById(R.id.homeListRow_imgView_car);
            //compRate=view.findViewById(R.id.homeListRow_ratingBar_compRate);
            //compLocation=view.findViewById(R.id.homeListRow_txtView_compLocation);
            companyName=view.findViewById(R.id.homeListRow_txtView_compName);
            vehicleModel =view.findViewById(R.id.homeListRow_txtView_carModel);
            vehicleColor =view.findViewById(R.id.homeListRow_txtView_spec1);
            doorsNum =view.findViewById(R.id.homeListRow_txtView_spec2);
            //seatingCapacity =view.findViewById(R.id.homeListRow_txtView_spec3);
            //transmission =view.findViewById(R.id.homeListRow_txtView_spec4);
            price=view.findViewById(R.id.homeListRow_txtView_price);
            priceLabel=view.findViewById(R.id.homeListRow_txtView_lbl);
            itemView.setOnClickListener(v -> {
                //Log.d("click","Adapter_onItemClick");
                if(onRecyclerViewClickListener!=null){
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                        onRecyclerViewClickListener.onItemClick(position);
                    //onRecyclerViewClickListener.onItemClick(HomeListAdapter.this.getItemId(getAdapterPosition()),getItem(getAdapterPosition()));
                }
            });
        }

        void bind(final Carpet carpet){
            this.carpet = carpet;

            priceLabel.setText(carpet.getPriceLabel().toString());
            vehicleImage.setImageResource(carpet.getCarpetImg()[0]);
            companyName.setText(carpet.getCompanyName());
            vehicleModel.setText(carpet.getCarpetModel());
            vehicleColor.setText(carpet.getCarpetColor());
            //doorsNum.setText(String.valueOf(carpet.getDoorsNum()));
            //seatingCapacity.setText(String.valueOf(carpet.getSeatingCapacity()));
            //transmission.setText(carpet.getVehicleSpecs().getAutomaticTransmission()?"Automatic":"Manual");
            price.setText(String.valueOf(carpet.getPrice()));
        }
    }

    public interface OnRecyclerViewClickListener {
        void onItemClick(int position);
    }

}
