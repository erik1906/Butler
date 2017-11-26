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

        holder.title.setText(register.getTitle());
        holder.date.setText(register.getDate());
        holder.total.setText(register.getTotal());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class DeliveryHistoryHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView total;

        public DeliveryHistoryHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.history_item_title);
            date = (TextView) view.findViewById(R.id.history_item_date);
            total = (TextView) view.findViewById(R.id.history_item_total);
        }
    }

}
