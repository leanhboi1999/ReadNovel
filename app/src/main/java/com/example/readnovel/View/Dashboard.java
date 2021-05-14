package com.example.readnovel.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.readnovel.Adapter.ComicAdapter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    //Khai báo view
    Button btnBXH;
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
        //Khởi tạo hàm xử lý
        //loadComicNewupdate();
        loadComicHottrend();
        loadComicGirl();
        loadComicBoy();
        Realm.init(this);
        //Set sự kiện cho search
        _searchAuto.addTextChangedListener(this);
        _searchAuto.setThreshold(0);
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
        _sliderBanner.findViewById(R.id.banner_slider);
        _newUpdate.findViewById(R.id.NewUpdate);
        _hotTrend.findViewById(R.id.HotTrend);
        _Boy.findViewById(R.id.Boy);
        _Girl.findViewById(R.id.Girl);
        Button btnTheloai = findViewById(R.id.btnTheLoai);
        btnTheloai.setOnClickListener(this);
        Button btnFavorite = findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(this);
        Button btnNewUpdate = findViewById(R.id.btnNewUpdate);
        btnNewUpdate.setOnClickListener(this);
        Button btnHotTrend = findViewById(R.id.btnHotTrend);
        btnHotTrend.setOnClickListener(this);
        Button btnTruyenGirl = findViewById(R.id.btnTruyenGirl);
        btnTruyenGirl.setOnClickListener(this);
        Button btnTruyenBoy = findViewById(R.id.btnTruyenBoy);
        btnTruyenBoy.setOnClickListener(this);
        btnBXH = findViewById(R.id.btnBXH);
        btnBXH.setOnClickListener(this);
    }

    private void openRanking() {
        Intent iOpenRanking = new Intent(Dashboard.this, TopActivity.class);
        startActivity(iOpenRanking);
    }

    @Override
    public void onClick(View v) {

    }

    private void loadComicBoy() {
    }

    private void loadComicGirl() {
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

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

            }
        });
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
                        }

                        for (int i = 0; i < 10; i++) {
                            String url = _listUpdate.get(i).getLinkComic();
                            String thumbal = _listUpdate.get(i).getThumbal();
                            _banner.add(new RemoteBanner(thumbal));
                            _urlBanner.add(url);
                        }

                        _sliderBanner.setBanners(_banner);
                        _sliderBanner.setInterval(1000);
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
                }, new Response.ErrorListener() {
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}