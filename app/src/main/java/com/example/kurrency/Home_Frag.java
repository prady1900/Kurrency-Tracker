package com.example.kurrency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_Frag extends Fragment {

    //public ArrayList<String> prevnames= new ArrayList<>();
    public ArrayList<String> prevvalues = new ArrayList<>();
    RecyclerView recyclerView;
    Home_Frag_Adapter home_frag_adapter;
    TextView txt_value;


    public Home_Frag()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_currency_list, container, false);
        recyclerView = view.findViewById(R.id.home_frag);
        getdata();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("view1"," "+Global.names+" "+Global.values);

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        getdata();
        for(int i=0;i<Global.names.size();i++)
        {

            String name = Global.names.get(i);
            Log.i("currr_name",""+name);
            String value = String.valueOf(Global.map.get(name));
            Log.i("prev_val",""+value);
            prevvalues.add(value);
        }
        home_frag_adapter= new Home_Frag_Adapter(Global.names, Global.values,prevvalues);
        recyclerView.setAdapter(home_frag_adapter);

        Log.i("OnStart"," "+Global.names+" "+Global.values);


        /*for (int m=0;m<Global.values.size();m++)
        {
            if (prevvalues.get(m)<Double.parseDouble(Global.values.get(m)))
            {
                txt_value.setTextColor(0x36c20c);
            }
            else
            {
                txt_value.setTextColor(0xe01414);
            }
        }*/
    }

    public void getdata()
        {

            String pattern = "yyyy-MM-dd";
            Calendar cal  = Calendar.getInstance();
            //subtracting a day
            cal.add(Calendar.DATE, -1);
            SimpleDateFormat s = new SimpleDateFormat(pattern);
            String prevDate = s.format(new Date(cal.getTimeInMillis()));
            Log.i("date1"," "+prevDate);


            Call<CurrencyList> prevoiusList = CurrencyAPI.getCurrencyService().getPreviousDayData("INR");
            prevoiusList.enqueue(new Callback<CurrencyList>() {
                @Override
                public void onResponse(Call<CurrencyList> call, Response<CurrencyList> response) {
                    CurrencyList list= response.body();

                    assert list != null;
                    Global.map.put("CAD",list.getRates().getCAD());
                    Global.map.put("HKD",list.getRates().getHKD());
                    Global.map.put("ISK",list.getRates().getISK());
                    Global.map.put("PHP",list.getRates().getPHP());
                    Global.map.put("DKK",list.getRates().getDKK());
                    Global.map.put("HUF",list.getRates().getHUF());
                    Global.map.put("CZK",list.getRates().getCZK());
                    Global.map.put("GBP",list.getRates().getGBP());
                    Global.map.put("RON",list.getRates().getRON());
                    Global.map.put("SEK",list.getRates().getSEK());
                    Global.map.put("IDR",list.getRates().getIDR());
                    Global.map.put("BRL",list.getRates().getBRL());
                    Global.map.put("RUB",list.getRates().getRUB());
                    Global.map.put("HRK",list.getRates().getHRK());
                    Global.map.put("JPY",list.getRates().getJPY());
                    Global.map.put("THB",list.getRates().getTHB());
                    Global.map.put("CHF",list.getRates().getCHF());
                    Global.map.put("EUR",list.getRates().getEUR());
                    Global.map.put("MYR",list.getRates().getMYR());
                    Global.map.put("BGN",list.getRates().getBGN());
                    Global.map.put("TRY",list.getRates().getTRY());
                    Global.map.put("CNY",list.getRates().getCNY());
                    Global.map.put("NOK",list.getRates().getNOK());
                    Global.map.put("NZD",list.getRates().getNZD());
                    Global.map.put("ZAR",list.getRates().getZAR());
                    Global.map.put("USD",list.getRates().getUSD());
                    Global.map.put("MXN",list.getRates().getMXN());

                    Log.i("message","i am called");



                }
                @Override
                public void onFailure(Call<CurrencyList> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error getting JSON", Toast.LENGTH_SHORT).show();
                }
            });
        }
}
