package com.example.e_travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.e_travel.adapter.MainAdapter;
import com.example.e_travel.model.Country;
import com.example.e_travel.response.MainResponse;
import com.example.e_travel.room.ETravelRoomDatabase;
import com.example.e_travel.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    ETravelRoomDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);

        generateMainRecyclerView();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.countriesLiveData.observe(this, new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                if (mainResponse == null) return;
                if (mainResponse.getThrowable() != null) {
                  if(mainResponse.getCountryList() != null){
                      mainAdapter.updateCountryList(mainResponse.getCountryList());
                    }else {
                        Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                    }
                    ETravelRoomDatabase.destroyInstance();

                } else {
                    // radi
                    mainAdapter.updateCountryList(mainResponse.getCountryList());
                }
            }
        });
        mainViewModel.getCountryList();
    }

    private void generateMainRecyclerView() {

        mainAdapter = new MainAdapter(this, new ArrayList<Country>(), new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Country item) {
                CitiesActivity.start(MainActivity.this, item.getId(), item.getName());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }
}
