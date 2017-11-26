package com.company.shidoris.butler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.shidoris.butler.R;
import com.company.shidoris.butler.model.view.DeliveryRegister;

import java.util.ArrayList;


/**
 * Created by isaac on 11/26/17.
 */

public class RecyclerViewDeliveryHistoryAdapter extends RecyclerView.Adapter<RecyclerViewDeliveryHistoryAdapter.DeliveryHistoryHolder>{

    private ArrayList<DeliveryRegister> registers;

    public RecyclerViewDeliveryHistoryAdapter(ArrayList<DeliveryRegister> registers) {
        this.registers = registers;
    }

    @Override
    public DeliveryHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_history_item, parent, false);
        return  new DeliveryHistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(DeliveryHistoryHolder holder, int position) {
        DeliveryRegister register = registers.get(position);

        holder.date.setText(register.getDate());
        holder.status.setText(register.getStatus());
        holder.total.setText(register.getTotal());
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }


    public static class DeliveryHistoryHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView status;
        private TextView total;

        public DeliveryHistoryHolder(View view) {
            super(view);

            date = (TextView) view.findViewById(R.id.history_item_dates);
            status = (TextView) view.findViewById(R.id.history_item_status);
            total = (TextView) view.findViewById(R.id.history_item_total);
        }
    }

}
