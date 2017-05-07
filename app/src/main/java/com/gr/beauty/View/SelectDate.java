package com.gr.beauty.View;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.gr.beauty.AlarmReceiver;
import com.gr.beauty.Model.CustomerBookingDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonBookingDetails;
import com.gr.beauty.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelectDate extends AppCompatActivity {

    CalendarView selectDateCalendarView;
    Button confirmBookingBtn;
    String dateString,dayString,monthString;
    DBHandler dbHandler;
    CustomerBookingDetails customerBookingDetails;
    SalonBookingDetails salonBookingDetails;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        dbHandler = new DBHandler(this,null,null,1);
        selectDateCalendarView = (CalendarView) findViewById(R.id.selectDateCalendarView);
        confirmBookingBtn = (Button) findViewById(R.id.confirmBookingBtn);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");;
        dateString = simpleDateFormat.format(calendar.getTime());

        Date date = new Date();

        selectDateCalendarView.setMinDate(date.getDate());

        selectDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                if((month+1) < 10)
                    monthString = "0" + (month+1);
                else
                    monthString = ""+ (month+1);

                if(dayOfMonth <10)
                    dayString = "0" + dayOfMonth;
                else
                    dayString = "" + dayOfMonth;

                dateString = dayString + "-" + monthString  + "-" + year;

            }
        });

        confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(dbHandler.getSeatsBooked("S_Booking_" + getIntent().getLongExtra("MobileNo",0), getIntent().getStringExtra("ServiceName"), dateString) <
                        dbHandler.getMaxSeats("Services_" + getIntent().getLongExtra("MobileNo",0), getIntent().getStringExtra("ServiceName"))) {

                    Intent paytmIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                    if (paytmIntent != null) {
                        paytmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(paytmIntent);

                        customerBookingDetails = new CustomerBookingDetails(dbHandler.getSalonShopName(getIntent().getLongExtra("MobileNo",0)), getIntent().getLongExtra("MobileNo",0),
                                getIntent().getStringExtra("ServiceName"), dateString);

                        salonBookingDetails = new SalonBookingDetails(dbHandler.getCustomerName(Long.parseLong(getIntent().getStringExtra("TableName").substring(10))), Long.parseLong(getIntent().getStringExtra("TableName").substring(10)),
                                getIntent().getStringExtra("ServiceName"), dateString);

                        if(dbHandler.addNewCustomerBooking(getIntent().getStringExtra("TableName"), customerBookingDetails) != -1){

                            dbHandler.addNewSalonBooking("S_Booking_" + getIntent().getLongExtra("MobileNo",0), salonBookingDetails);

                            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            intent.putExtra("CustomerName",salonBookingDetails.getCustomerName());
                            intent.putExtra("ServiceName",salonBookingDetails.getServiceName());
                            intent.putExtra("MobileNo",getIntent().getLongExtra("MobileNo",0));
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);

                            Toast.makeText(getApplicationContext(),"Booking Confirmed!!",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"You have Already Booked this Service!!",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Please Install Paytm to Proceed to Payment!!",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"No Seat Available for " + getIntent().getStringExtra("ServiceName") + " on the Selected Date!!",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),CustomerSalonView.class);
                intent.putExtra("CMobileNo",Long.parseLong(getIntent().getStringExtra("TableName").substring(10)));
                intent.putExtra("MobileNo",getIntent().getLongExtra("MobileNo",0));
                startActivity(intent);
                finish();
            }
        });
    }
}
