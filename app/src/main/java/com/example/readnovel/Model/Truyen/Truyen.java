package com.example.readnovel.Model.Truyen;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.io.Serializable;

@Entity(tableName = "Truyen")
public class Truyen implements Serializable {
    @PrimaryKey
    @NonNull
    private String MaTruyen;

    @ColumnInfo(name = "TenTruyen")
    private String TenTuyen;

    @ColumnInfo(name = "MaTacGia")
    private String MaTacGia;

    @ColumnInfo(name = "TomTat")
    private String TomTat;

    @ColumnInfo(name = "SoLuotDoc")
    private int SoLuotDoc;

    @ColumnInfo(name = "DiemTrungBinh")
    private float DiemTrungBinh;

    @ColumnInfo(name = "SoChuongDaRa")
    private int SoChuongDaRa;

    @ColumnInfo(name = "SoChuongDuKien")
    private int SoChuongDuKien;

    @ColumnInfo(name = "TrangThai")
    private int TrangThai;

    public Truyen(@NonNull String maTruyen, String tenTuyen, String maTacGia, String tomTat, int soLuotDoc, float diemTrungBinh, int soChuongDaRa, int soChuongDuKien, int trangThai) {
        MaTruyen = maTruyen;
        TenTuyen = tenTuyen;
        MaTacGia = maTacGia;
        TomTat = tomTat;
        SoLuotDoc = soLuotDoc;
        DiemTrungBinh = diemTrungBinh;
        SoChuongDaRa = soChuongDaRa;
        SoChuongDuKien = soChuongDuKien;
        TrangThai = trangThai;
    }

    @NonNull
    public String getMaTruyen() {
        return MaTruyen;
    }

    public String getTenTuyen() {
        return TenTuyen;
    }

    public String getMaTacGia() {
        return MaTacGia;
    }

    public String getTomTat() {
        return TomTat;
    }

    public int getSoLuotDoc() {
        return SoLuotDoc;
    }

    public float getDiemTrungBinh() {
        return DiemTrungBinh;
    }

    public int getSoChuongDaRa() {
        return SoChuongDaRa;
    }

    public int getSoChuongDuKien() {
        return SoChuongDuKien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setMaTruyen(@NonNull String maTruyen) {
        MaTruyen = maTruyen;
    }

    public void setTenTuyen(String tenTuyen) {
        TenTuyen = tenTuyen;
    }

    public void setMaTacGia(String maTacGia) {
        MaTacGia = maTacGia;
    }

    public void setTomTat(String tomTat) {
        TomTat = tomTat;
    }

    public void setSoLuotDoc(int soLuotDoc) {
        SoLuotDoc = soLuotDoc;
    }

    public void setDiemTrungBinh(float diemTrungBinh) {
        DiemTrungBinh = diemTrungBinh;
    }

    public void setSoChuongDaRa(int soChuongDaRa) {
        SoChuongDaRa = soChuongDaRa;
    }

    public void setSoChuongDuKien(int soChuongDuKien) {
        SoChuongDuKien = soChuongDuKien;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }
}
