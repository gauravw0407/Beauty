package com.gr.beauty.View;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

public class SalonEditServices extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor;

    private TextView salonEditServicesShopName;
    private FloatingActionButton tickFloatingActionButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SalonServiceDetails salonServiceDetails;
    private ArrayList<SalonServiceDetails> salonServiceDetailses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_edit_services_main);

        initialization();
    }

    public void initialization(){

        dbHandler = new DBHandler(this,null,null,1);

        salonEditServicesShopName = (TextView) findViewById(R.id.salonEditServicesShopName);
        salonEditServicesShopName.setText(dbHandler.getSalonShopName(getIntent().getLongExtra("MobileNo",0)));

        tickFloatingActionButton = (FloatingActionButton) findViewById(R.id.tickFloatingActionButton);
        recyclerView = (RecyclerView) findViewById(R.id.salonEditServicesRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        cursor = null;
        salonServiceDetailses = new ArrayList<SalonServiceDetails>();
        cursor = dbHandler.getAllServices(getIntent().getStringExtra("TableName"));
        while (cursor.moveToNext()){
            salonServiceDetails = new SalonServiceDetails(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            salonServiceDetailses.add(salonServiceDetails);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new SalonEditServicesRecyclerAdapter(this,getIntent().getStringExtra("TableName"),getIntent().getLongExtra("MobileNo",0)
                ,salonServiceDetailses);
        recyclerView.setAdapter(adapter);
    }

    public void onTickFloatingActionBtnClick(View view){

        Intent intent = new Intent(this,SalonServices.class);
        intent.putExtra("TableName",getIntent().getStringExtra("TableName"));
        intent.putExtra("MobileNo",getIntent().getLongExtra("MobileNo",0));
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
