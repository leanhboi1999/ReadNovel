package com.example.readnovel.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readnovel.Adapter.ComicViewAdapter;
import com.example.readnovel.Model.Image;
import com.example.readnovel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class DetailComicActivity extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {
    private ArrayList<Image> lstImage;
    private ComicViewAdapter adapter;
    private RecyclerView mRvComic;
    private ImageView imgPreChap, imgNextChap;
    private TextView txtChapName;
    private GestureDetector gestureDetector;
    private RelativeLayout rlChapter,rlChapterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        initView();
        Intent intent = getIntent();
        String URL_IMAGE = intent.getStringExtra("url");
        mRvComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSystemUI();
            }
        });
        loadBook(URL_IMAGE);
        onSystemUiVisibilityChange(2);
        onHandleChapterBehavior();
        onNextPrevButton();
    }

    private void onNextPrevButton() {

    }

    private void onHandleChapterBehavior() {
        mRvComic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && rlChapter.isShown()) {
                    rlChapter.setVisibility(View.GONE);
                }
                else if (dy < 0 ) {
                    rlChapter.setVisibility(View.VISIBLE);

                }

            }
        });
        rlChapterLayout.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View view) {
                Toast.makeText(DetailComicActivity.this,"Hi",Toast.LENGTH_LONG).show();
                if (rlChapter.isShown())
                {
                    rlChapter.setVisibility(View.GONE);
                }
                else
                {
                    rlChapter.setVisibility(View.VISIBLE);
                }
                return false;
            }
        }); }

    private void loadBook(final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(DetailComicActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        //load list chapter
                        lstImage.clear();
                        Elements chap = document.select("div.reading-detail.box_doc");
                        Elements aElements = chap.select("img");
                        for (Element element : aElements) {
                            String url = element.attr("src");
                            lstImage.add(new Image(url));
                        }
                        mRvComic.post(new Runnable() {
                            @Override
                            public void run() {

                                adapter = new ComicViewAdapter(getApplicationContext(), lstImage);
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                mRvComic.setLayoutManager(mLayoutManager);
                                mRvComic.setHasFixedSize(true);
                                mRvComic.setItemAnimator(new DefaultItemAnimator());
                                mRvComic.setAdapter(adapter);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        150000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

            }
        }).start();
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    private void initView() {
        lstImage = new ArrayList<>();
        mRvComic = findViewById(R.id.rvComic);
        imgNextChap = findViewById(R.id.imgNextChap);
        imgPreChap = findViewById(R.id.imgPreChap);
        txtChapName = findViewById(R.id.txtChapName);
        rlChapter = findViewById(R.id.rlChapter);
        rlChapterLayout = findViewById(R.id.rlChapterLayout);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {

    }
}
