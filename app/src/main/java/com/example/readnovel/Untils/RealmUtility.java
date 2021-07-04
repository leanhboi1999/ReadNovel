package com.example.readnovel.Untils;

import io.realm.RealmConfiguration;


public class RealmUtility {
    public static RealmConfiguration getDefaultConfig() {
        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}