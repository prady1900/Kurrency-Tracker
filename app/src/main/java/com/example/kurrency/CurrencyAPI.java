package com.example.kurrency;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CurrencyAPI {



    private static final String url="https://api.exchangeratesapi.io/";

    public static CurrencyService currencyService=null;

    public static CurrencyService getCurrencyService()
    {
        if (currencyService==null)
        {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            currencyService= retrofit.create(CurrencyService.class);
        }
        return currencyService;
    }
    public interface CurrencyService
    {
        @GET("/latest")
        Call<CurrencyList> getCurrencyList(@Query("base") String base);

        @GET("/{date}")
        Call<CurrencyList> getPreviousDayData(@Path("date") String date
                ,@Query("base") String base);

    }
}
