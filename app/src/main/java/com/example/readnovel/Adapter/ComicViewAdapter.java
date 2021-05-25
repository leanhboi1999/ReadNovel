package com.example.readnovel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.readnovel.Model.Image;
import com.example.readnovel.R;

import java.util.List;

public class ComicViewAdapter extends RecyclerView.Adapter<ComicViewAdapter.ViewHolder> {

    private final List<Image> lst;
    private final Context mContext;

    public ComicViewAdapter(Context context, List<Image> chapterList) {
        this.lst = chapterList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_custom_image, parent, false);

        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgComic;

        ViewHolder(View itemView) {
            super(itemView);
            imgComic = itemView.findViewById(R.id.photo_view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Image item = lst.get(position);

//        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
//        imageLoader.displayImage(item.getUrl().toString(), holder.imgComic);

        Glide.with(mContext).load(item.getUrl()).into(holder.imgComic);
//        CustomPicasso.with(mContext)
//                .load(item.getUrl().toString())
//                .error(R.drawable.bgtest)
//                .into(holder.imgComic);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

}
