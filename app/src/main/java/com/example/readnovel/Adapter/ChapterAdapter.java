package com.example.readnovel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readnovel.Activity.DetailComicActivity;
import com.example.readnovel.Model.Chapter;
import com.example.readnovel.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private final List<Chapter> lst;
    private final Context mContext;
    public ChapterAdapter(Context context, List<Chapter> chapterList) {
        this.lst = chapterList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_custom_chapter, parent, false);

        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final Button tvName;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.btnName);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Chapter item = lst.get(position);
        holder.tvName.setText(item.getName());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailComicActivity.class);
                intent.putExtra("url",item.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
