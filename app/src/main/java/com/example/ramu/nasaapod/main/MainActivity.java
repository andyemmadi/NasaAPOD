package com.example.ramu.nasaapod.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ramu.nasaapod.DataBase.DatabaseHandler;
import com.example.ramu.nasaapod.Model.OneDayData;
import com.example.ramu.nasaapod.Network.NetworkManager;
import com.example.ramu.nasaapod.Other.StaticDataSetup;
import com.example.ramu.nasaapod.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity{


    RelativeLayout relativelayout;
    RecyclerView mRecyclerView;
    RecyclerDayAdapter mRecyclerAdapter;
    LinearLayoutManager mLayoutManager;
    DatabaseHandler databaseHandler;
    NetworkManager mnetworkManager;
    String today_date = "";
    String tomorrow_date = "";
    TextView empty_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativelayout = (RelativeLayout) findViewById(R.id.activity_main);
        empty_text = (TextView) findViewById(R.id.empty_textview);


        /**
         * for tomorrow data
         */
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tomorrow_date = dateFormat.format(tomorrow);


        /**
         * getting today's date
         */
        today_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());



        databaseHandler = new DatabaseHandler(this);
        mnetworkManager = new NetworkManager();
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerDays);
        mRecyclerView.setLayoutManager(mLayoutManager);


        /**
         * setup the database with static data if its null
         */

       if(databaseHandler.getAPODCount()<=0)
        setUpDatabase();

        /**
         * getting the all APODS from database as List<OneDayData>
         */
        List<OneDayData> daysList = databaseHandler.getAllAPODS();

        /**
         * checking the today's date in the from above fetched List<>
         */
        boolean isTodayThere = false;
        for(int i=0;i<daysList.size();i++)
        {
            OneDayData day = daysList.get(i);
            if(today_date.equals(day.getDate()))
                isTodayThere = true;
        }

        /**
         * if today's is not present then get the today's APOD from internet.
         */

        if(isTodayThere == false)
            fetchTodayPictureData();

        Log.e("today date",today_date);
        //Log.e("two","excuted");
        //List<OneDayData> daysList1 = databaseHandler.getAllAPODS();

        /**
         * creating the adapter and setting the adapter to recyclerview.
         */

        mRecyclerAdapter = new RecyclerDayAdapter(getApplicationContext(), databaseHandler);
        //Log.e("five","excuted");
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    private void fetchTodayPictureData() {


        final Call<OneDayData> todayData = mnetworkManager.getmNasaservices().getTodaysPictureDate();

        todayData.enqueue(new Callback<OneDayData>() {
            @Override
            public void onResponse(Call<OneDayData> call, Response<OneDayData> response) {
                if(response.isSuccessful()){
                    Log.e("onResponse"," "+response.body().toString());

                    OneDayData dayData = response.body();

                    /**
                     * checking if the today date is present the fetched APOD
                     *
                     * if
                     *      present - write the APOD directly into the database
                     * else
                     *      change the date in fetched APOD and than write to database.
                     */
                    if(today_date.equals(dayData.getDate()))
                        databaseHandler.addAPOD(response.body());
                    else
                    {
                        dayData.setDate(today_date);
                        databaseHandler.addAPOD(dayData);
                    }
                    Log.e("one","excuted");

                    /**
                     * refreshing the List<APOD> in MASTERLIST with new data and setting to recyclerview.
                     */
                    mRecyclerAdapter.refreshRecyclerView();
                }
                else {
                    Log.e("onRespnonse","Custome Error"+response.code());
                }

            }
            @Override
            public void onFailure(Call<OneDayData> call, Throwable t) {
                if(t instanceof UnknownHostException){
                    Log.e("onFailure","No network"+t.getMessage());
                }else if(t instanceof SocketTimeoutException){
                    Log.e("onFailure","Time out"+t.getMessage());
                }
                else
                    Log.e("onFailure","error"+t.getMessage());

            }

        });
    }


    /***
     * setup the database with static data, if it is null.
     */

    private void setUpDatabase() {
        List<OneDayData> daysList = new StaticDataSetup().getData();
        for(int i=0;i<daysList.size();i++)
            databaseHandler.addAPOD(daysList.get(i));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id == R.id.menu_item_calender){
            //showDatePicker();
            startCalendarActivity();

        }
        else if(id == R.id.menu_item_custom){
            mRecyclerAdapter.showThisList("all");

            switchEmptyView();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("year",2017);
        intent.putExtra("month",0);
        intent.putExtra("day",19);
        startActivityForResult(intent,108);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //switchEmptyView();
        if(requestCode == 108)
        {
          //  switchEmptyView(false);
            String date = data.getStringExtra("date");
            Log.e("onActivityREsult",date);
            Snackbar snackbar = Snackbar.make(relativelayout, "Date.."+data.getStringExtra("date"), Snackbar.LENGTH_LONG);
            snackbar.show();
            if(!date.equals("No date selected"))
            {
                mRecyclerAdapter.showThisList(date);
            }
            switchEmptyView();
        }
    }

    /***
     * switchEmptyView - is the one
     */

    public void switchEmptyView(){
        if(mRecyclerAdapter.getItemCount()>0)
            empty_text.setVisibility(View.GONE);
        else
            empty_text.setVisibility(View.VISIBLE);
    }


    /***
     * DatePickerDialog total rescource is found at : https://github.com/wdullaer/MaterialDateTimePicker
     * this library is included in the gradle mddule.
     *
     */


    /*
    private void showDatePicker() {
      Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        int today = now.get(Calendar.DAY_OF_MONTH);

        int[] selected_days =  mRecyclerAdapter.getSelected_days_array();;

        int[] difference_array = new int[selected_days.length];
         for(int i=0;i<selected_days.length;i++){
             if(today == selected_days[i]){
                 difference_array[i] = 0;
             }
             else{
                 difference_array[i] = selected_days[i]-today;
             }
         }

        Calendar[] days = new Calendar[selected_days.length];
        for (int i = 0; i < selected_days.length; i++) {
            Calendar day = Calendar.getInstance();
            day.add(now.DAY_OF_MONTH, difference_array[i]);
            days[i] = day;
        }

        //dpd.setSelectableDays(days);
        dpd.setSelectableDays(days);
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }
*/
/*
    public void startNewFragemnt(OneDayData day){

        Bundle bundle = new Bundle();
        bundle.putParcelable("day",day);

        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(bundle);

        //getFragmentManager().beginTransaction().replace(R.layout.activity_main,dayFragment,"mrfrag").commit();

    }

  */
    /*
    // (fragment DateDialog)interface method for know the date at end of datepickerdialog.
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
      //  EditText ed = (EditText) findViewById(R.id.date_et);
        monthOfYear++;
        Log.e("date picked:",String.valueOf(dayOfMonth));

        String date = String.valueOf(year)+"-"+String.valueOf(monthOfYear)+"-"+String.valueOf(dayOfMonth);
        OneDayData day = mRecyclerAdapter.getThisDay(date);
//
        startNewFragemnt(day);
        Log.e("date",date);
         //       mRecyclerAdapter.notifyDataSetChanged();
       // ed.setText(String.valueOf(year)+" - "+String.valueOf(monthOfYear)+" - "+String.valueOf(dayOfMonth));
    }
*/


    /*
    public String readSharedPreference(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);

        if(preferences.contains("date"))
            return preferences.getString("date","null");
        else
            return "null";
    }
    public void writeSharedPreference(String today){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("date", today);
        editor.commit();
    }*/
}
