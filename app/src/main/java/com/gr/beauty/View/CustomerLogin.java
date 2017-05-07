package com.gr.beauty.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.R;

/**

 * Created by gaurav on 11/3/17.

 */

public class CustomerLogin extends AppCompatActivity {

    private DBHandler dbHandler;
    private EditText customerLoginMobileNo;
    private EditText customerLoginPassword;
    private Button customerLoginBtn;
    private TextView customerRegisterHere;

    public static final String CustomerPreferences = "CustomerPrefs" ;
    public static final String CMobileNo = "CMobileNo";
    public static final String CPassword = "CPassword";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        sharedPreferences = getSharedPreferences(CustomerPreferences,MODE_PRIVATE);
        if(sharedPreferences.getLong(CMobileNo,0) != 0){

            Intent intent = new Intent(this,CustomerView.class);
            intent.putExtra("CMobileNo",sharedPreferences.getLong(CMobileNo,0));
            startActivity(intent);
            finish();
        }

        initialization();

    }

    public void initialization()
    {
        dbHandler = new DBHandler(this,null,null,1);
        customerLoginMobileNo = (EditText)findViewById(R.id.customerLoginMobileNo);
        customerLoginPassword = (EditText)findViewById(R.id.customerLoginPassword);
        customerLoginBtn = (Button)findViewById(R.id.customerLoginBtn);
        customerRegisterHere = (TextView)findViewById(R.id.customerRegisterHere);
    }

    public void onCustomerLoginBtnClick(View view)
    {
        if (customerLoginMobileNo.getText().toString().equals("") || customerLoginPassword.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Please enter Mobile No / Password!!", Toast.LENGTH_SHORT).show();
        else {
            Cursor cursor = null;
            cursor = dbHandler.searchCustomerLogin(Long.parseLong(customerLoginMobileNo.getText().toString()), customerLoginPassword.getText().toString());

            if (cursor.moveToFirst()) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(CMobileNo,Long.parseLong(customerLoginMobileNo.getText().toString()));
                editor.putString(CPassword,customerLoginPassword.getText().toString());
                editor.commit();

                Intent intent = new Intent(this,CustomerView.class);
                intent.putExtra("CMobileNo",Long.parseLong(customerLoginMobileNo.getText().toString()));
                startActivity(intent);
                finish();

            } else
                Toast.makeText(getApplicationContext(), "Invalid Mobile No / Password!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCustomerRegisterHereClick(View view)
    {
        Intent intent=new Intent(this,CustomerRegister.class);
        startActivity(intent);
        finish();
    }
}