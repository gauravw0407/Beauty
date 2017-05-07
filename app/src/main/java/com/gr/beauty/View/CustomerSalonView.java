package com.gr.beauty.View;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

public class CustomerSalonView extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor;

    private TextView displayShopname, displayLocality, displayArea, displayCity, displayState, displayMobileNo, customerSalonViewCustomerName;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SalonServiceDetails salonServiceDetails;
    private ArrayList<SalonServiceDetails> salonServiceDetailses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_salon_view);

        initialization();
    }

    public void initialization(){

        dbHandler = new DBHandler(this,null,null,1);

        customerSalonViewCustomerName = (TextView) findViewById(R.id.customerSalonViewCustomerName);
        displayShopname = (TextView) findViewById(R.id.displayShopname);
        displayLocality = (TextView) findViewById(R.id.displayLocality);
        displayArea = (TextView) findViewById(R.id.displayArea);
        displayCity = (TextView) findViewById(R.id.displayCity);
        displayState = (TextView) findViewById(R.id.displayState);
        displayMobileNo = (TextView) findViewById(R.id.displayMobileNo);

        customerSalonViewCustomerName.setText(dbHandler.getCustomerName(getIntent().getLongExtra("CMobileNo",0)));

        displayFieldsInitialization();

        recyclerView = (RecyclerView) findViewById(R.id.displayServicesRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        cursor = null;
        salonServiceDetailses = new ArrayList<SalonServiceDetails>();
        cursor = dbHandler.getAllServices("Services_" + displayMobileNo.getText().toString());
        while (cursor.moveToNext()){
            salonServiceDetails = new SalonServiceDetails(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            salonServiceDetailses.add(salonServiceDetails);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomerSalonViewRecyclerAdapter(this,"C_Booking_" + getIntent().getLongExtra("CMobileNo",0),getIntent().getLongExtra("MobileNo",0),salonServiceDetailses);
        recyclerView.setAdapter(adapter);
    }

    public void displayFieldsInitialization(){

        cursor = dbHandler.getShopDetails(getIntent().getLongExtra("MobileNo",0));
        cursor.moveToFirst();
        displayShopname.setText(cursor.getString(0));
        displayLocality.setText(cursor.getString(1));
        displayArea.setText(cursor.getString(2));
        displayCity.setText(cursor.getString(3));
        displayState.setText(cursor.getString(4));
        displayMobileNo.setText(""+getIntent().getLongExtra("MobileNo",0));
    }
}
