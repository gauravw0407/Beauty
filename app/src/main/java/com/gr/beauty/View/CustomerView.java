package com.gr.beauty.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gr.beauty.Model.CustomerBookingDetails;
import com.gr.beauty.Model.CustomerViewDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

public class CustomerView extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    CustomerViewDetails customerViewDetails;
    private ArrayList<CustomerViewDetails> customerViewDetailses;

    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private ViewGroup container;
    private TextView search, myBookings, customerLogOut, customerViewCustomerName;
    PopupWindow popupWindow;
    DisplayMetrics displayMetrics;
    int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        initialization();
    }

    public void initialization(){

        dbHandler = new DBHandler(this,null,null,1);

        customerViewCustomerName = (TextView) findViewById(R.id.customerViewCustomerName);
        setCustomerName();

        recyclerView = (RecyclerView)findViewById(R.id.customerViewRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        cursor = null;
        customerViewDetailses = new ArrayList<CustomerViewDetails>();
        cursor = dbHandler.getCustomerViewData();
        while (cursor.moveToNext()){
            customerViewDetails = new CustomerViewDetails(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));
            customerViewDetailses.add(customerViewDetails);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomerViewRecyclerAdapter(getIntent().getLongExtra("CMobileNo",0),customerViewDetailses);
        recyclerView.setAdapter(adapter);

        linearLayout = (LinearLayout)findViewById(R.id.activity_customer_view);
        layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        container = (ViewGroup)layoutInflater.inflate(R.layout.customer_popup_window,null);
        search = (TextView) container.findViewById(R.id.search);
        myBookings = (TextView) container.findViewById(R.id.myBookings);
        customerLogOut = (TextView) container.findViewById(R.id.customerLogOut);
    }

    public void setCustomerName(){
        customerViewCustomerName.setText("Welcome " + dbHandler.getCustomerName(getIntent().getLongExtra("CMobileNo",0)));
    }

    public void onCustomerThreeDotsBtnClick(View view){

        showCustomerPopupWindow();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                Intent intent = new Intent(getApplicationContext(), CustomerSalonSearch.class);
                intent.putExtra("CMobileNo",getIntent().getLongExtra("CMobileNo",0));
                startActivity(intent);
            }
        });

        myBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                Intent intent = new Intent(getApplicationContext(), CustomerBookings.class);
                intent.putExtra("CMobileNo",getIntent().getLongExtra("CMobileNo",0));
                startActivity(intent);
            }
        });

        customerLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();

                SharedPreferences sharedpreferences = getSharedPreferences(CustomerLogin.CustomerPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                sharedpreferences = getSharedPreferences(ChooseCategory.CategoryPreferences,Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(getApplicationContext(),ChooseCategory.class));
                finish();
            }
        });
    }

    public void showCustomerPopupWindow(){

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        popupWindow = new PopupWindow(container,(int)(width*0.43),(int)(height*0.19),true);
        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,(int)(width*0.5),(int)(height*0.22));

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}