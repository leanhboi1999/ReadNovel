package com.example.readnovel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.readnovel.Model.Comic;
import com.example.readnovel.Model.ComicDatabase;
import com.example.readnovel.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    private final List<Comic> _list;
    private final Context _context;
    private final Realm _myRealm = Realm.getDefaultInstance();

    public ComicAdapter(Context context, List<Comic> list) {
        _list = list;
        _context = context;
    }

    @NonNull
    @Override
    //Hàm tạo ViewHolder, trong đấy chứa View dùng để hiển thị data
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_comic, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    //Chuyển dữ liệu vào ViewHolder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Comic item = _list.get(position);
        holder.txtChapter.setText(item.getChapter());
        holder.txtName.setText(item.getName());
        holder.txtView.setText(item.getView());

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;
                RealmResults<ComicDatabase> result = _myRealm.where(ComicDatabase.class).findAll();
                for (ComicDatabase database : result) {
                    if (database.getName() != null)
                        if (database.getName().equals(item.getName().trim())) {
                            Toast.makeText(_context, "Truyện đã có trong mục yêu thích!", Toast.LENGTH_SHORT).show();
                            check = true;
                            Log.e("Check", String.valueOf(check));
                            break;
                        }
                }

                if (!check) {
                    _myRealm.beginTransaction();
                    ComicDatabase cmDatabase = _myRealm.createObject(ComicDatabase.class);
                    cmDatabase.setName(item.getName());
                    cmDatabase.setChapter(item.getChapter());
                    cmDatabase.setThumb(item.getThumb());
                    cmDatabase.setView(item.getView());
                    cmDatabase.setUrl(item.getLinkComic());
                    _myRealm.commitTransaction();
                    Toast.makeText(_context, "Đã lưu truyện " + item.getName() + " vào mục yêu thích!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        Glide.with(_context).load(item.getThumb()).into(holder.imgThumbal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyển đến trang truyển
                //Todo, từ từ rồi làm
            }
        });
    }

    @Override
    //Lấy số phần tử của dữ liệu
    public int getItemCount() {
        return _list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Khởi tạo class ViewHolder kế thừa abstarct ViewHolder
        final TextView txtChapter;
        final TextView txtName;
        final TextView txtView;
        final ImageView imgThumbal;
        final ImageButton btnFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            txtChapter = itemView.findViewById(R.id.txtChapter);
            txtName = itemView.findViewById(R.id.txtName);
            txtView = itemView.findViewById(R.id.txtView);
            imgThumbal = itemView.findViewById(R.id.imgThumbal);
            btnFavorite = itemView.findViewById(R.id.btnLike);
        }
    }


}
