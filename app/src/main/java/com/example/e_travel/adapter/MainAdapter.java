package com.example.e_travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_travel.R;
import com.example.e_travel.model.Country;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {


    private ArrayList<Country> countryList;
    private Context context;
    private final OnItemClickListener listener;

    public MainAdapter(Context context, ArrayList<Country> countryList, OnItemClickListener listener) {
        this.context = context;
        this.countryList = countryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        MainViewHolder vh = new MainViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.countryName.setText(countryList.get(position).getName());

        String imgURL = countryList.get(position).getSrc();
        Picasso.get().load(RetrofitInstance.BASE_URL + imgURL).into(holder.countryImage);

        holder.countryImage.setContentDescription(countryList.get(position).getAlt());

        holder.bind(countryList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void updateCountryList(List<Country> list) {
        countryList.clear();
        countryList.addAll(list);
        notifyDataSetChanged();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        ImageView countryImage;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name);
            countryImage = itemView.findViewById(R.id.country_image);
        }

        public void bind(final Country item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Country item);
    }
}
