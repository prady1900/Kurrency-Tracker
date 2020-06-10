package com.example.kurrency;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {


    private ArrayList<String> names;
    private ArrayList<Double> rates;
    private OnCurrClickListener onCLick;

    public interface OnCurrClickListener
    {
        void onClickListener(int pos);
    }
    public void setCurrClick(OnCurrClickListener listener)
    {
        onCLick=listener;
    }

    public CurrencyAdapter(ArrayList<String> currName, ArrayList<Double> rateOfConv)
    {
        names = currName;
        rates = rateOfConv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.currency_row,parent,false);
        return new ViewHolder(view,onCLick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        double rate = rates.get(position);
        String name = names.get(position);
        holder.curr_Name.setText(name);
        holder.rate.setText(String.valueOf(new DecimalFormat("#.####").format(rate)));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView curr_Name;
        TextView rate;
        ConstraintLayout constraintLayout;
        private Context context;


        public ViewHolder(@NonNull View itemView,OnCurrClickListener onClick) {
            super(itemView);
            curr_Name = itemView.findViewById(R.id.curr_name);
            rate = itemView.findViewById(R.id.rate);
            itemView.setOnClickListener(v ->
            {
                if (onClick !=null)
                {
                    int pos = getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION)
                    {
                        onClick.onClickListener(pos);
                    }
                }
            });

        }


    }


}



