package com.gr.beauty.View;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gr.beauty.Model.CustomerBookingDetails;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

/**
 * Created by gaurav on 13/3/17.
 */

public class CustomerBookingsRecyclerAdapter extends RecyclerView.Adapter<CustomerBookingsRecyclerAdapter.CustomerBookingsRecyclerViewHolder> {

    ArrayList<CustomerBookingDetails> customerBookingDetailses;

    public CustomerBookingsRecyclerAdapter(ArrayList<CustomerBookingDetails> customerBookingDetailses) {
        this.customerBookingDetailses = customerBookingDetailses;
    }

    @Override
    public CustomerBookingsRecyclerAdapter.CustomerBookingsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_bookings_recyclerview_item,parent,false);
        CustomerBookingsRecyclerAdapter.CustomerBookingsRecyclerViewHolder customerBookingsRecyclerViewHolder = new CustomerBookingsRecyclerAdapter.CustomerBookingsRecyclerViewHolder(view);

        return customerBookingsRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerBookingsRecyclerAdapter.CustomerBookingsRecyclerViewHolder holder, int position) {

        holder.customerBookingShopname.setText(customerBookingDetailses.get(position).getShopname());
        holder.customerBookingServiceName.setText(customerBookingDetailses.get(position).getServiceName());
        holder.customerBookingDate.setText(customerBookingDetailses.get(position).getDate());
        holder.customerBookingMobileNo.setText(""+customerBookingDetailses.get(position).getMobileNo());
    }

    @Override
    public int getItemCount() {

        return customerBookingDetailses.size();
    }

    public static class CustomerBookingsRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView customerBookingShopname, customerBookingServiceName, customerBookingDate, customerBookingMobileNo;

        public CustomerBookingsRecyclerViewHolder(View view) {
            super(view);
            customerBookingShopname = (TextView) view.findViewById(R.id.customerBookingShopname);
            customerBookingServiceName = (TextView) view.findViewById(R.id.customerBookingServiceName);
            customerBookingDate= (TextView) view.findViewById(R.id.customerBookingDate);
            customerBookingMobileNo = (TextView) view.findViewById(R.id.customerBookingMobileNo);
        }
    }
}
