package com.gr.beauty.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gr.beauty.Model.CustomerViewDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

/**
 * Created by gaurav on 14/3/17.
 */

public class CustomerSalonSearchRecyclerAdapter extends RecyclerView.Adapter<CustomerSalonSearchRecyclerAdapter.CustomerSalonSearchRecyclerViewHolder> {

    ArrayList<CustomerViewDetails> customerViewDetailses;
    long cMobileNo;

    public CustomerSalonSearchRecyclerAdapter(long cMobileNo, ArrayList<CustomerViewDetails> customerViewDetailses)
    {
        this.customerViewDetailses = customerViewDetailses;
        this.cMobileNo = cMobileNo;
    }

    @Override
    public CustomerSalonSearchRecyclerAdapter.CustomerSalonSearchRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_view_recyclerview_item,parent,false);
        CustomerSalonSearchRecyclerAdapter.CustomerSalonSearchRecyclerViewHolder customerSalonSearchRecyclerViewHolder = new CustomerSalonSearchRecyclerAdapter.CustomerSalonSearchRecyclerViewHolder(view);
        return customerSalonSearchRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerSalonSearchRecyclerAdapter.CustomerSalonSearchRecyclerViewHolder holder, int position) {
        holder.shopName.setText(customerViewDetailses.get(position).getShopName());
        holder.cityName.setText(customerViewDetailses.get(position).getCityName());
        holder.areaName.setText(customerViewDetailses.get(position).getAreaName());
    }

    @Override
    public int getItemCount() {
        return customerViewDetailses.size();
    }

    public class CustomerSalonSearchRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView shopName, cityName, areaName;

        public CustomerSalonSearchRecyclerViewHolder(final View view)
        {
            super(view);
            shopName=(TextView)view.findViewById(R.id.shopName);
            cityName=(TextView)view.findViewById(R.id.cityName);
            areaName=(TextView)view.findViewById(R.id.areaName);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(view.getContext(),CustomerSalonView.class);
                    intent.putExtra("MobileNo",customerViewDetailses.get(getAdapterPosition()).getMobileNo());
                    intent.putExtra("CMobileNo",cMobileNo);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
