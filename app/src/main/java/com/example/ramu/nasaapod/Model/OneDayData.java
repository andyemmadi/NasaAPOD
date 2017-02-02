package com.example.ramu.nasaapod.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Ramu on 1/24/2017.
 */
/*
public class OneDayData{

    private int id;
    private String title;
    private String explanation;
    private  String url;
    private String hdurl;
    private String copyright;
    private String date;
    private String media_type;
    private String service_version;

    public Long getLong_date() {
        return long_date;
    }

    public void setLong_date(Long long_date) {
        this.long_date = long_date;
    }

    private Long long_date;


    public OneDayData(){

    }


    public OneDayData(int id, String title, String explanation, String url) {
        this.id = id;
        this.explanation = explanation;
        this.title = title;
        this.url = url;
    }


    public OneDayData(int id,String title, String explanation, String url, String hdurl, String copyright, String date, String media_type, String service_version) {
        this.id = id;

        this.copyright = copyright;

        this.date = date;

        this.explanation = explanation;

        this.hdurl = hdurl;

        this.media_type = media_type;

        this.service_version = service_version;

        this.title = title;

        this.url = url;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

*/
public class OneDayData implements Comparable<OneDayData>{

    private int id;
    private String title;
    private String explanation;
    private  String url;
    private String hdurl;
    private String copyright;
    private String date;
    private String media_type;
    private String service_version;


    private Long date_milliseconds;

    public OneDayData(){

    }


    public OneDayData( String title, String explanation, String url) {
        this.id = id;
        this.explanation = explanation;
        this.title = title;
        this.url = url;
    }


    public OneDayData(int id,String title, String explanation, String url, String hdurl, String copyright, String date, String media_type, String service_version) {
        this.id = id;

        this.copyright = copyright;

        this.date = date;

        this.explanation = explanation;

        this.hdurl = hdurl;

        this.media_type = media_type;

        this.service_version = service_version;

        this.title = title;

        this.url = url;

     //   this.date_milliseconds = dateInMilliSeconds(this.date);
    }



    public Long getDate_milliseconds() {
        return date_milliseconds;
    }

    public void setDate_milliseconds(Long date_milliseconds) {
        this.date_milliseconds = date_milliseconds;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(OneDayData o) {

        int compareId = o.getId();

        return compareId-this.getId();
    }


    public static Long dateInMilliSeconds(String dateInString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String dateInString = "22-01-2015 10:20:56";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

       // System.out.println(dateInString);
       // System.out.println("Date - Time in milliseconds : " + date.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //Long date_milliseconds = calendar.getTimeInMillis();
        //setDate_milliseconds(calendar.getTimeInMillis());

        return calendar.getTimeInMillis();
        //System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());
    }

    /**
     * public static Comparator<Fruit> FruitNameComparator
     = new Comparator<Fruit>() {

     public int compare(Fruit fruit1, Fruit fruit2) {

     String fruitName1 = fruit1.getFruitName().toUpperCase();
     String fruitName2 = fruit2.getFruitName().toUpperCase();

     //ascending order
     return fruitName1.compareTo(fruitName2);

     //descending order
     //return fruitName2.compareTo(fruitName1);
     }

     };
     */


    public static Comparator<OneDayData> OneDayMilliComparator =
            new Comparator<OneDayData>() {
                @Override
                public int compare(OneDayData o1, OneDayData o2) {

                    Long date1 = dateInMilliSeconds(o1.getDate());
                    Long date2 = dateInMilliSeconds(o2.getDate());

                    //date1 = o1.getDate_milliseconds();
                    //date2 = o2.getDate_milliseconds();

                    return date2.compareTo(date1);
                }
            };

    /**

    protected OneDayData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        explanation = in.readString();
        url = in.readString();
        hdurl = in.readString();
        copyright = in.readString();
        date = in.readString();
        media_type = in.readString();
        service_version = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(explanation);
        dest.writeString(url);
        dest.writeString(hdurl);
        dest.writeString(copyright);
        dest.writeString(date);
        dest.writeString(media_type);
        dest.writeString(service_version);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OneDayData> CREATOR = new Parcelable.Creator<OneDayData>() {
        @Override
        public OneDayData createFromParcel(Parcel in) {
            return new OneDayData(in);
        }

        @Override
        public OneDayData[] newArray(int size) {
            return new OneDayData[size];
        }
    };  */
}
