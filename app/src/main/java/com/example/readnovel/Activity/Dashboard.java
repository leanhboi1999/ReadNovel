package com.example.readnovel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

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
import com.example.readnovel.Adapter.SliderAdapter;
import com.example.readnovel.Adapter.VerticalSliderAdapter;
import com.example.readnovel.Model.Bookmark;
import com.example.readnovel.Model.BookmarkDatabase;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.Model.Search;
import com.example.readnovel.Model.SliderItem;
import com.example.readnovel.R;
import com.example.readnovel.Untils.CheckConnect;
import com.example.readnovel.Untils.Link;
import com.example.readnovel.Untils.RealmUtility;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    //Khai b??o view
    Button btnBXH, btnFavorite, btnTheloai, btnNewUpdate, btnHotTrend, btnTruyenGirl, btnTruyenBoy, btnMarkComic;
    TextView text;
    //Khai b??o model l??u data
    private ArrayList<Comic> _listUpdate;
    private ArrayList<Comic> _listHottrend;
    private ArrayList<Comic> _listGirl;
    private ArrayList<Comic> _listBoy;
    private ArrayList<Comic> listBookmark, listBMReverse;
    private List<SliderItem> sliderItems;
    private List<SliderItem> sliderItemsForVer;
    private LinearLayout bookmarkLayout;
