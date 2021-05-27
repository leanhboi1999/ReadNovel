package com.example.readnovel.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.readnovel.Adapter.LoadMoreAdapter;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.R;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class MoreActivity extends AppCompatActivity {

    private RecyclerView mRvComic;

    private ArrayList<Comic> lstComic;
    private LoadMoreAdapter adapterBXH;

    private Boolean isScrolling = false;
    private int currentItems;
    private int totalItems;
    private int scrollOutItems;
    private LinearLayoutManager manager;
    private ArrayList<Comic> lstmore;
    private int pos = 0;
    private ArrayList<String> lstUrl;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadBXH(url);

        adapterBXH = new LoadMoreAdapter(MoreActivity.this, lstComic);
        manager = new LinearLayoutManager(MoreActivity.this);
        mRvComic.setLayoutManager(manager);
        mRvComic.setHasFixedSize(true);
        mRvComic.setItemAnimator(new DefaultItemAnimator());
        mRvComic.setAdapter(adapterBXH);


        setScrollRV();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setScrollRV() {
        mRvComic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    if (pos <= lstUrl.size()) {
                        loadBookmore(lstUrl.get(pos));
                    }
                }
            }
        });
    }

    private void initView() {
        mRvComic = findViewById(R.id.rvComic);
        lstComic = new ArrayList<>();
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        lstmore = new ArrayList<>();
        lstUrl = new ArrayList<>();
    }

    private void loadBookmore(final String url) {
        pos++;
        lstmore.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(MoreActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Elements all = document.select("div#ctl00_divCenter");
                        Elements sub = all.select(".item");
                        for (Element element : sub) {
                            Element hinhanh = element.getElementsByTag("img").get(0);
                            Element linktruyen = element.getElementsByTag("a").get(0);
                            Element sochuong = element.getElementsByTag("a").get(2);
                            Element tentruyen = element.getElementsByTag("h3").get(0);
                            Element luotxem = element.getElementsByTag("span").get(0);
                            Element luotxem2 = null;
                            try {
                                luotxem2 = element.getElementsByTag("span").get(1);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            String thumb;
                            String thumb1 = hinhanh.attr("src");
                            String thumb2 = hinhanh.attr("data-original");
                            if (thumb2.equals("")) {
                                thumb = thumb1;
                            } else {
                                thumb = thumb2;
                            }
                            String name = tentruyen.text();
                            String link = linktruyen.attr("href");
                            String view;
                            if (luotxem.text().equals("")) {
                                view = Objects.requireNonNull(luotxem2).text();
                            } else {
                                view = luotxem.text();
                            }
                            String string = view;
                            String[] parts = string.split(" ");
                            String viewCount = parts[0];
                            if (thumb.startsWith("http:") || thumb.startsWith("https:")) {

                            } else {
                                thumb = "http:" + thumb;
                            }
                            String chapter = sochuong.text();
                            lstmore.add(new Comic(name, viewCount, thumb, chapter, link));
                        }
                        lstComic.addAll(lstmore);
                        adapterBXH.notifyDataSetChanged();

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

    private void loadBXH(final String url) {
        lstComic.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(MoreActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final Document document = Jsoup.parse(response);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Elements more = document.select("div#ctl00_mainContent_ctl00_divPager");
                                Log.e("name:", more.toString());

                                Elements more1 = more.select("ul.pagination");

                                Elements allli = more1.select("li");
                                Elements next = allli.select("a");
                                for (Element element : next) {
                                    String url = element.attr("href");
                                    Log.e("name:", url);

                                    lstUrl.add(url);
                                }

                            }
                        }).start();

                        Elements all = document.select("div#ctl00_divCenter");
                        Elements sub = all.select(".item");
                        for (Element element : sub) {
                            Element hinhanh = element.getElementsByTag("img").get(0);
                            Element linktruyen = element.getElementsByTag("a").get(0);
                            Element sochuong = element.getElementsByTag("a").get(2);
                            Element tentruyen = element.getElementsByTag("h3").get(0);
                            Element luotxem = element.getElementsByTag("span").get(0);
                            Element luotxem2 = null;
                            try {
                                luotxem2 = element.getElementsByTag("span").get(1);
                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }
                            String thumb;
                            String thumb1 = hinhanh.attr("src");
                            String thumb2 = hinhanh.attr("data-original");
                            if (thumb2.equals("")) {
                                thumb = thumb1;
                            } else {
                                thumb = thumb2;
                            }
                            String name = tentruyen.text();
                            String link = linktruyen.attr("href");
                            String view;
                            if (luotxem.text().equals("")) {
                                view = luotxem2.text();
                            } else {
                                view = luotxem.text();
                            }
                            String string = view;
                            String[] parts = string.split(" ");
                            String viewCount = parts[0];
                            if (thumb.startsWith("http:") || thumb.startsWith("https:")) {

                            } else {
                                thumb = "http:" + thumb;
                            }

                            String chapter = sochuong.text();
                            lstComic.add(new Comic(name, viewCount, thumb, chapter, link));
                        }
                        adapterBXH.notifyDataSetChanged();
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

}
