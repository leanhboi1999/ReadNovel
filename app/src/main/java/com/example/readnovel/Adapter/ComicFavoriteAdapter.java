package com.example.readnovel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.readnovel.Activity.PageComicActivity;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.Model.ComicDatabase;
import com.example.readnovel.R;


import java.util.List;

import io.realm.Realm;

public class ComicFavoriteAdapter extends RecyclerView.Adapter<ComicFavoriteAdapter.ViewHolder> {
    private final List<Comic> lst;
    private final Context mContext;
    private String name;

    public ComicFavoriteAdapter(Context context, List<Comic> comicList) {
        this.lst = comicList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_comic_custom, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Comic item = lst.get(holder.getAdapterPosition());
        holder.tvChapter.setText(item.getChapter());
        holder.tvName.setText(item.getName());
        holder.tvView.setText(item.getView());
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteFromDatabase(item.getName(), holder.getAdapterPosition());
            }
        });
        Glide.with(mContext).load(item.getThumbal()).into(holder.thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PageComicActivity.class);
                intent.putExtra("url", item.getLinkComic());
                mContext.startActivity(intent);

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvChapter;
        final TextView tvName;
        final TextView tvView;
        final ImageView thumbnail;
        final ImageButton btn_Delete;

        ViewHolder(View itemView) {
            super(itemView);
            tvChapter = itemView.findViewById(R.id.tvChapter);
            tvName = itemView.findViewById(R.id.tvName);
            tvView = itemView.findViewById(R.id.tv_View);
            thumbnail = itemView.findViewById(R.id.imgThumb);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
        }

    }


    private void deleteFromDatabase(final String itemName, final int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm bgRealm) {
                ComicDatabase item = bgRealm.where(ComicDatabase.class).equalTo("name", itemName).findFirst();
                if (item != null) {
                    item.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.'
                removerItem(position);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

    }

    private void removerItem(int position) {
        lst.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, lst.size());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


}
