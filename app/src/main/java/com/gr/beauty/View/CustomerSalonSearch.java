package com.gr.beauty.View;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.CustomerViewDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

public class CustomerSalonSearch extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor, cursor1;

    private TextView customerSalonSearchCustomerName, searchByService, searchByArea, searchBySalon;
    private EditText searchField;
    private ImageButton searchBtn;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CustomerViewDetails customerViewDetails;
    private ArrayList<CustomerViewDetails> customerViewDetailses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_salon_search);

        initialization();
    }

    public void initialization(){

        dbHandler = new DBHandler(this,null,null,1);
        cursor = null;
        cursor1 = null;

        customerSalonSearchCustomerName = (TextView) findViewById(R.id.customerSalonSearchCustomerName);
        searchByService = (TextView) findViewById(R.id.searchByService);
        searchByArea = (TextView) findViewById(R.id.searchByArea);
        searchBySalon = (TextView) findViewById(R.id.searchBySalon);
        searchField = (EditText) findViewById(R.id.searchField);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);

        recyclerView = (RecyclerView) findViewById(R.id.customerSalonSearchRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        customerSalonSearchCustomerName.setText(dbHandler.getCustomerName(getIntent().getLongExtra("CMobileNo",0)));

        searchByService.setSelected(true);
    }

    public void onSearchByClick(View view){

        if(view.getId() == R.id.searchByService){

            searchByService.setSelected(true);
            searchByService.setBackground(getResources().getDrawable(R.drawable.search_by_layout));
            searchByService.setTextColor(Color.WHITE);
            searchByArea.setBackgroundColor(Color.TRANSPARENT);
            searchByArea.setTextColor(Color.BLACK);
            searchByArea.setSelected(false);
            searchBySalon.setBackgroundColor(Color.TRANSPARENT);
            searchBySalon.setTextColor(Color.BLACK);
            searchBySalon.setSelected(false);
        }
        else if(view.getId() == R.id.searchByArea){

            searchByArea.setSelected(true);
            searchByArea.setBackground(getResources().getDrawable(R.drawable.search_by_layout));
            searchByArea.setTextColor(Color.WHITE);
            searchByService.setBackgroundColor(Color.TRANSPARENT);
            searchByService.setTextColor(Color.BLACK);
            searchByService.setSelected(false);
            searchBySalon.setBackgroundColor(Color.TRANSPARENT);
            searchBySalon.setTextColor(Color.BLACK);
            searchBySalon.setSelected(false);
        }
        else if(view.getId() == R.id.searchBySalon){

            searchBySalon.setSelected(true);
            searchBySalon.setBackground(getResources().getDrawable(R.drawable.search_by_layout));
            searchBySalon.setTextColor(Color.WHITE);
            searchByArea.setBackgroundColor(Color.TRANSPARENT);
            searchByArea.setTextColor(Color.BLACK);
            searchByArea.setSelected(false);
            searchByService.setBackgroundColor(Color.TRANSPARENT);
            searchByService.setTextColor(Color.BLACK);
            searchByService.setSelected(false);
        }
    }

    public void onSearchBtnClick(View view){

        customerViewDetailses = new ArrayList<CustomerViewDetails>();

        if(searchByService.isSelected()){

            cursor = dbHandler.searchByService(searchField.getText().toString());

            while (cursor.moveToNext()){
                cursor1 = dbHandler.getShopDetails(cursor.getLong(0));
                cursor1.moveToFirst();
                customerViewDetails = new CustomerViewDetails(cursor1.getString(0),cursor1.getString(2),cursor1.getString(3),cursor.getLong(0));
                customerViewDetailses.add(customerViewDetails);
            }
        }

        else if(searchByArea.isSelected()){

            cursor = dbHandler.searchByArea(searchField.getText().toString());

            while (cursor.moveToNext()){
                customerViewDetails = new CustomerViewDetails(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));
                customerViewDetailses.add(customerViewDetails);
            }
        }

        else if(searchBySalon.isSelected()){

            cursor = dbHandler.searchBySalon(searchField.getText().toString());

            while (cursor.moveToNext()){
                customerViewDetails = new CustomerViewDetails(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));
                customerViewDetailses.add(customerViewDetails);
            }
        }

        if(customerViewDetailses.size() == 0)

            Toast.makeText(getApplicationContext(),"No Results Found!!",Toast.LENGTH_SHORT).show();

            adapter = new CustomerSalonSearchRecyclerAdapter(getIntent().getLongExtra("CMobileNo",0),customerViewDetailses);
            recyclerView.setAdapter(adapter);
    }
}
