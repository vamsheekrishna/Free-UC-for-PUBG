package com.example.myapplication.server;

import com.example.myapplication.models.AppInfos;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoreMoneyAPI {
    @GET("/Icreative/FreeUC/moreapps1.json")
    Call<AppInfos> getAllPhotos();
}
