package com.gr.beauty.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gr.beauty.Model.SalonBookingDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

/**
 * Created by gaurav on 13/3/17.
 */

public class SalonBookingsRecyclerAdapter extends RecyclerView.Adapter<SalonBookingsRecyclerAdapter.SalonBookingsRecyclerViewHolder> {

    ArrayList<SalonBookingDetails> salonBookingDetailses;

    public SalonBookingsRecyclerAdapter(ArrayList<SalonBookingDetails> salonBookingDetailses) {
        this.salonBookingDetailses = salonBookingDetailses;
    }

    @Override
    public SalonBookingsRecyclerAdapter.SalonBookingsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_bookings_recyclerview_item,parent,false);
        SalonBookingsRecyclerAdapter.SalonBookingsRecyclerViewHolder salonBookingsRecyclerViewHolder = new SalonBookingsRecyclerAdapter.SalonBookingsRecyclerViewHolder(view);

        return salonBookingsRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(SalonBookingsRecyclerAdapter.SalonBookingsRecyclerViewHolder holder, int position) {

        holder.salonBookingCustomername.setText(salonBookingDetailses.get(position).getCustomerName());
        holder.salonBookingServiceName.setText(salonBookingDetailses.get(position).getServiceName());
        holder.salonBookingDate.setText(salonBookingDetailses.get(position).getDate());
        holder.salonBookingMobileNo.setText(""+salonBookingDetailses.get(position).getMobileNo());
    }

    @Override
    public int getItemCount() {

        return salonBookingDetailses.size();
    }

    public static class SalonBookingsRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView salonBookingCustomername, salonBookingServiceName, salonBookingDate, salonBookingMobileNo;

        public SalonBookingsRecyclerViewHolder(View view) {
            super(view);
            salonBookingCustomername = (TextView) view.findViewById(R.id.salonBookingCustomername);
            salonBookingServiceName = (TextView) view.findViewById(R.id.salonBookingServiceName);
            salonBookingDate = (TextView) view.findViewById(R.id.salonBookingDate);
            salonBookingMobileNo = (TextView) view.findViewById(R.id.salonBookingMobileNo);
        }
    }
}
