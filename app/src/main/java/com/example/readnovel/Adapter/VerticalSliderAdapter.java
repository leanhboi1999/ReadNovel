package com.example.readnovel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.readnovel.Activity.PageComicActivity;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.Model.SliderItem;
import com.example.readnovel.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class VerticalSliderAdapter extends RecyclerView.Adapter<VerticalSliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItem;
    private ViewPager2 viewPager2;
    private Context context;
    private final ArrayList<Comic> listBookmark;
    private String Url;
    private String Name;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public VerticalSliderAdapter(List<SliderItem> sliderItem, ViewPager2 viewPager2, ArrayList<Comic> listBookmark) {
        this.sliderItem = sliderItem;
        this.viewPager2 = viewPager2;
        this.listBookmark = listBookmark;
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
                        R.layout.vertical_slide_item,
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Comic item = listBookmark.get(position);
                Intent intent = new Intent(context, PageComicActivity.class);
                for (int i = 0; i < 10; i++) {
                    intent.putExtra("url", item.getLinkComic());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderItem.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;
        private TextView txtBookmark;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgVerSlide);
            txtBookmark = itemView.findViewById(R.id.txtBookmark);
            txtBookmark.setText(Name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
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
