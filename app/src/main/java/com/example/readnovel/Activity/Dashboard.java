package com.example.readnovel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readnovel.Adapter.ComicAdapter;
import com.example.readnovel.Adapter.SearchAdapter;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.Model.Search;
import com.example.readnovel.R;
import com.example.readnovel.Untils.CheckConnect;
import com.example.readnovel.Untils.Link;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    //Khai báo view
    Button btnBXH, btnFavorite, btnTheloai, btnNewUpdate, btnHotTrend, btnTruyenGirl, btnTruyenBoy;
    TextView text;
    //Khai báo model lưu data
    private ArrayList<Comic> _listUpdate;
    private ArrayList<Comic> _listHottrend;
    private ArrayList<Comic> _listGirl;
    private ArrayList<Comic> _listBoy;
    private BannerSlider _sliderBanner;
    private ArrayList<String> _urlBanner;
    private ArrayList<Search> _search;
    //Khai báo Id trên file xml
    private ShimmerRecyclerView _newUpdate;
    private ShimmerRecyclerView _hotTrend;
    private ShimmerRecyclerView _Boy;
    private ShimmerRecyclerView _Girl;
    private AutoCompleteTextView _searchAuto;
    private List<Banner> _banner;
    private RelativeLayout _root;
    private CheckConnect _checkConnect;
    //Khai báo Adapter;
    private ComicAdapter _newUpdateAdapter;
    private SearchAdapter _seachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Khởi tạo thực thi ban đầu
        inttView();
        //Check mạng, tester cấm test việc đã vào cắt mạng nha
        _checkConnect = new CheckConnect(_root, Dashboard.this);
        _checkConnect.CheckConnection();
        //Khởi tạo Id view
        _newUpdate.showShimmer();
        _hotTrend.showShimmer();
        _Girl.showShimmer();
        _Boy.showShimmer();
        Realm.init(this);
        //Set sự kiện cho search
