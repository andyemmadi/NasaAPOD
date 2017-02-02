package com.example.ramu.nasaapod.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ramu.nasaapod.DataBase.DatabaseHandler;
import com.example.ramu.nasaapod.Model.OneDayData;
import com.example.ramu.nasaapod.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * Created by Ramu on 1/24/2017.
 */

public class RecyclerDayAdapter extends RecyclerView.Adapter<RecyclerDayAdapter.DayViewHolder> {

    Context mcontext;

    /**
     * dayList is temparory list, this is used generally for setting the recyclerview.
     * daylist size may change based on user selection.
     */
    List<OneDayData> daysList;
    LayoutInflater mInflator;

    /**
     * masterDaysList is master datalist, which preserves data that fetched from database.
     */
    List<OneDayData> masterDaysList;

    DatabaseHandler databaseHandler = null;
    public int[] selected_days_array;

    RecyclerDayAdapter(Context context, DatabaseHandler t_databaseHandler){
        this.mcontext = context;
        Log.e("three","excuted");
        //this.daysList = databaseHandler.getAllAPODS();
        this.mInflator = LayoutInflater.from(mcontext);
        this.masterDaysList = t_databaseHandler.getAllAPODS();
        this.databaseHandler = t_databaseHandler;
        /**
         * this will sort the list according the date in List<>
         *     sort functionality written OneDayData.java
         */
        Collections.sort(masterDaysList, OneDayData.OneDayMilliComparator);

        /**
         *
         */
        this.daysList = this.masterDaysList;
        Log.e("four","excuted");

    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.recycler_day_item,parent,false);
        DayViewHolder viewHolder = new DayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        OneDayData single_day = daysList.get(position);
        holder.explanation.setText(single_day.getExplanation());
        holder.title.setText(single_day.getTitle());

        String date_str = formateDateFromstring("yyyy-MM-dd", "dd, MMM yyyy", single_day.getDate());

        //Date date = new Date();

        holder.date.setText(date_str);
        Glide.with(mcontext)
                .load(single_day.getUrl())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.dangerous_woman)
                .into(holder.picutreOfDay);
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder{

        ImageView picutreOfDay;
        TextView title;
        TextView explanation;
        TextView date;

        /**
         * isTextViewClicked
         */
        boolean isTextViewClicked = false;

        public DayViewHolder(View itemView) {
            super(itemView);
            picutreOfDay = (ImageView) itemView.findViewById(R.id.image_day);
            title = (TextView) itemView.findViewById(R.id.day_title);
            explanation = (TextView) itemView.findViewById(R.id.day_explanation);
            date = (TextView) itemView.findViewById(R.id.day_date);

            explanation.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isTextViewClicked){
                        //This will shrink textview to 2 lines if it is expanded.
                        explanation.setVisibility(View.GONE);
                        explanation.setMaxLines(2);
                        isTextViewClicked = false;
                    } else {
                        //This will expand the textview if it is of 2 lines
                        explanation.setVisibility(View.VISIBLE);
                        explanation.setMaxLines(Integer.MAX_VALUE);
                        isTextViewClicked = true;
                    }
                }
            });
        }
    }


    public int[] getSelected_days_array(){

        selected_days_array = new int[daysList.size()];
        for(int i=0;i<daysList.size();i++)
        {
            String date = daysList.get(i).getDate();
            String day = date.substring(date.length()-2);
            int date_int = Integer.parseInt(day);
            selected_days_array[i] = date_int;
        }
        return selected_days_array;
    }



    public void refreshRecyclerView(){

        if(databaseHandler != null && databaseHandler.getAPODCount()>0)
        {
            Log.e("recycler view","refreshed");
            masterDaysList = databaseHandler.getAllAPODS();
            Collections.sort(masterDaysList, OneDayData.OneDayMilliComparator);
            daysList = masterDaysList;
            notifyDataSetChanged();
        }
    }


    public OneDayData getThisDay(String date){
        //OneDayData day;
        int pos;
        for(int i=0;i<daysList.size();i++)
        {
            if(date.equals(daysList.get(i).getDate()))
            {
                return daysList.get(i);
            }
        }
        return null;
    }

    public boolean showThisList(String date){

        if(date.equals("all")){
            daysList = masterDaysList;
            //Collections.sort(daysList,Collections.reverseOrder());
            Collections.sort(masterDaysList, OneDayData.OneDayMilliComparator);
            notifyDataSetChanged();
        }
        else{

            OneDayData data = null;
            for(int i=0;i<masterDaysList.size();i++){
                if(date.equals(masterDaysList.get(i).getDate())){
                    data = masterDaysList.get(i);
                }
            }
            List<OneDayData> temp_list = new ArrayList<>();
            if(data != null)
            {
                temp_list.add(data);
                daysList = temp_list;
                notifyDataSetChanged();
            }
            else{
                daysList = temp_list;

               /// daysList.sort();
               // daysList.sort(Comparator.comparing(OneDayData::getId).reversed());
                notifyDataSetChanged();
                //switchEmptyView(true);
                return false;
            }

        }
        return true;
    }


    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.e("error: ", "ParseException - dateFormat");
        }
        return outputDate;
    }
}
