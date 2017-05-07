package com.gr.beauty.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.gr.beauty.Model.CustomerBookingDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonBookingDetails;
import com.gr.beauty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SalonBookings extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor;

    private TextView salonBookingShopname;

    private SalonBookingDetails salonBookingDetails;
    private ArrayList<SalonBookingDetails> salonBookingDetailses;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private SimpleDateFormat simpleDateFormat;
    Calendar calendar;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_bookings);

        sharedPreferences = getSharedPreferences(SalonLogin.SalonPreferences,MODE_PRIVATE);
        if(sharedPreferences.getLong(SalonLogin.SMobileNo,0) != getIntent().getLongExtra("MobileNo",0)) {

            Intent intent = new Intent(this, SalonLogin.class);
            startActivity(intent);
            finish();
        }

        initialization();
    }

    public void initialization() {

        dbHandler = new DBHandler(this, null, null, 1);

        salonBookingShopname = (TextView) findViewById(R.id.salonBookingShopName);
        salonBookingShopname.setText(dbHandler.getSalonShopName(getIntent().getLongExtra("MobileNo",0)));

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        calendar = Calendar.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.salonBookingsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        cursor = null;
        salonBookingDetailses = new ArrayList<SalonBookingDetails>();
        cursor = dbHandler.getSalonBookings("S_Booking_" + getIntent().getLongExtra("MobileNo",0));
        while (cursor.moveToNext()) {
            try {
                if (simpleDateFormat.parse(cursor.getString(4)).after(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())))
                        || simpleDateFormat.parse(cursor.getString(4)).equals(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())))) {

                    salonBookingDetails = new SalonBookingDetails(cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4));
                    salonBookingDetailses.add(salonBookingDetails);
                }
            }
            catch(Exception e){
            }
        }

        adapter = new SalonBookingsRecyclerAdapter(salonBookingDetailses);
        recyclerView.setAdapter(adapter);
    }
}
