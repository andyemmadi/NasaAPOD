package com.example.ramu.nasaapod.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramu.nasaapod.R;

//import com.example.ramu.nasaapod.R;

/**
 * Created by Ramu on 1/25/2017.
 */

public class DayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_display,container,false);
        return v;
    }
}
