package com.gr.beauty.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gaurav on 7/3/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Beauty.db";
    private static final String TABLE1_NAME = "Salon";
    private static final String COLUMN_ID  = "_id";
    private static final String COLUMN_SHOPNAME = "Shop_Name";
    private static final String COLUMN_MOBILENO = "Mobile_No";
    private static final String COLUMN_LOCALITY = "Locality";
    private static final String COLUMN_AREA = "Area";
    private static final String COLUMN_CITY = "City";
    private static final String COLUMN_STATE = "State";
    private static final String COLUMN_PASSWORD = "Password";

    private static final String TABLE2_NAME = "Customer";
    private static final String COLUMN_NAME = "Name";

    private static final String COLUMN_SERVICE_NAME = "Service_Name";
    private static final String COLUMN_SERVICE_PRICE = "Service_Price";
    private static final String COLUMN_SERVICE_SEATS = "Service_Seats";
    private static final String COLUMN_DATE = "Date";

    private static final String TABLE3_NAME = "All_Services";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query1 = "CREATE TABLE " + TABLE1_NAME + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_SHOPNAME +
                " TEXT, " + COLUMN_MOBILENO + " INTEGER UNIQUE, " + COLUMN_LOCALITY + " TEXT, " + COLUMN_AREA + " TEXT, " + COLUMN_CITY +
                " TEXT, " + COLUMN_STATE + " TEXT, " + COLUMN_PASSWORD + " TEXT );";
        db.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE2_NAME + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME +
                " TEXT, " + COLUMN_MOBILENO + " INTEGER UNIQUE, " + COLUMN_PASSWORD + " TEXT );";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE3_NAME + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_SERVICE_NAME +
                " TEXT, " + COLUMN_MOBILENO + " INTEGER );";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME + ";");
        onCreate(db);
    }

    public long addNewSalonRecord(SalonDetails salonDetails){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SHOPNAME, salonDetails.getShopname());
        contentValues.put(COLUMN_MOBILENO, salonDetails.getMobileNo());
        contentValues.put(COLUMN_LOCALITY, salonDetails.getLocality());
        contentValues.put(COLUMN_AREA, salonDetails.getArea());
        contentValues.put(COLUMN_CITY, salonDetails.getCity());
        contentValues.put(COLUMN_STATE, salonDetails.getState());
        contentValues.put(COLUMN_PASSWORD, salonDetails.getPassword());

        return db.insert(TABLE1_NAME,null,contentValues);
    }

    public long addNewCustomerRecord(CustomerDetails customerDetails){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, customerDetails.getName());
        contentValues.put(COLUMN_MOBILENO, customerDetails.getMobileNo());
        contentValues.put(COLUMN_PASSWORD, customerDetails.getPassword());

        return db.insert(TABLE2_NAME,null,contentValues);
    }

    public Cursor searchSalonLogin(long mobileNo, String password){

        Cursor cursor =null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE " + COLUMN_MOBILENO + " = " + mobileNo + " AND " + COLUMN_PASSWORD +
                " = '" + password + "';";

        cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Cursor searchCustomerLogin(long mobileNo, String password){

        Cursor cursor =null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE2_NAME + " WHERE " + COLUMN_MOBILENO + " = " + mobileNo + " AND " + COLUMN_PASSWORD +
                " = '" + password + "';";

        cursor = db.rawQuery(query,null);
        return cursor;
    }

    public String getSalonShopName(long mobileNo){

        Cursor cursor =null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_SHOPNAME + " FROM " + TABLE1_NAME + " WHERE " + COLUMN_MOBILENO + " = " + mobileNo + ";";

        cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        return cursor.getString(0);
    }

    public String getCustomerName(long mobileNo){

        Cursor cursor =null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE2_NAME + " WHERE " + COLUMN_MOBILENO + " = " + mobileNo + ";";

        cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        return cursor.getString(0);
    }

    public void createServicesTable(String tableName){

        SQLiteDatabase db = getWritableDatabase();
        String query = "CREATE TABLE " + tableName + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SERVICE_NAME +
                " TEXT, " + COLUMN_SERVICE_PRICE + " TEXT, " + COLUMN_SERVICE_SEATS + " TEXT );";
        db.execSQL(query);
    }

    public long addNewService(String tableName, SalonServiceDetails salonServiceDetails){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERVICE_NAME,salonServiceDetails.getServiceName());
        contentValues.put(COLUMN_SERVICE_PRICE,salonServiceDetails.getServicePrice());
        contentValues.put(COLUMN_SERVICE_SEATS,salonServiceDetails.getServiceSeats());

        return (db.insert(tableName,null,contentValues));
    }

    public Cursor getAllServices(String tableName){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + tableName + ";";
        cursor = db.rawQuery(query,null);
        return cursor;
    }

    public int getServiceID(String tableName, String serviceName){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT " + COLUMN_ID + " FROM " + tableName + " WHERE " + COLUMN_SERVICE_NAME + " = '" + serviceName + "';";
        cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        Log.d("msg",query);
        return cursor.getInt(0);
    }

    public long editExistingService(String tableName, int ID, SalonServiceDetails salonServiceDetails){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERVICE_NAME,salonServiceDetails.getServiceName());
        contentValues.put(COLUMN_SERVICE_PRICE,salonServiceDetails.getServicePrice());
        contentValues.put(COLUMN_SERVICE_SEATS,salonServiceDetails.getServiceSeats());

        return db.update(tableName,contentValues, COLUMN_ID  + " = " + ID,null);
    }

    public long deleteExistingService(String tableName, String serviceName){

        SQLiteDatabase db = getWritableDatabase();
        return (db.delete(tableName,COLUMN_SERVICE_NAME + " = '" + serviceName + "'",null));
    }

    public Cursor getCustomerViewData(){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_SHOPNAME + ", " + COLUMN_AREA + ", " + COLUMN_CITY + ", " + COLUMN_MOBILENO + " FROM " + TABLE1_NAME + ";";
        return db.rawQuery(query,null);
    }

    public Cursor getShopDetails(long mobileNo){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT " + COLUMN_SHOPNAME + ", " + COLUMN_LOCALITY + ", " + COLUMN_AREA + ", " + COLUMN_CITY + ", " + COLUMN_STATE +
                " FROM " + TABLE1_NAME + " WHERE " + COLUMN_MOBILENO + " = " + mobileNo + ";";
        return db.rawQuery(query,null);
    }

    public void createNewCustomerBookingTable(String tableName){

        SQLiteDatabase db = getWritableDatabase();
        String query = "CREATE TABLE " + tableName + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SHOPNAME +
                " TEXT, " + COLUMN_MOBILENO + " INTEGER, " + COLUMN_SERVICE_NAME + " TEXT, " + COLUMN_DATE + " TEXT, UNIQUE(" +
                COLUMN_MOBILENO + "," + COLUMN_SERVICE_NAME + "," + COLUMN_DATE + "));";
        db.execSQL(query);
    }

    public long addNewCustomerBooking(String tableName, CustomerBookingDetails customerBookingDetails){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SHOPNAME,customerBookingDetails.getShopname());
        contentValues.put(COLUMN_MOBILENO,customerBookingDetails.getMobileNo());
        contentValues.put(COLUMN_SERVICE_NAME,customerBookingDetails.getServiceName());
        contentValues.put(COLUMN_DATE,customerBookingDetails.getDate());
        return db.insert(tableName,null,contentValues);
    }

    public void createNewSalonBookingTable(String tableName){

        SQLiteDatabase db = getWritableDatabase();
        String query = "CREATE TABLE " + tableName + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                " TEXT, " + COLUMN_MOBILENO + " INTEGER, " + COLUMN_SERVICE_NAME + " TEXT, " + COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    public long addNewSalonBooking(String tableName, SalonBookingDetails salonBookingDetails){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,salonBookingDetails.getCustomerName());
        contentValues.put(COLUMN_MOBILENO,salonBookingDetails.getMobileNo());
        contentValues.put(COLUMN_SERVICE_NAME,salonBookingDetails.getServiceName());
        contentValues.put(COLUMN_DATE,salonBookingDetails.getDate());
        return db.insert(tableName,null,contentValues);
    }

    public int getMaxSeats(String tableName, String serviceName){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT " + COLUMN_SERVICE_SEATS + " FROM " + tableName + " WHERE " + COLUMN_SERVICE_NAME + " = '" + serviceName + "';";
        cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(0));
    }

    public int getSeatsBooked(String tableName, String serviceName, String date){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT COUNT(" + COLUMN_NAME + ") FROM " + tableName + " WHERE " + COLUMN_SERVICE_NAME + " = '" +
                serviceName + "' AND " + COLUMN_DATE + " = '" + date + "';";

        cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Cursor getCustomerBookings(String tableName){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " ORDER BY " + COLUMN_DATE + ";";
        return db.rawQuery(query,null);
    }

    public Cursor getSalonBookings(String tableName){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " ORDER BY " + COLUMN_DATE + ";";
        return db.rawQuery(query,null);
    }

    public Cursor searchByArea(String areaName){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_SHOPNAME + ", " + COLUMN_AREA + ", " + COLUMN_CITY + ", " + COLUMN_MOBILENO + " FROM "
                + TABLE1_NAME + " WHERE " + COLUMN_AREA + " LIKE '%" + areaName + "%';";
        return db.rawQuery(query,null);
    }

    public Cursor searchBySalon(String salonName){

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_SHOPNAME + ", " + COLUMN_AREA + ", " + COLUMN_CITY + ", " + COLUMN_MOBILENO + " FROM "
                + TABLE1_NAME + " WHERE " + COLUMN_SHOPNAME + " LIKE '%" + salonName + "%';";
        return db.rawQuery(query,null);
    }

    public Cursor searchByService(String serviceName) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_MOBILENO + " FROM " + TABLE3_NAME + " WHERE " + COLUMN_SERVICE_NAME + " LIKE '%" + serviceName + "%';";
        return db.rawQuery(query, null);
    }

    public long addNewServiceRecord(String serviceName, long mobileNo){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERVICE_NAME,serviceName);
        contentValues.put(COLUMN_MOBILENO,mobileNo);

        return db.insert(TABLE3_NAME,null,contentValues);
    }

    public long updateServiceRecord(String serviceName, long mobileNo){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERVICE_NAME,serviceName);

        return db.update(TABLE3_NAME,contentValues, COLUMN_MOBILENO  + " = " + mobileNo,null);
    }

    public long deleteServiceRecord(String serviceName, long mobileNo){

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TABLE3_NAME,COLUMN_SERVICE_NAME + " = '" + serviceName + "' AND " + COLUMN_MOBILENO + " = " + mobileNo,null);
    }
}
