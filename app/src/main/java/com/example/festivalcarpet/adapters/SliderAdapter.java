package com.example.festivalcarpet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.festivalcarpet.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.ViewHolder>{

    private final int[] images;

    public SliderAdapter(int[] images){

        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.default_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    static class ViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderItem_imgView_sliderImg);
        }
    }



}
