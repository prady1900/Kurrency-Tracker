package com.example.kurrency;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Currency_Frag extends Fragment{

    private RecyclerView recyclerView;
    private CurrencyAdapter currencyAdapter;
    private double[] rate;
    String[] name;
    private ArrayList<String> names= new ArrayList<>();
    private ArrayList<Double> rates= new ArrayList<>();


    public Currency_Frag()
    {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_currency_, container, false);
        recyclerView = view.findViewById(R.id.currency_cards);
        getData();

        return view;
    }






    public void  getData()
    {
        Call<CurrencyList> currencyList  =CurrencyAPI.getCurrencyService().getCurrencyList("INR");
        currencyList.enqueue(new Callback<CurrencyList>() {
            @Override
            public void onResponse(Call<CurrencyList> call, Response<CurrencyList> response) {
                CurrencyList list = response.body();
                assert list != null;
                name= new String[]{
                        "CAD", "HKD", "ISK", "PHP", "DKK", "HUF", "CZK", "GBP", "RON", "SEK",
                        "IDR", "BRL", "RUB", "HRK", "JPY", "THB", "CHF", "EUR", "MYR", "BGN",
                        "TRY", "CNY", "NOK", "NZD", "ZAR", "USD", "MXN"};
                names.addAll(Arrays.asList(name));
                rate = new double[]{
                        list.getRates().getCAD(),
                        list.getRates().getHKD(),
                        list.getRates().getISK(),
                        list.getRates().getPHP(),
                        list.getRates().getDKK(),
                        list.getRates().getHUF(),
                        list.getRates().getCZK(),
                        list.getRates().getGBP(),
                        list.getRates().getRON(),
                        list.getRates().getSEK(),
                        list.getRates().getIDR(),
                        list.getRates().getBRL(),
                        list.getRates().getRUB(),
                        list.getRates().getHRK(),
                        list.getRates().getJPY(),
                        list.getRates().getTHB(),
                        list.getRates().getCHF(),
                        list.getRates().getEUR(),
                        list.getRates().getMYR(),
                        list.getRates().getBGN(),
                        list.getRates().getTRY(),
                        list.getRates().getCNY(),
                        list.getRates().getNOK(),
                        list.getRates().getNZD(),
                        list.getRates().getZAR(),
                        list.getRates().getUSD(),
                        list.getRates().getMXN(),

                };
                rates= (ArrayList<Double>) DoubleStream.of(rate).boxed().collect(Collectors.toList());
                currencyAdapter  =new CurrencyAdapter(names,rates);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(currencyAdapter);

                currencyAdapter.setCurrClick(pos -> {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(names.get(pos));
                    alertDialog.setMessage(String.valueOf(rates.get(pos)));

                    final EditText input = new EditText(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("BUY", (dialog, which) -> {
                        String units = input.getText().toString();
                        String name_of_currency = names.get(pos);
                        String rates = new DecimalFormat("#.##").format(rate[pos]);
                        Log.i("alertDialog"," "+units+
                                " "+name_of_currency+" "+
                                rates);

                        //double actual_rate = Double.parseDouble(units)/Double.parseDouble(rate);
                        Global.values.add(rates);
                        Global.names.add(name_of_currency);




                        names.remove(pos);
                        currencyAdapter.notifyDataSetChanged();

                        Toast.makeText(getActivity(), "Added to Your PortFolio",
                                Toast.LENGTH_SHORT).
                                show();

                    });


                    alertDialog.setNegativeButton("CANCEL",((dialog, which) ->
                    {

                    }));
                    alertDialog.create().show();

                });
            }
            @Override
            public void onFailure(Call<CurrencyList> call, Throwable t) {
                Toast.makeText(getActivity(),"Failed to receive Json",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
