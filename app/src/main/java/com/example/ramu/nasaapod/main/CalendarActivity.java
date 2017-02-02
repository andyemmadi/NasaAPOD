 package com.example.ramu.nasaapod.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.ramu.nasaapod.R;

import java.util.Calendar;

 public class CalendarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


     CalendarView calendarView;

     TextView textView;

     //DatePicker datePicker;
     DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        int min_year= getIntent().getIntExtra("year",2017);
        int min_month = getIntent().getIntExtra("month",0);
        int min_day = getIntent().getIntExtra("day",1);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,year,month,day);


        c.set(year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        c.set(min_year,min_month,min_day);//Year,Mounth -1,Day

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("datepickerdialog","cancel clicked");
                backToPrevActivity("No date selected");
            }
        });

    }



     @Override
     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
         String t_year = String.valueOf(year);
         month++;
         String t_month = String.valueOf(month);
         String t_day = String.valueOf(dayOfMonth);


         String date_str=null;
         if(month<10) {
             date_str = t_year + "-0" + t_month + "-" + t_day;
         }
         else{
             date_str = t_year + "-" + t_month + "-" + t_day;
         }

         //yyyy-MM-dd
         //String date_str = t_year+"-"+t_month+"-"+t_day;
         // textView.setText(t_month+" - "+t_day+" - "+t_year);
        backToPrevActivity(date_str);
     }

     public void backToPrevActivity(String date_str){

         Intent intent = new Intent();
         intent.putExtra("date",date_str);
         setResult(RESULT_OK,intent);
         finish();

     }




//
//     @Override
//     public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//         String t_year = String.valueOf(year);
//         String t_month = String.valueOf(monthOfYear);
//         String t_day = String.valueOf(dayOfMonth);
//
//         String date_str = t_month+" - "+t_day+" - "+t_year;
//         // textView.setText(t_month+" - "+t_day+" - "+t_year);
//
//         Intent intent = new Intent();
//         intent.putExtra("date",date_str);
//         setResult(RESULT_OK,intent);
//         finish();
//     }

/*
     @Override
     public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

         String t_year = String.valueOf(year);
         String t_month = String.valueOf(month);
         String t_day = String.valueOf(dayOfMonth);

         String date_str = t_month+" - "+t_day+" - "+t_year;
        // textView.setText(t_month+" - "+t_day+" - "+t_year);

         Intent intent = new Intent();
         intent.putExtra("date",date_str);
         setResult(RESULT_OK,intent);
         finish();
     }*/
 }
