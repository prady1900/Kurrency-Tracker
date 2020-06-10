package com.example.kurrency;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Home_Frag_Adapter extends RecyclerView.Adapter<Home_Frag_Adapter.ViewHolder> {

    private ArrayList<String> names;
    private ArrayList<String> vals;
    private ArrayList<String> prev_values;
    public Home_Frag_Adapter(ArrayList<String> name, ArrayList<String> val,ArrayList<String> prev_value)
    {
        names=name;
        vals=val;
        prev_value=prev_values;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.currency_row_2,parent,false);
        return new Home_Frag_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String value = vals.get(position);
        String curr_name = names.get(position);
        double prev_val_var = 1.12/*Double.parseDouble(prev_values.get(position))*/;
        Log.i("BindView1"," "+curr_name+" "+value+" "+prev_val_var);
        for(int i =0;i<Global.names.size();i++)
        {

            double val = Double.parseDouble(value);

            Log.i("map123"," "+val+" "+" "+curr_name+" "+ Global.map.get(curr_name));


            if (val>prev_val_var)
            {
                holder.rate.setTextColor(Color.rgb(96, 245, 42));
            }
            else if(val<prev_val_var)
                {
                    holder.rate.setTextColor(Color.rgb(224, 43, 43));
                }
        }
        holder.curr_Name.setText(curr_name);
        holder.rate.setText(value);
    }


    @Override
    public int getItemCount() {
        return vals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView curr_Name;
        TextView rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            curr_Name = itemView.findViewById(R.id.curr_name1);
            rate = itemView.findViewById(R.id.rate2);
        }
    }
}
