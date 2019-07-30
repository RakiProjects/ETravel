package com.example.e_travel.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_travel.R;
import com.example.e_travel.model.City;

import java.util.ArrayList;
import java.util.List;


public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private ArrayList<City> cityList = new ArrayList<>();
    private Context context;
    private final OnItemClickListener listener;


    public CitiesAdapter( Context context,ArrayList<City> cityList, OnItemClickListener listener) {
        this.cityList = cityList;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cities, parent, false);
        CitiesViewHolder vh = new CitiesViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.cityName.setText(cityList.get(position).getName());

        holder.bind(cityList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public void updateCityList(List<City> list){
        cityList.clear();
        cityList.addAll(list);
        notifyDataSetChanged();
    }

    public static class CitiesViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;

        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
        }
        public void bind(final City item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(City item);
    }
}
