package com.gr.beauty.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonDetails;
import com.gr.beauty.R;

public class SalonRegister extends AppCompatActivity {

    private DBHandler dbHandler;
    private SalonDetails salonDetails;

    private EditText salonShopName;
    private EditText salonMobileNo;
    private EditText salonLocality;
    private EditText salonArea;
    private EditText salonCity;
    private EditText salonState;
    private EditText salonPassword;
    private EditText salonConfirmPassword;
    private Button salonRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_register);

        initialization();
    }

    public void initialization(){

        dbHandler = new DBHandler(this,null,null,1);

        salonShopName = (EditText)findViewById(R.id.salonShopname);
        salonMobileNo = (EditText)findViewById(R.id.salonMobileNo);
        salonLocality = (EditText)findViewById(R.id.salonLocality);
        salonArea = (EditText)findViewById(R.id.salonArea);
        salonCity = (EditText)findViewById(R.id.salonCity);
        salonState = (EditText)findViewById(R.id.salonState);
        salonPassword = (EditText)findViewById(R.id.salonPassword);
        salonConfirmPassword = (EditText)findViewById(R.id.salonConfirmPassword);
        salonRegisterBtn = (Button)findViewById(R.id.salonRegisterBtn);
    }

    public void onSalonRegisterBtnClick(View view){

        if(!salonPassword.getText().toString().equals(salonConfirmPassword.getText().toString()))
            Toast.makeText(getApplicationContext(),"Passwords Don't Match!!",Toast.LENGTH_SHORT).show();

        else if(salonShopName.getText().toString().equals("") || salonMobileNo.getText().toString().equals("") ||
                salonLocality.getText().toString().equals("")  || salonArea.getText().toString().equals("") || salonCity.getText().toString().equals("") ||
                salonState.getText().toString().equals("") || salonPassword.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(),"Please provide complete details!!",Toast.LENGTH_SHORT).show();
        
        else {

            salonDetails = new SalonDetails(salonShopName.getText().toString(), Long.parseLong(salonMobileNo.getText().toString()),
                    salonLocality.getText().toString() ,salonArea.getText().toString() , salonCity.getText().toString(),
                    salonState.getText().toString(), salonPassword.getText().toString());

            if(dbHandler.addNewSalonRecord(salonDetails) != -1) {
                Toast.makeText(getApplicationContext(), "Registered Successfully!!", Toast.LENGTH_SHORT).show();

                dbHandler.createServicesTable("Services_" + salonMobileNo.getText().toString());
                dbHandler.createNewSalonBookingTable("S_Booking_" + salonMobileNo.getText().toString());

                SharedPreferences sharedpreferences = getSharedPreferences(SalonLogin.SalonPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putLong(SalonLogin.SMobileNo,Long.parseLong(salonMobileNo.getText().toString()));
                editor.putString(SalonLogin.SPassword,salonPassword.getText().toString());
                editor.commit();

                Intent intent = new Intent(this,SalonServices.class);
                intent.putExtra("MobileNo",Long.parseLong(salonMobileNo.getText().toString()));
                intent.putExtra("TableName","Services_" + salonMobileNo.getText().toString());
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(),"Mobile No Already Registered!!",Toast.LENGTH_SHORT).show();
        }
    }
}
