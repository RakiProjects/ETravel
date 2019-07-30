package com.example.e_travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.e_travel.adapter.CitiesAdapter;
import com.example.e_travel.model.City;
import com.example.e_travel.response.CitiesResponse;
import com.example.e_travel.room.ETravelRoomDatabase;
import com.example.e_travel.viewmodel.CitiesViewModel;

import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends AppCompatActivity {

    private static final String EXTRA_COUNTRY_ID = "EXTRA_COUNTRY_ID";

    CitiesViewModel citiesViewModel;

    private RecyclerView recyclerView;
    private CitiesAdapter citiesAdapter;

    ETravelRoomDatabase database;

    public static void start(Context context, int countyId, String countyName) {
        Intent starter = new Intent(context, CitiesActivity.class);
        starter.putExtra(EXTRA_COUNTRY_ID, countyId);
        starter.putExtra("countryName", countyName);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String countryName = (String) getIntent().getExtras().get("countryName");
        setTitle(countryName);
        setContentView(R.layout.activity_cities);


        recyclerView = findViewById(R.id.cities_recycler_view);

        generateCitiesRecyclerView();

        citiesViewModel = ViewModelProviders.of(this).get(CitiesViewModel.class);
        citiesViewModel.citiesLiveData.observe(this, new Observer<CitiesResponse>() {
            @Override
            public void onChanged(CitiesResponse citiesResponse) {
                if(citiesResponse == null) return;
                if(citiesResponse.getThrowable() != null){
                    if(citiesResponse.getCityList() == null){
                        Toast.makeText(CitiesActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                    }else{
                        citiesAdapter.updateCityList(citiesResponse.getCityList());
                    }
                }else{
                citiesAdapter.updateCityList(citiesResponse.getCityList());
                }
            }
        });
        int countryId = (int) getIntent().getExtras().get(EXTRA_COUNTRY_ID);
        citiesViewModel.getCityList(countryId);
    }


    private void generateCitiesRecyclerView() {
        citiesAdapter = new CitiesAdapter(this, new ArrayList<City>() , new CitiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City item) {
               CityContentActivity.start(CitiesActivity.this, item.getId(), item.getName());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(citiesAdapter);
    }
}
