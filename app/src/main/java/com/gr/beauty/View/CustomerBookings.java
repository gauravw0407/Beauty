package com.gr.beauty.View;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gr.beauty.Model.CustomerBookingDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomerBookings extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor;

    private TextView customerBookingCustomerName;

    private CustomerBookingDetails customerBookingDetails;
    private ArrayList<CustomerBookingDetails> customerBookingDetailses;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private SimpleDateFormat simpleDateFormat;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_bookings);

        initialization();
    }

    public void initialization() {

        dbHandler = new DBHandler(this, null, null, 1);

        customerBookingCustomerName = (TextView) findViewById(R.id.customerBookingCustomerName);
        customerBookingCustomerName.setText(dbHandler.getCustomerName(getIntent().getLongExtra("CMobileNo",0)));

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        calendar = Calendar.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.customerBookingsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        cursor = null;
        customerBookingDetailses = new ArrayList<CustomerBookingDetails>();
        cursor = dbHandler.getCustomerBookings("C_Booking_" + getIntent().getLongExtra("CMobileNo",0));
        while (cursor.moveToNext()) {
            try {
                if (simpleDateFormat.parse(cursor.getString(4)).after(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())))
                        || simpleDateFormat.parse(cursor.getString(4)).equals(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())))) {

                    customerBookingDetails = new CustomerBookingDetails(cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4));
                    customerBookingDetailses.add(customerBookingDetails);
                    }
                }
                catch(Exception e){
                }
            }

        adapter = new CustomerBookingsRecyclerAdapter(customerBookingDetailses);
        recyclerView.setAdapter(adapter);
    }
}