package com.example.readnovel.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readnovel.Adapter.ComicFavoriteAdapter;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.Model.ComicDatabase;
import com.example.readnovel.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteActivity extends AppCompatActivity {
    private ArrayList<Comic> lstBXH;
    private RecyclerView mFavoriteRv;
    private LinearLayout mNoPost;
    private Toolbar mToolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();

        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        mToolbar.setTitle(getString(R.string.favorite));
        mToolbar.setTitleTextColor(Color.WHITE);
        Realm myRealm = Realm.getDefaultInstance();
        RealmResults<ComicDatabase> results1 =
                myRealm.where(ComicDatabase.class).findAll();
        for (ComicDatabase c : results1) {
            lstBXH.add(new Comic(c.getName(), c.getView(), c.getThumbal(), c.getChapter(), c.getUrl()));
        }
        if (lstBXH.size() == 0) {
            mNoPost.setVisibility(View.VISIBLE);
        } else {
            mNoPost.setVisibility(View.GONE);
        }
        ComicFavoriteAdapter adapterBXH = new ComicFavoriteAdapter(FavoriteActivity.this, lstBXH);
        RecyclerView.LayoutManager verticalLayout = new GridLayoutManager(FavoriteActivity.this, 3);
        mFavoriteRv.setLayoutManager(verticalLayout);
        mFavoriteRv.setHasFixedSize(true);
        mFavoriteRv.setItemAnimator(new DefaultItemAnimator());
        mFavoriteRv.setAdapter(adapterBXH);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView() {
        lstBXH = new ArrayList<>();
        mFavoriteRv = findViewById(R.id.rv_favorite);
        mNoPost = findViewById(R.id.noPost);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
