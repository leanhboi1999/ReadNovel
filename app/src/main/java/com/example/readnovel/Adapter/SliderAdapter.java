package com.example.readnovel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.readnovel.Model.SliderItem;
import com.example.readnovel.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItem;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapter(List<SliderItem> sliderItem, ViewPager2 viewPager2) {
        this.sliderItem = sliderItem;
        this.viewPager2 = viewPager2;
    }

    public void setItems(List<SliderItem> sliderItem) {
        this.sliderItem = sliderItem;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImageView(sliderItem.get(position));
        if (position == sliderItem.size() - 2)
        {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItem.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgSlide);
        }

       void setImageView(SliderItem sliderItem){
           //custom settings for fast loading image
           RequestOptions options = new RequestOptions()
                   .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                   .centerCrop()
                   .priority(Priority.HIGH)
                   .format(DecodeFormat.PREFER_RGB_565);

           Glide.with(context)
                   .applyDefaultRequestOptions(options)
                   .load(sliderItem.getImageURL())
                   .thumbnail(0.4f)
                   .into(imageView);
       }

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItem.addAll(sliderItem);
            notifyDataSetChanged();
        }
    };
}
