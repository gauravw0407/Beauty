package com.gr.beauty.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonDetails;
import com.gr.beauty.R;

public class SalonLogin extends AppCompatActivity {

    private DBHandler dbHandler;

    private EditText salonLoginMobileNo;
    private EditText salonLoginPassword;
    private Button salonLoginBtn;
    private TextView salonRegisterHere;

    public static final String SalonPreferences = "SalonPrefs" ;
    public static final String SMobileNo = "SMobileNo";
    public static final String SPassword = "SPassword";

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_login);

        sharedPreferences = getSharedPreferences(SalonPreferences,MODE_PRIVATE);
        if(sharedPreferences.getLong(SMobileNo,0) != 0){

            Intent intent = new Intent(this,SalonServices.class);
            intent.putExtra("MobileNo",sharedPreferences.getLong(SMobileNo,0));
            intent.putExtra("TableName","Services_" + sharedPreferences.getLong(SMobileNo,0));
            startActivity(intent);
            finish();
        }

        initialization();
    }

    public void initialization() {

        dbHandler = new DBHandler(this, null, null, 1);

        salonLoginMobileNo = (EditText) findViewById(R.id.salonLoginMobileNo);
        salonLoginPassword = (EditText) findViewById(R.id.salonLoginPassword);
        salonLoginBtn = (Button) findViewById(R.id.salonLoginBtn);
        salonRegisterHere = (TextView) findViewById(R.id.salonRegisterHere);
    }

    public void onLoginBtnClick(View view) {

        if (salonLoginMobileNo.getText().toString().equals("") || salonLoginPassword.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Please enter Mobile No / Password!!", Toast.LENGTH_SHORT).show();
        else {
            Cursor cursor = null;
            cursor = dbHandler.searchSalonLogin(Long.parseLong(salonLoginMobileNo.getText().toString()), salonLoginPassword.getText().toString());

            if (cursor.moveToFirst()) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(SMobileNo,Long.parseLong(salonLoginMobileNo.getText().toString()));
                editor.putString(SPassword,salonLoginPassword.getText().toString());
                editor.commit();

                Intent intent = new Intent(this,SalonServices.class);
                intent.putExtra("MobileNo",Long.parseLong(salonLoginMobileNo.getText().toString()));
                intent.putExtra("TableName", "Services_" + salonLoginMobileNo.getText().toString());
                startActivity(intent);
                finish();

            } else
                Toast.makeText(getApplicationContext(), "Invalid Mobile No / Password!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSalonRegisterHereClick(View view){

        Intent intent = new Intent(this,SalonRegister.class);
        startActivity(intent);
        finish();
    }
}
