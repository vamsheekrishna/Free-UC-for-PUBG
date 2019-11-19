package com.example.myapplication.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//http://suggestions-75bd.kxcdn.com/Icreative/FreeUC/moreapps1.json
public class MyRetrofit {
    private static Retrofit retrofit = null;
    public static MoreMoneyAPI getInstance() {
        if (null == retrofit) {
            retrofit = new Retrofit
                    .Builder().baseUrl("http://suggestions-75bd.kxcdn.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MoreMoneyAPI.class);
    }
}
