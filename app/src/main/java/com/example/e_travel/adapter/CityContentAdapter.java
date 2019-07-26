package com.example.e_travel.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_travel.R;
import com.example.e_travel.model.CityContent;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CityContentAdapter extends RecyclerView.Adapter<CityContentAdapter.CityContentViewHolder> {


    private ArrayList<CityContent> cityContent;
    private final OnItemClickListener listener;

    private Context context;

    public CityContentAdapter(Context context,ArrayList<CityContent> cityContent, OnItemClickListener listener) {
        this.context = context;
        this.cityContent = cityContent;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_content, parent, false);
        return new CityContentViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull final CityContentViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        final ImageView imageView = cardView.findViewById(R.id.card_image_city_content);
        final ProgressBar progressBar = cardView.findViewById(R.id.card_view_progressBar);

        String imgURL = cityContent.get(position).getSrc();
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(RetrofitInstance.BASE_URL + imgURL).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onError(Exception e) {
            }
        });


        //Picasso.get().load(RetrofitInstance.BASE_URL + imgURL).into(imageView);




        TextView textView = cardView.findViewById(R.id.card_title_city_content);
        textView.setText(cityContent.get(position).getName());

        holder.bind(cityContent.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cityContent.size();
    }

    public void updateCityContent(List<CityContent> list){
        cityContent.clear();
        cityContent.addAll(list);
        notifyDataSetChanged();
    }

    public static class CityContentViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public CityContentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
        }
        public void bind(final CityContent item, final CityContentAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CityContent item);
    }
}
