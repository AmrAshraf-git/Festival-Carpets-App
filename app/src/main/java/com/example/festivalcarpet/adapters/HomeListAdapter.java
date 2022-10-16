package com.example.festivalcarpet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalcarpet.dataModels.Carpet;
import com.example.festivalcarpet.R;

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
        TextView carpetModel, carpetCategory, carpetColor, carpetSize,price;

        public ViewHolder(final View view)
        {
            super(view);

            //Inflating
            vehicleImage =view.findViewById(R.id.homeListRow_imgView_car);
            carpetModel=view.findViewById(R.id.homeListRow_txtView_carpetModel);
            carpetCategory =view.findViewById(R.id.homeListRow_txtView_carpetCategory);
            carpetColor =view.findViewById(R.id.homeListRow_txtView_spec1);
            carpetSize =view.findViewById(R.id.homeListRow_txtView_spec2);
            price=view.findViewById(R.id.homeListRow_txtView_price);
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
            carpetModel.setText(carpet.getCarpetModel());
            vehicleImage.setImageResource(carpet.getCarpetImg()[0]);
            carpetCategory.setText(carpet.getCarpetCategory());
            carpetColor.setText(carpet.getCarpetColor());
            carpetSize.setText(carpet.getCarpetSize());
            price.setText(String.valueOf("Price: "+carpet.getPrice()) + " L.E");
        }
    }

    public interface OnRecyclerViewClickListener {
        void onItemClick(int position);
    }

}
