package com.gr.beauty.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gr.beauty.Model.DBHandler;
import com.gr.beauty.Model.SalonServiceDetails;
import com.gr.beauty.R;

import java.util.ArrayList;

/**
 * Created by gaurav on 11/3/17.
 */

public class SalonEditServicesRecyclerAdapter extends RecyclerView.Adapter<SalonEditServicesRecyclerAdapter.SalonEditServicesRecyclerViewHolder> {

    ArrayList<SalonServiceDetails> salonServiceDetailses;
    Context context;
    String tableName;
    SalonServiceDetails salonServiceDetails;
    DBHandler dbHandler;
    long mobileNo;

    public SalonEditServicesRecyclerAdapter(Context context, String tableName, long mobileNo, ArrayList<SalonServiceDetails> salonServiceDetailses) {
        this.salonServiceDetailses = salonServiceDetailses;
        this.context = context;
        this.tableName = tableName;
        this.mobileNo = mobileNo;
    }

    @Override
    public SalonEditServicesRecyclerAdapter.SalonEditServicesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_edit_services_recyclerview_item,parent,false);
        SalonEditServicesRecyclerAdapter.SalonEditServicesRecyclerViewHolder salonEditServicesRecyclerViewHolder = new SalonEditServicesRecyclerAdapter.SalonEditServicesRecyclerViewHolder(view);


        dbHandler = new DBHandler(parent.getContext(),null,null,1);

        return salonEditServicesRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(SalonEditServicesRecyclerAdapter.SalonEditServicesRecyclerViewHolder holder, int position) {

        holder.editServiceName.setText(salonServiceDetailses.get(position).getServiceName());
        holder.editServicePrice.setText(salonServiceDetailses.get(position).getServicePrice());
        holder.editServiceSeats.setText(salonServiceDetailses.get(position).getServiceSeats());
    }

    @Override
    public int getItemCount() {

        return salonServiceDetailses.size();
    }

    public class SalonEditServicesRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView editServiceName, editServicePrice, editServiceSeats;
        ImageButton deleteServiceBtn;

        public SalonEditServicesRecyclerViewHolder(final View view) {
            super(view);
            editServiceName = (TextView) view.findViewById(R.id.editServiceName);
            editServicePrice = (TextView) view.findViewById(R.id.editServicePrice);
            editServiceSeats = (TextView) view.findViewById(R.id.editServiceSeats);
            deleteServiceBtn = (ImageButton) view.findViewById(R.id.deleteServiceBtn);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    View promptView = layoutInflater.inflate(R.layout.salon_edit_services_input_dialog,null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(promptView);

                    final EditText inputEditServiceName = (EditText) promptView.findViewById(R.id.inputEditServiceName);
                    final EditText inputEditServicePrice = (EditText) promptView.findViewById(R.id.inputEditServicePrice);
                    final EditText inputEditServiceSeats = (EditText) promptView.findViewById(R.id.inputEditServiceSeats);
                    Button inputEditServiceOkBtn = (Button) promptView.findViewById(R.id.inputEditServiceOkBtn);
                    Button inputEditServiceCancelBtn = (Button) promptView.findViewById(R.id.inputEditServiceCancelBtn);

                    inputEditServiceName.setText(salonServiceDetailses.get(getAdapterPosition()).getServiceName());
                    inputEditServicePrice.setText(salonServiceDetailses.get(getAdapterPosition()).getServicePrice());
                    inputEditServiceSeats.setText(salonServiceDetailses.get(getAdapterPosition()).getServiceSeats());

                    builder.setCancelable(false);

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    inputEditServiceOkBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(inputEditServiceName.getText().toString().equals("") || inputEditServicePrice.getText().toString().equals("") ||
                                    inputEditServiceSeats.getText().toString().equals("")) {
                                Toast.makeText(context, "Invalid Entry!!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                salonServiceDetails = new SalonServiceDetails(inputEditServiceName.getText().toString(),inputEditServicePrice.getText().toString(),
                                        inputEditServiceSeats.getText().toString());
                                dbHandler.editExistingService(tableName,dbHandler.getServiceID(tableName,salonServiceDetailses.get(getAdapterPosition()).getServiceName()),salonServiceDetails);
                                dbHandler.updateServiceRecord(inputEditServiceName.getText().toString(),mobileNo);
                                salonServiceDetailses.set(getAdapterPosition(),salonServiceDetails);
                                alertDialog.dismiss();
                                notifyDataSetChanged();
                            }
                }
            });
                    inputEditServiceCancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.cancel();
                        }
                    });

                }
            });

            deleteServiceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false)
                            .setTitle("Delete Service")
                            .setMessage("Are you sure you want to delete the Service??")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dbHandler.deleteExistingService(tableName,salonServiceDetailses.get(getAdapterPosition()).getServiceName());
                                    dbHandler.deleteServiceRecord(salonServiceDetailses.get(getAdapterPosition()).getServiceName(),mobileNo);
                                    salonServiceDetailses.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }
}
