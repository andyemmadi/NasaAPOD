package com.example.ramu.nasaapod.Network;

import com.example.ramu.nasaapod.Model.OneDayData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ramu on 1/24/2017.
 */

public interface NasaService {
    @GET("planetary/apod?api_key=DEMO_KEY")
    Call<OneDayData> getTodaysPictureDate();
}
