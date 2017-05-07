package com.gr.beauty.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;
import java.util.List;

public class SalonServices extends AppCompatActivity {

    private DBHandler dbHandler;
    private Cursor cursor;

    private TextView salonServicesShopname;
    private ImageButton salonThreeDotsBtn;
    private FloatingActionButton plusFloatingActionButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SalonServiceDetails salonServiceDetails;
    private ArrayList<SalonServiceDetails> salonServiceDetailses;

    private LinearLayout linearLayout;
    private LayoutInflater layoutInflater;
    private ViewGroup container;
    private TextView bookings, editServices, salonLogOut;
    PopupWindow popupWindow;
    DisplayMetrics displayMetrics;
    int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_services_main);

        initialization();
        setSalonShopname();
    }

    public void initialization(){

        dbHandler = new DBHandler(this,null,null,1);

        plusFloatingActionButton = (FloatingActionButton) findViewById(R.id.plusFloatingActionButton);
        salonServicesShopname = (TextView)findViewById(R.id.salonServicesShopName);
        salonThreeDotsBtn = (ImageButton)findViewById(R.id.salonThreeDotsBtn);

        recyclerView = (RecyclerView) findViewById(R.id.salonServicesRecyclerView);
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
        adapter = new SalonServicesRecyclerAdapter(salonServiceDetailses);
        recyclerView.setAdapter(adapter);

        linearLayout = (LinearLayout)findViewById(R.id.activity_salon_services);
        layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        container = (ViewGroup)layoutInflater.inflate(R.layout.salon_popup_window,null);
        bookings = (TextView) container.findViewById(R.id.bookings);
        editServices = (TextView) container.findViewById(R.id.editServices);
        salonLogOut = (TextView) container.findViewById(R.id.salonLogOut);
    }

    public void setSalonShopname(){

        Intent intent = getIntent();
        salonServicesShopname.setText(dbHandler.getSalonShopName(intent.getLongExtra("MobileNo",0)));
    }

    public void onPlusFloatingActionBtnClick(View view){

        showInputDialog();
    }

    public void showInputDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.salon_services_input_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(promptView);

        final EditText inputServiceName = (EditText) promptView.findViewById(R.id.inputServiceName);
        final EditText inputServicePrice = (EditText) promptView.findViewById(R.id.inputServicePrice);
        final EditText inputServiceSeats = (EditText) promptView.findViewById(R.id.inputServiceSeats);
        final Button inputServiceOkBtn = (Button) promptView.findViewById(R.id.inputServiceOkBtn);
        final Button inputServiceCancelBtn = (Button) promptView.findViewById(R.id.inputServiceCancelBtn);

        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        inputServiceOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputServiceName.getText().toString().equals("") || inputServicePrice.getText().toString().equals("") ||
                        inputServiceSeats.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid Entry!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = getIntent();
                    salonServiceDetails = new SalonServiceDetails(inputServiceName.getText().toString(), inputServicePrice.getText().toString(),
                            inputServiceSeats.getText().toString());
                    salonServiceDetailses.add(salonServiceDetails);
                    dbHandler.addNewService(intent.getStringExtra("TableName"), salonServiceDetails);
                    dbHandler.addNewServiceRecord(inputServiceName.getText().toString(),getIntent().getLongExtra("MobileNo",0));
                    alertDialog.dismiss();
                    adapter = new SalonServicesRecyclerAdapter(salonServiceDetailses);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        inputServiceCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.cancel();
            }
        });
    }

    public void onSalonThreeDotsBtnClick(View view){

        showSalonPopupWindow();

        editServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                Intent intent = new Intent(getApplicationContext(),SalonEditServices.class);
                intent.putExtra("TableName",getIntent().getStringExtra("TableName"));
                intent.putExtra("MobileNo",getIntent().getLongExtra("MobileNo",0));
                startActivity(intent);
                finish();
            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                Intent intent = new Intent(getApplicationContext(),SalonBookings.class);
                intent.putExtra("MobileNo",getIntent().getLongExtra("MobileNo",0));
                startActivity(intent);
            }
        });

        salonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();

                SharedPreferences sharedpreferences = getSharedPreferences(SalonLogin.SalonPreferences, Context.MODE_PRIVATE);
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

    public void showSalonPopupWindow(){

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

