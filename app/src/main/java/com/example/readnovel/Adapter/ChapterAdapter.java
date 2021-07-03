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
import com.example.readnovel.Model.Bookmark;
import com.example.readnovel.Model.BookmarkDatabase;
import com.example.readnovel.Model.Chapter;
import com.example.readnovel.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private final List<Chapter> lst;
    private final Context mContext;
    private final Realm _myRealm = Realm.getDefaultInstance();
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
        holder.tvName.setText(item.getChapter());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData(item);
                Intent intent = new Intent(mContext, DetailComicActivity.class);
                intent.putExtra("url",item.getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    private void addData(Chapter item) {
        boolean check = false;
        RealmResults<BookmarkDatabase> result = _myRealm.where(BookmarkDatabase.class).findAll();
        for(BookmarkDatabase database : result) {
            if(database.getName() != null) {
                if(database.getChapter().equals(item.getChapter().trim())) {
                    check = true;
                    Bookmark temp = new Bookmark(database.getName(), database.getView(), database.getThumbal(), database.getChapter(), database.getLinkChapter());
                    BookmarkDatabase bm = _myRealm.where(BookmarkDatabase.class).equalTo("chapter", temp.getChapter()).findFirst();
                    _myRealm.beginTransaction();
                    bm.setChapter(temp.getChapter());
                    bm.setLinkChapter(temp.getLinkChapter());
                    bm.setView(temp.getView());
                    bm.setThumbal(temp.getThumbal());
                    bm.setName(temp.getName());
                    _myRealm.commitTransaction();
                    break;
                }
            }
        }

        if(!check) {
            _myRealm.beginTransaction();
            BookmarkDatabase bmDatabase = _myRealm.createObject(BookmarkDatabase.class);
            bmDatabase.setChapter(item.getChapter());
            bmDatabase.setLinkChapter(item.getUrl());
            bmDatabase.setName(item.getName());
            bmDatabase.setThumbal(item.getThumbal());
            bmDatabase.setView(item.getView());
            _myRealm.commitTransaction();
        }
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