//    private BannerSlider _sliderBanner;
    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;
    private Handler sliderHandler = new Handler();
    private ViewPager2 vpMarkComic;
    private VerticalSliderAdapter verSlierAdapter;

    private ArrayList<String> _urlBanner;
    private ArrayList<Search> _search;
    //Khai b??o Id tr??n file xml
    private ShimmerRecyclerView _newUpdate;
    private ShimmerRecyclerView _hotTrend;
    private ShimmerRecyclerView _Boy;
    private ShimmerRecyclerView _Girl;
    private AutoCompleteTextView _searchAuto;
    private List<Banner> _banner;
    private RelativeLayout _root;
    private CheckConnect _checkConnect;
    //Khai b??o Adapter;
    private ComicAdapter _newUpdateAdapter;
    private SearchAdapter _seachAdapter;
    //Khai b??o truy v???n database

    private ArrayList<Bookmark> bookmarks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Kh???i t???o th???c thi ban ?????u
        inttView();
        //Check m???ng, tester c???m test vi???c ???? v??o c???t m???ng nha
        _checkConnect = new CheckConnect(_root, Dashboard.this);
        _checkConnect.CheckConnection();
        //Kh???i t???o Id view
        _newUpdate.showShimmer();
        _hotTrend.showShimmer();
        _Girl.showShimmer();
        _Boy.showShimmer();

        //Update database
        Realm.init(this);
        RealmConfiguration config = RealmUtility.getDefaultConfig();
        Realm.setDefaultConfiguration(config);

        loadBookmark();
        loadComicNewupdate();
        loadComicHottrend();
        loadComicGirl();
        loadComicBoy();
        loadHistory();
    }

    private void loadHistory() {
        if (bookmarks.size()==0)
        {
            bookmarkLayout.setVisibility(View.GONE);
        }
        else
        {
            for (int i = 0; i<listBMReverse.size()-1; i++)
            {
                if (i<5)
                {
            sliderItemsForVer.add(new SliderItem(listBMReverse.get(i).getThumbal(),listBMReverse.get(i).getName()));
            verSlierAdapter.setUrl(listBMReverse.get(i).getLinkComic());
            verSlierAdapter.setItems(sliderItemsForVer);
            verSlierAdapter.setName(listBMReverse.get(i).getName());
            verSlierAdapter.notifyDataSetChanged();}

            }}


    }

    @Override
    public void onBackPressed() { }

    private void loadBookmark() {
        Realm _myRealm = Realm.getDefaultInstance();
        bookmarks.clear();
        RealmResults<BookmarkDatabase> result = _myRealm.where(BookmarkDatabase.class).findAll();
        for(BookmarkDatabase database :result) {
            bookmarks.add(new Bookmark(database.getName(),
                    database.getView(), database.getThumbal(), database.getChapter(), database.getLinkChapter()));
            listBookmark.add(new Comic(database.getName(),
                    database.getView(), database.getThumbal(), database.getChapter(), database.getLinkChapter()));
            for (int i =listBookmark.size()-1;i>listBookmark.size()-6;i--)
            {
                if (i==-1)
                    break;
                listBMReverse.add(listBookmark.get(i));
            }

        }

    }

    //Kh???i t???o UI
    private void inttView() {
        //Kh???i t???o l??u tr???
        _listUpdate = new ArrayList<>();
        _listHottrend = new ArrayList<>();
        _listBoy = new ArrayList<>();
        _listGirl = new ArrayList<>();
        _banner = new ArrayList<>();
        _urlBanner = new ArrayList<>();
        _search = new ArrayList<>();
        listBookmark = new ArrayList<>();
        listBMReverse = new ArrayList<>();
        //Kh???i t???o giao di???n, b???o sao n?? c??? null point m?? kh??ng th???y b??o l???i

        //init Slider
        initSlider();
        sliderItems = new ArrayList<>();
        sliderItemsForVer = new ArrayList<>();
        sliderAdapter = new SliderAdapter(sliderItems, viewPager2, _listUpdate);
        verSlierAdapter = new VerticalSliderAdapter(sliderItemsForVer,vpMarkComic,listBMReverse);
        viewPager2.setAdapter(sliderAdapter);
        vpMarkComic.setAdapter(verSlierAdapter);

        btnMarkComic = findViewById(R.id.btnMarkComic);
        btnMarkComic.setOnClickListener(this);
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
        bookmarkLayout = findViewById(R.id.bookmarkLayout);
    }

    private void initSlider() {
        viewPager2 = findViewById(R.id.banner_slider);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.setCurrentItem(2);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        vpMarkComic = findViewById(R.id.vpMarkComic);
        vpMarkComic.setClickable(false);
        vpMarkComic.setClipChildren(false);
        vpMarkComic.setOffscreenPageLimit(3);
        vpMarkComic.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        vpMarkComic.setCurrentItem(2);
        vpMarkComic.setPageTransformer(compositePageTransformer);
        vpMarkComic.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 1800);
            }
        });
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
            case R.id.btnMarkComic:
                openViewPager();
            default:
                break;
        }
    }

    private void openViewPager() {
        if (vpMarkComic.isShown())
        {
            vpMarkComic.setVisibility(View.GONE);
        }
        else
        {
        vpMarkComic.setVisibility(View.VISIBLE);}


    }

    private void openTheLoai() {
        Intent iOpenTheLoai = new Intent(Dashboard.this, CategoryActivity.class);
        startActivity(iOpenTheLoai);
    }

    private void openTruyenBoy(Intent more) {
        more.putExtra("url", Link.URL_HOMEPAGE);
        more.putExtra("title", "Truy???n con trai th??ch");
        startActivity(more);
    }

    private void openTruyenGirl(Intent more) {
        more.putExtra("url", Link.URL_GIRL);
        more.putExtra("title", "Truy???n con g??i th??ch");
        startActivity(more);
    }

    private void openNewUpdate(Intent more) {
        more.putExtra("url", Link.URL_HOMEPAGE);
        more.putExtra("title", "Truy???n m???i c???p nh???t");
        startActivity(more);
    }

    private void openFavorite() {
        Intent iOpenFavorite = new Intent(Dashboard.this, FavoriteActivity.class);
        startActivity(iOpenFavorite);
    }

    private void openHotTrend(Intent more) {
        more.putExtra("url", Link.URL_HOT_TREND);
        more.putExtra("title", "Truy???n hot");
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
                //T???o request crawl data
                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Link.URL_HOMEPAGE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Th???c thi
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
//                            _banner.add(new RemoteBanner(thumbal));
//                            _banner.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
//                            _urlBanner.add(url);
                            Log.d("Ho",url);
                            sliderItems.add(new SliderItem(thumbal));
                            sliderAdapter.setUrl(url);
                            sliderAdapter.setItems(sliderItems);
                            sliderAdapter.notifyDataSetChanged();

                            //

                        }

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
        }).start(); //Kh???i ch???y thread n??o
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            vpMarkComic.setCurrentItem(vpMarkComic.getCurrentItem() + 1);
        }
    };

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String la = _searchAuto.getText().toString();
                String keyword = la.replaceAll(" ", "+");
                String urlsearch = Link.URL_SEARCH + keyword;
                RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
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