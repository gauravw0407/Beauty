package com.gr.beauty.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gr.beauty.Model.CustomerDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.R;

/**

 * Created by gaurav on 11/3/17.

 */

public class CustomerRegister extends AppCompatActivity {

    private DBHandler dbHandler;
    private CustomerDetails customerDetails;
    private EditText customerName;
    private EditText customerMobileNo;
    private EditText customerPassword;
    private EditText customerConfirmPassword;
    private Button customerRegisterBtn;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        initialization();
    }

    public void initialization()
    {
        dbHandler = new DBHandler(this,null,null,1);
        customerName = (EditText)findViewById(R.id.customerName);
        customerMobileNo = (EditText)findViewById(R.id.customerMobileNo);
        customerPassword = (EditText)findViewById(R.id.customerPassword);
        customerConfirmPassword = (EditText)findViewById(R.id.customerConfirmPassword);
        customerRegisterBtn = (Button)findViewById(R.id.customerRegisterBtn);
    }

    public void onCustomerRegisterBtnClick(View view)
    {
        if(!customerPassword.getText().toString().equals(customerConfirmPassword.getText().toString()))
            Toast.makeText(getApplicationContext(),"Passwords don't match!!",Toast.LENGTH_SHORT).show();
        else if(customerName.getText().toString().equals("") || customerMobileNo.getText().toString().equals("") ||
                customerPassword.getText().toString().equals("")  || customerPassword.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(),"Please provide complete details!!",Toast.LENGTH_SHORT).show();

        else {

            customerDetails = new CustomerDetails(customerName.getText().toString(), Long.parseLong(customerMobileNo.getText().toString()),
                    customerPassword.getText().toString());

            if(dbHandler.addNewCustomerRecord(customerDetails) != -1) {
                Toast.makeText(getApplicationContext(), "Registered Successfully!!", Toast.LENGTH_SHORT).show();

                dbHandler.createNewCustomerBookingTable("C_Booking_" + customerMobileNo.getText().toString());

                SharedPreferences sharedpreferences = getSharedPreferences(CustomerLogin.CustomerPreferences, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putLong(CustomerLogin.CMobileNo,Long.parseLong(customerMobileNo.getText().toString()));
                editor.putString(CustomerLogin.CPassword,customerPassword.getText().toString());
                editor.commit();

                Intent intent = new Intent(this, CustomerView.class);
                intent.putExtra("CMobileNo",Long.parseLong(customerMobileNo.getText().toString()));
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(),"Mobile No Already Registered!!",Toast.LENGTH_SHORT).show();
        }
    }
}