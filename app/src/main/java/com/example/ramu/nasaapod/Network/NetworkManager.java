package com.example.ramu.nasaapod.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ramu on 1/24/2017.
 */

public class NetworkManager {

    public static final String BaseURL = "https://api.nasa.gov/";
    private Retrofit mretrofit;
    private NasaService mNasaservices;
    public NetworkManager(){
        mretrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNasaservices = mretrofit.create(NasaService.class);

    }

    public NasaService getmNasaservices(){
        return this.mNasaservices;
    }
}
