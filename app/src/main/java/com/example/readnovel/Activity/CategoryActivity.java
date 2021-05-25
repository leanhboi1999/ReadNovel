package com.example.readnovel.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
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
import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.example.readnovel.Adapter.ChapterAdapter;
import com.example.readnovel.Adapter.LoadMoreAdapter;
import com.example.readnovel.Model.Chapter;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.R;
import com.example.readnovel.Untils.Link;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView mRvComic;

    private ArrayList<Chapter> lstTheLoai;
    private ArrayList<Comic> lstComic;
    private ArrayList<String> lstName;

    private ChapterAdapter KindAdapter;
    private LoadMoreAdapter comicAdapter;

    private PickerUI mUiViewPicker;


    private Boolean isScrolling = false;
    private int currentItems;
    private int totalItems;
    private int scrollOutItems;
    private LinearLayoutManager manager;
    private ArrayList<Comic> listMore;
    private int pos = 0;
    private ArrayList<String> lstUrl;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();

        mToolbar.setTitle(getString(R.string.categoryComic));
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        listMore = new ArrayList<>();
        loadCategory();


        comicAdapter = new LoadMoreAdapter(CategoryActivity.this, lstComic);
        manager = new LinearLayoutManager(CategoryActivity.this);
        mRvComic.setLayoutManager(manager);
        mRvComic.setHasFixedSize(true);
        mRvComic.setItemAnimator(new DefaultItemAnimator());
        mRvComic.setAdapter(comicAdapter);

        lstUrl = new ArrayList<>();

        mUiViewPicker.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                loadBook(lstTheLoai.get(position).getUrl());
            }
        });

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
        lstTheLoai = new ArrayList<>();
        lstName = new ArrayList<>();
        mUiViewPicker = findViewById(R.id.picker_ui_view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void loadBook(final String url) {
        lstComic.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(CategoryActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Elements all = document.select("div#ctl00_divCenter");
                        Elements sub = all.select(".item");
                        for (Element element : sub) {
                            Element image = element.getElementsByTag("img").get(0);
                            Element linkComic = element.getElementsByTag("a").get(0);
                            Element chap = element.getElementsByTag("a").get(2);
                            Element nameComic = element.getElementsByTag("h3").get(0);
                            Element viewer = element.getElementsByTag("span").get(0);
                            Element viewer2 = null;
                            try {
                                viewer2 = element.getElementsByTag("span").get(1);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            String thumb;
                            String thumb1 = image.attr("src");
                            String thumb2 = image.attr("data-original");
                            if (thumb2.equals("")) {
                                thumb = thumb1;
                            } else {
                                thumb = thumb2;
                            }
                            String name = nameComic.text();
                            String link = linkComic.attr("href");
                            String view;
                            if (viewer.text().equals("")) {
                                view = Objects.requireNonNull(viewer2).text();
                            } else {
                                view = viewer.text();
                            }
                            String string = view;
                            String[] parts = string.split(" ");
                            String viewCount = parts[0];
                            if (thumb.startsWith("http:") || thumb.startsWith("https:")) {

                            } else {
                                thumb = "http:" + thumb;
                            }
                            String chapter = chap.text();
                            lstComic.add(new Comic(name, viewCount, thumb, chapter, link));
                        }

                        Elements more = document.select("div#ctl00_mainContent_ctl01_divPager");
                        Elements more1 = more.select("ul.pagination");
                        Elements allli = more1.select("li");
                        Elements next = allli.select("a");
                        for (Element element : next) {
                            String url = element.attr("href");
                            lstUrl.add(url);
                        }
                        comicAdapter.notifyDataSetChanged();


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

    private void loadBookmore(final String url) {
        pos++;
        listMore.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(CategoryActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Elements all = document.select("div#ctl00_divCenter");
                        Elements sub = all.select(".item");
                        for (Element element : sub) {
                            Element image = element.getElementsByTag("img").get(0);
                            Element linkComic = element.getElementsByTag("a").get(0);
                            Element chap = element.getElementsByTag("a").get(2);
                            Element nameComic = element.getElementsByTag("h3").get(0);
                            Element viewer = element.getElementsByTag("span").get(0);
                            Element viewer2 = null;
                            try {
                                viewer2 = element.getElementsByTag("span").get(1);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            String thumb;
                            String thumb1 = image.attr("src");
                            String thumb2 = image.attr("data-original");
                            if (thumb2.equals("")) {
                                thumb = thumb1;
                            } else {
                                thumb = thumb2;
                            }
                            String name = nameComic.text();
                            String link = linkComic.attr("href");
                            String view;
                            if (viewer.text().equals("")) {
                                view = viewer2.text();
                            } else {
                                view = viewer.text();
                            }
                            String string = view;
                            String[] parts = string.split(" ");
                            String viewCount = parts[0];
                            if (thumb.startsWith("http:") || thumb.startsWith("https:")) {

                            } else {
                                thumb = "http:" + thumb;
                            }
                            String chapter = chap.text();
                            listMore.add(new Comic(name, viewCount, thumb, chapter, link));
                        }
                        lstComic.addAll(listMore);
                        comicAdapter.notifyDataSetChanged();


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

    private void loadCategory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(CategoryActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Link.URL_KIND, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lstTheLoai.clear();
                        Document document = Jsoup.parse(response);
                        Elements start = document.select("div.box.darkBox.genres.Module.Module-204");
                        Elements all = start.select("ul.nav");
                        Elements allli = all.select("li");
                        Elements category = allli.select("a");
                        for (Element element : category) {
                            String url = element.attr("href");
                            String name = element.text();
                            lstTheLoai.add(new Chapter(name, url));
                            lstName.add(name);
                        }

                        lstTheLoai.remove(lstTheLoai.size() - 1);
                        lstTheLoai.remove(lstTheLoai.size() - 1);
                        lstName.remove(lstName.size() - 1);
                        lstName.remove(lstName.size() - 1);
                        PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                                .withItems(lstName)
                                .withAutoDismiss(false)
                                .withColorTextCenter(Color.WHITE)
                                .withColorTextNoCenter(Color.DKGRAY)
                                .withItemsClickables(false)
                                .withUseBlur(false)
                                .build();
                        mUiViewPicker.setSettings(pickerUISettings);
                        loadBook(lstTheLoai.get(5).getUrl());
                        mUiViewPicker.slide(5);

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