//        _searchAuto.addTextChangedListener(this);
//        _searchAuto.setThreshold(0);
        //Khởi tạo hàm xử lý
        loadComicNewupdate();
        loadComicHottrend();
        loadComicGirl();
        loadComicBoy();

    }

    //Khởi tạo UI
    private void inttView() {
        //Khởi tạo lưu trữ
        _listUpdate = new ArrayList<>();
        _listHottrend = new ArrayList<>();
        _listBoy = new ArrayList<>();
        _listGirl = new ArrayList<>();
        _banner = new ArrayList<>();
        _urlBanner = new ArrayList<>();
        _search = new ArrayList<>();
        //Khởi tạo giao diện, bảo sao nó cứ null point mà không thấy báo lỗi
        _sliderBanner = findViewById(R.id.banner_slider);
        _newUpdate = findViewById(R.id.NewUpdate);
        _hotTrend = findViewById(R.id.HotTrend);
        _Boy = findViewById(R.id.Boy);
        _Girl = findViewById(R.id.Girl);
        btnTheloai = findViewById(R.id.btnTheLoai);
        btnTheloai.setOnClickListener(this);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(this);
        btnNewUpdate = findViewById(R.id.btnNewUpdate);
        btnNewUpdate.setOnClickListener(this);
        btnHotTrend = findViewById(R.id.btnHotTrend);
        btnHotTrend.setOnClickListener(this);
        btnTruyenGirl = findViewById(R.id.btnTruyenGirl);
        btnTruyenGirl.setOnClickListener(this);
        btnTruyenBoy = findViewById(R.id.btnTruyenBoy);
        btnTruyenBoy.setOnClickListener(this);
        btnBXH = findViewById(R.id.btnBXH);
        btnBXH.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent more = new Intent(Dashboard.this, MoreActivity.class);
        switch (v.getId()) {
            case R.id.btnBXH:
                openRanking();
                break;
            case R.id.btnFavorite:
                openFavorite();
                break;
            case R.id.btnTheLoai:
                openTheLoai();
                break;
            case R.id.btnHotTrend:
                openHotTrend(more);
                break;
            case R.id.btnNewUpdate:
                openNewUpdate(more);
                break;
            case R.id.btnTruyenGirl:
                openTruyenGirl(more);
                break;
            case R.id.btnTruyenBoy:
                openTruyenBoy(more);
                break;
            default:
                break;
        }
    }

    private void openTheLoai() {
        Intent iOpenTheLoai = new Intent(Dashboard.this, CategoryActivity.class);
        startActivity(iOpenTheLoai);
    }

    private void openTruyenBoy(Intent more) {
        more.putExtra("url", Link.URL_HOMEPAGE);
        more.putExtra("title", "Truyện con trai thích");
        startActivity(more);
    }

    private void openTruyenGirl(Intent more) {
        more.putExtra("url", Link.URL_GIRL);
        more.putExtra("title", "Truyện con gái thích");
        startActivity(more);
    }

    private void openNewUpdate(Intent more) {
        more.putExtra("url", Link.URL_HOMEPAGE);
        more.putExtra("title", "Truyện mới cập nhật");
        startActivity(more);
    }

    private void openFavorite() {
        Intent iOpenFavorite = new Intent(Dashboard.this, FavoriteActivity.class);
        startActivity(iOpenFavorite);
    }

    private void openHotTrend(Intent more) {
        more.putExtra("url", Link.URL_HOT_TREND);
        more.putExtra("title", "Truyện hot");
        startActivity(more);
    }

    private void openRanking() {
        Intent iOpenRanking = new Intent(Dashboard.this, TopActivity.class);
        startActivity(iOpenRanking);
    }

    private void loadComicNewupdate() {
        _listUpdate.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Tạo request crawl data
                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Link.URL_HOMEPAGE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Thực thi
                        Document document = Jsoup.parse(response);
                        Elements elementAll = document.select("div#ctl00_divCenter");
                        Elements sub = elementAll.select(".item");
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
                            String thumbal;
                            String thumbal1 = hinhanh.attr("src");
                            String thumbal2 = hinhanh.attr("data-original");
                            if (thumbal2.equals("")) {
                                thumbal = thumbal1;
                            } else
                                thumbal = thumbal2;
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
                            if (thumbal.startsWith("http:") || thumbal.startsWith("https:")) {

                            } else {
                                thumbal = "http:" + thumbal;
                            }
                            String chapter = sochuong.text();
                            _listUpdate.add(new Comic(name, viewCount, thumbal, chapter, link));
                            //Log.i("test", String.valueOf(sochuong));
                        }

                        for (int i = 0; i < 10; i++) {
                            String url = _listUpdate.get(i).getLinkComic();
                            String thumbal = _listUpdate.get(i).getThumbal();
                            _banner.add(new RemoteBanner(thumbal));
                            _urlBanner.add(url);
                        }

                        _sliderBanner.setBanners(_banner);
                        _sliderBanner.setInterval(5000);
                        _sliderBanner.setDefaultIndicator(2);
                        _sliderBanner.setMustAnimateIndicators(true);
                        _sliderBanner.setLoopSlides(true);

                        _newUpdate.post(new Runnable() {
                            @Override
                            public void run() {
                                _newUpdateAdapter = new ComicAdapter(Dashboard.this, _listUpdate);
                                LinearLayoutManager horizontallayout = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                                _newUpdate.setLayoutManager(horizontallayout);
                                _newUpdate.setHasFixedSize(true);
                                _newUpdate.setItemAnimator(new DefaultItemAnimator());
                                _newUpdate.setAdapter(_newUpdateAdapter);
                                _newUpdate.hideShimmer();
                            }
                        });

                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                //Set load time and try out
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                requestQueue.add(stringRequest);
            }
        }).start(); //Khởi chạy thread nào
    }

    private void loadComicHottrend() {
        _listHottrend.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Link.URL_HOT_TREND, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Elements elementAll = document.select("div#ctl00_divCenter");
                        Elements sub = elementAll.select(".item");
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
                            String thumbal;
                            String thumbal1 = hinhanh.attr("src");
                            String thumbal2 = hinhanh.attr("data-original");
                            if (thumbal2.equals("")) {
                                thumbal = thumbal1;
                            } else thumbal = thumbal2;
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
                            if (thumbal.startsWith("http:") || thumbal.startsWith("https:")) {

                            } else {
                                thumbal = "http:" + thumbal;
                            }
                            String chapter = sochuong.text();
                            _listHottrend.add(new Comic(name, viewCount, thumbal, chapter, link));
                        }
                        _hotTrend.post(new Runnable() {
                            @Override
                            public void run() {
                                _newUpdateAdapter = new ComicAdapter(Dashboard.this, _listHottrend);
                                LinearLayoutManager horizontalLayout = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                                _hotTrend.setLayoutManager(horizontalLayout);
                                _hotTrend.setHasFixedSize(true);
                                _hotTrend.setItemAnimator(new DefaultItemAnimator());
                                _hotTrend.setAdapter(_newUpdateAdapter);
                                _hotTrend.hideShimmer();
                            }
                        });
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        1500,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                requestQueue.add(stringRequest);
            }
        }).start();
    }

    private void loadComicGirl() {
        _listGirl.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Link.URL_GIRL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        Elements elementAll = document.select("div#ctl00_divCenter");
                        Elements sub = elementAll.select(".item");
                        for (Element element : sub) {
                            Element hinhanh = element.getElementsByTag("img").get(0);
                            Element linktruyen = element.getElementsByTag("a").get(0);
                            Element sochuong = element.getElementsByTag("a").get(2);
                            Element tentruyen = element.getElementsByTag("h3").get(0);
                            Element luotxem = element.getElementsByTag("span").get(0);
                            Element luotxem2 = null;
                            try {
                                luotxem2 = element.getElementsByTag("span").get(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String thumbal;
                            String thumbal1 = hinhanh.attr("src");
                            String thumbal2 = hinhanh.attr("data-original");
                            if (thumbal2.equals("")) {
                                thumbal = thumbal1;
                            } else {
                                thumbal = thumbal2;
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
                            if (thumbal.startsWith("http:") || thumbal.startsWith("https:")) {
                            } else {
                                thumbal = "http:" + thumbal;
                            }
                            String chapter = sochuong.text();
                            _listGirl.add(new Comic(name, viewCount, thumbal, chapter, link));
                        }
                        _Girl.post(new Runnable() {
                            @Override
                            public void run() {
                                _newUpdateAdapter = new ComicAdapter(Dashboard.this, _listGirl);
                                LinearLayoutManager horizontallayout = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                                _Girl.setLayoutManager(horizontallayout);
                                _Girl.setHasFixedSize(true);
                                _Girl.setItemAnimator(new DefaultItemAnimator());
                                _Girl.setAdapter(_newUpdateAdapter);
                                _Girl.hideShimmer();
                            }
                        });
                    }
                }, new ErrorListener() {
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

    private void loadComicBoy() {
        _listBoy.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Link.URL_BOY, new Response.Listener<String>() {
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
                            String thumbal;
                            String thumbal1 = hinhanh.attr("src");
                            String thumbal2 = hinhanh.attr("data-original");
                            if (thumbal2.equals("")) {
                                thumbal = thumbal1;
                            } else {
                                thumbal = thumbal2;
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
                            if (thumbal.startsWith("http:") || thumbal.startsWith("https:")) {
                            } else {
                                thumbal = "http:" + thumbal;
                            }
                            String chapter = sochuong.text();
                            _listBoy.add(new Comic(name, viewCount, thumbal, chapter, link));
                        }
                        _Boy.post(new Runnable() {
                            @Override
                            public void run() {
                                _newUpdateAdapter = new ComicAdapter(Dashboard.this, _listBoy);
                                LinearLayoutManager horizontalLayout = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
                                _Boy.setLayoutManager(horizontalLayout);
                                _Boy.setHasFixedSize(true);
                                _Boy.setItemAnimator(new DefaultItemAnimator());
                                _Boy.setAdapter(_newUpdateAdapter);
                                _Boy.hideShimmer();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Todo something

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Todo something
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            requestSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestSearch() {
        _search.clear();
        final String la = _searchAuto.getText().toString();
        String keyword = la.replaceAll(" ", "+");
        String urlSearch = Link.URL_SEARCH;
        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlSearch, new Response.Listener<String>() {
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
                    _search.add(new Search(name, thumb, link));
                }
                _seachAdapter = new SearchAdapter(Dashboard.this, R.layout.item_custom_search, _search);
                _searchAuto.setAdapter(_seachAdapter);
                _seachAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
//                    catch (JSONException e2) {
//                        e2.printStackTrace();
//                    }
                }
            }
        }
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("status:", "onresume");
        _checkConnect.CheckConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("status:", "onPause");
    }
}