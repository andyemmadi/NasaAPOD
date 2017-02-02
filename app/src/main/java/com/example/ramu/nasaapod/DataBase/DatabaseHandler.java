package com.example.ramu.nasaapod.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.ramu.nasaapod.Model.OneDayData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramu on 1/24/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{


        private static final int DATABASE_VERSION = 1;
        //DATA BASE NAME;
        private static final String DATABASE_NAME = "apodmanager";
        //album table name
        private static final String TABLE_ALBUM = "apod";

        //album table columns names
        private static final String KEY_ID = "id";
        private static final String KEY_TITLE = "day_title";
        private static final String KEY_EXPLANATION = "day_explanation";
        private static final String KEY_URL = "day_url";
        private static final String KEY_HDURL = "day_hdurl";
        private static final String KEY_COPYRIGHT = "day_copyright";
        private static final String KEY_DATA = "album_data";
        private static final String KEY_MEDIATYPE = "album_mediatype";
        private static final String KEY_SERVICEVERSION = "album_serviceverison";



        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_ALBUM + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    KEY_TITLE + " TEXT," +
                    KEY_EXPLANATION + " TEXT," +
                    KEY_URL + " TEXT," +
                    KEY_HDURL + " TEXT," +
                    KEY_COPYRIGHT + " TEXT," +
                    KEY_DATA + " TEXT," +
                    KEY_MEDIATYPE + " TEXT," +
                    KEY_SERVICEVERSION + " TEXT" + ")";

            sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
            //setUpDataBase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXITS"+TABLE_ALBUM);
            Log.d("onUpgrade","databse deleted");
            onCreate(sqLiteDatabase);
        }


        /***
         * all crud operations (create, read, upgrade, delete)
         */


        // adding new contact

        public void addAPOD(OneDayData oneDayData){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, oneDayData.getTitle());
            values.put(KEY_EXPLANATION, oneDayData.getExplanation());
            values.put(KEY_URL, oneDayData.getUrl());
            values.put(KEY_HDURL, oneDayData.getHdurl());
            values.put(KEY_COPYRIGHT, oneDayData.getCopyright());
            values.put(KEY_DATA, oneDayData.getDate());
            values.put(KEY_MEDIATYPE, oneDayData.getMedia_type());
            values.put(KEY_SERVICEVERSION, oneDayData.getService_version());

            //inserting a row
            db.insert(TABLE_ALBUM, null, values);
            db.close();
        }


        // getting single contact

        public OneDayData getAPOD(int id){
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_ALBUM, new String[]{ KEY_ID, KEY_TITLE, KEY_EXPLANATION, KEY_URL, KEY_HDURL, KEY_COPYRIGHT, KEY_DATA, KEY_MEDIATYPE, KEY_SERVICEVERSION},
                    KEY_ID + " =?", new String[]{ String.valueOf(id)}, null,null,null);

            if(cursor != null){
                cursor.moveToFirst();
            }

            OneDayData oneDayData = new OneDayData(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),
                cursor.getString(6),cursor.getString(7),cursor.getString(8));
            return oneDayData;
        }

        //getting list of all contacts
        public List<OneDayData> getAllAPODS(){

            List<OneDayData> dayList = new ArrayList<>();

            String selectQuery = "SELECT * FROM "+TABLE_ALBUM;

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(selectQuery,null);

            // looping through all rows and adding to list;

            if(cursor.moveToFirst()){
                do{

                    OneDayData oneDayData = new OneDayData();
                    oneDayData.setId(Integer.parseInt(cursor.getString(0)));
                    oneDayData.setTitle(cursor.getString(1));
                    oneDayData.setExplanation(cursor.getString(2));
                    oneDayData.setUrl(cursor.getString(3));
                    oneDayData.setHdurl(cursor.getString(4));
                    oneDayData.setCopyright(cursor.getString(5));
                    oneDayData.setDate(cursor.getString(6));
                    oneDayData.setMedia_type(cursor.getString(7));
                    oneDayData.setService_version(cursor.getString(8));
                    dayList.add(oneDayData);
                }while (cursor.moveToNext());
            }
//        // returning all contacts;
            return dayList;
        }

        /**
         * Updating single contact
         */
/*
        public int updateAlbum(Album album){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_SINGER, album.getSingerName());
            values.put(KEY_ALBUM, album.getSongName());
            values.put(KEY_ICON, album.getAlbumIcon());
            values.put(KEY_RATE, album.getRating());


            //updating row

            return db.update(TABLE_ALBUM, values, KEY_ID + " =?",
                    new String[]{String.valueOf(album.get_id())});

        }
*/
        /***
         * deleting single contact
         */
/*
        public void deleteAlbum(Album album){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_ALBUM, KEY_ID + " =?", new String[]{String.valueOf(album.get_id())});

            Log.d("database delete","success");
            db.close();
        }
*/
        /***
         * getting contacts count
         */

        public int getAPODCount(){
            String countQuery = "SELECT * FROM "+TABLE_ALBUM;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery,null);

            int count = cursor.getCount();
            cursor.close();

            return count;
        }

        public void deleteAllAPODS(){
            String selectQuery = "DELETE * FROM "+TABLE_ALBUM;

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(selectQuery,null);
            cursor.close();

        }

        public boolean findWithDate(String date){
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_ALBUM, new String[]{ KEY_DATA},
                    KEY_DATA + " =?", new String[]{ date }, null,null,null);

            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                if(date.equals(cursor.getString(0)))
                    return true;
                else
                    return false;
            }
            return false;

        }

}
