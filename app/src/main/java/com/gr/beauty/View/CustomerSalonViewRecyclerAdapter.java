package com.gr.beauty.View;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.AlarmReceiver;
import com.gr.beauty.Model.CustomerBookingDetails;
import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonBookingDetails;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.CONTEXT_IGNORE_SECURITY;

/**
 * Created by gaurav on 12/3/17.
 */

public class CustomerSalonViewRecyclerAdapter extends RecyclerView.Adapter<CustomerSalonViewRecyclerAdapter.CustomerSalonViewRecyclerViewHolder> {

    ArrayList<SalonServiceDetails> salonServiceDetailses;
    Context context;
    String tableName;
    long mobileNo;
    DBHandler dbHandler;

    public CustomerSalonViewRecyclerAdapter(Context context,String tableName, long mobileNo, ArrayList<SalonServiceDetails> salonServiceDetailses) {
        this.salonServiceDetailses = salonServiceDetailses;
        this.context = context;
        this.tableName = tableName;
        this.mobileNo = mobileNo;
        dbHandler = new DBHandler(context,null,null,1);
    }

    @Override
    public CustomerSalonViewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_salon_view_recyclerview_item,parent,false);
        CustomerSalonViewRecyclerViewHolder customerSalonViewRecyclerViewHolder = new CustomerSalonViewRecyclerViewHolder(view);
        return customerSalonViewRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerSalonViewRecyclerViewHolder holder, int position) {

        holder.displayServiceName.setText(salonServiceDetailses.get(position).getServiceName());
        holder.displayServicePrice.setText(salonServiceDetailses.get(position).getServicePrice());
    }

    @Override
    public int getItemCount() {
        return salonServiceDetailses.size();
    }

    public class CustomerSalonViewRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView displayServiceName, displayServicePrice;
        ImageButton customerBookingBtn;

        public CustomerSalonViewRecyclerViewHolder(View view) {
            super(view);
            displayServiceName = (TextView) view.findViewById(R.id.displayServiceName);
            displayServicePrice = (TextView) view.findViewById(R.id.displayServicePrice);
            customerBookingBtn = (ImageButton) view.findViewById(R.id.customerBookingBtn);

            customerBookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,SelectDate.class);
                    intent.putExtra("MobileNo",mobileNo);
                    intent.putExtra("TableName",tableName);
                    intent.putExtra("ServiceName",salonServiceDetailses.get(getAdapterPosition()).getServiceName());
                    context.startActivity(intent);
                    }
            });
        }
    }
}
