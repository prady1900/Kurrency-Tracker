package com.example.kurrency;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    RecyclerView recyclerView;
    Home_Frag_Adapter home_frag_adapter;


    public Home_Frag()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_currency_list, container, false);

        recyclerView = view.findViewById(R.id.home_frag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getdata1();
        loaddata();
        Log.i("view12"," "+Global.names+" "+Global.values+" "+Global.prevvalues);

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();

        getdata1();
        Log.i("Onstart"," "+Global.names+" "+Global.values+" "+Global.prevvalues);
        loaddata();




    }

    public void getdata1()
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

                    Log.i("message1","i am called");
                    Log.i("USD",""+Global.map.get("USD"));
                    if (Global.prevvalues.size()>0)
                    {Global.prevvalues.clear();}
                    for(int i=0;i<Global.names.size();i++)
                    {
                        String name = Global.names.get(i);
                        //Log.i("curr_name",""+name);
                        double value = Global.map.get(name);
                        //Log.i("prev_val",""+value);
                        Global.prevvalues.add(value);
                    }
                    Log.i("getdata", " " + Global.names + " " + Global.values + " " + Global.prevvalues);
                    home_frag_adapter = new Home_Frag_Adapter(Global.names, Global.values,Global.prevvalues);
                    recyclerView.setAdapter(home_frag_adapter);

                }
                @Override
                public void onFailure(Call<CurrencyList> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error getting JSON", Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void loaddata()
        {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared_value",Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String ret_names = sharedPreferences.getString("saved_names", null);
            String ret_values = sharedPreferences.getString("saved_values",null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            Global.names = gson.fromJson(ret_names,type);
            Global.values= gson.fromJson(ret_values,type);
        }

}
