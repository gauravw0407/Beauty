package com.gr.beauty.View;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

/**
 * Created by gaurav on 9/3/17.
 */

public class SalonServicesRecyclerAdapter extends RecyclerView.Adapter<SalonServicesRecyclerAdapter.SalonServicesRecyclerViewHolder> {

    ArrayList<SalonServiceDetails> salonServiceDetailses;

    public SalonServicesRecyclerAdapter(ArrayList<SalonServiceDetails> salonServiceDetailses) {
        this.salonServiceDetailses = salonServiceDetailses;
    }

    @Override
    public SalonServicesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_services_recyclerview_item,parent,false);
        SalonServicesRecyclerViewHolder salonServicesRecyclerViewHolder = new SalonServicesRecyclerViewHolder(view);

        return salonServicesRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(SalonServicesRecyclerViewHolder holder, int position) {

        holder.serviceName.setText(salonServiceDetailses.get(position).getServiceName());
        holder.servicePrice.setText(salonServiceDetailses.get(position).getServicePrice());
        holder.serviceSeats.setText(salonServiceDetailses.get(position).getServiceSeats());
    }

    @Override
    public int getItemCount() {

        return salonServiceDetailses.size();
    }

    public static class SalonServicesRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView serviceName, servicePrice, serviceSeats;

        public SalonServicesRecyclerViewHolder(View view) {
            super(view);
            serviceName = (TextView) view.findViewById(R.id.serviceName);
            servicePrice = (TextView) view.findViewById(R.id.servicePrice);
            serviceSeats = (TextView) view.findViewById(R.id.serviceSeats);
        }
    }
}
