package com.example.readnovel.Activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readnovel.Adapter.SearchAdapter;
import com.example.readnovel.Adapter.ViewPagerAdapter;
import com.example.readnovel.Model.Search;
import com.example.readnovel.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TopActivity extends AppCompatActivity implements TextWatcher {
    private TabLayout tabLayout;

    private AutoCompleteTextView mEditAuto;
    private SearchAdapter adapterSearch;
    private ArrayList<Search> lstSearch;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        initView();
        mEditAuto.addTextChangedListener(this);
        adapterSearch = new SearchAdapter(this, R.layout.item_custom_search, lstSearch);
        mEditAuto.setAdapter(adapterSearch);
        mEditAuto.setThreshold(0);
        addControl();
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.white));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }

    private void initView() {
        mEditAuto = findViewById(R.id.editAuto);
        lstSearch = new ArrayList<>();
    }

    private void addControl() {
        ViewPager pager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setTabsFromPagerAdapter(adapter);//deprecated
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    public void onTextChanged(CharSequence arg0, int arg1,
                              int arg2, int arg3) {
    }

    public void afterTextChanged(Editable arg0) {
        try {
            requestSearch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    private void requestSearch() {
        lstSearch.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String la = mEditAuto.getText().toString();
                String keyword = la.replaceAll(" ", "+");
                String urlsearch = Link.URL_SEARCH + keyword;
                RequestQueue requestQueue = Volley.newRequestQueue(TopActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlsearch, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Elements all = document.select("div#ctl00_divCenter");
                        Elements sub = all.select(".item");
                        for (Element element : sub) {
                            Element hinhanh = element.getElementsByTag("img").get(0);
                            Element linktruyen = element.getElementsByTag("a").get(0);
                            Element tentruyen = element.getElementsByTag("h3").get(0);
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
                            if (thumb.startsWith("http:") || thumb.startsWith("https:")) {
                            } else {
                                thumb = "http:" + thumb;
                            }
                            lstSearch.add(new Search(name, thumb, link));
                        }
                        adapterSearch = new SearchAdapter(TopActivity.this, R.layout.item_custom_search, lstSearch);
                        mEditAuto.setAdapter(adapterSearch);
                        adapterSearch.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException | JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
                requestQueue.add(stringRequest);
            }
        }).start();
    }

}
