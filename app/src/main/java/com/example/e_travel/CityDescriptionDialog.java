package com.example.e_travel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.e_travel.response.CityDescriptionResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.viewmodel.CityDescriptionViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CityDescriptionDialog extends DialogFragment {

    private CityDescriptionViewModel cityDescViewModel;
    ImageView imageView0, imageView1, imageView2;
    TextView cityName, cityDescription, area, population;
    ProgressBar progressBar0, progressBar1, progressBar2;

    public static CityDescriptionDialog newInstance(int cityId) {

        Bundle args = new Bundle();
        args.putInt("cityId", cityId);

        CityDescriptionDialog fragment = new CityDescriptionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        int cityId = getArguments().getInt("cityId");

        View view = inflater.inflate(R.layout.dialog_city_description, null);

        imageView0 = view.findViewById(R.id.dialog_cityImage0);
        imageView1 = view.findViewById(R.id.dialog_cityImage1);
        imageView2 = view.findViewById(R.id.dialog_cityImage2);

        progressBar0 = view.findViewById(R.id.image_desc_progressBar0);
        progressBar1 = view.findViewById(R.id.image_desc_progressBar1);
        progressBar2 = view.findViewById(R.id.image_desc_progressBar2);

        cityName = view.findViewById(R.id.dialog_city_name);
        cityDescription = view.findViewById(R.id.dialog_city_description);
        area = view.findViewById(R.id.dialog_city_area);
        population = view.findViewById(R.id.dialog_city_population);

        builder.setView((view))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CityDescriptionDialog.this.getDialog().cancel();
                    }
                });

        cityDescViewModel = ViewModelProviders.of(this).get(CityDescriptionViewModel.class);
        cityDescViewModel.cityDescLiveData.observe(this, new Observer<CityDescriptionResponse>() {
            @Override
            public void onChanged(CityDescriptionResponse cityDescriptionResponse) {
                if(cityDescriptionResponse == null) return;
                if(cityDescriptionResponse.getThrowable() != null){
                    CityDescriptionDialog.this.getDialog().cancel();
                }

                String imgURL0 = cityDescriptionResponse.getCity().get(0).getSrc();
                String imgURL1 = cityDescriptionResponse.getCity().get(1).getSrc();
                String imgURL2 = cityDescriptionResponse.getCity().get(2).getSrc();

                Picasso.get().load(RetrofitInstance.BASE_URL + imgURL0).into(imageView0, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar0.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                Picasso.get().load(RetrofitInstance.BASE_URL + imgURL1).into(imageView1, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar1.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                Picasso.get().load(RetrofitInstance.BASE_URL + imgURL2).into(imageView2, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                cityName.setText(cityDescriptionResponse.getCity().get(0).getName());

                cityDescription.setText(cityDescriptionResponse.getCity().get(0).getDescription());

                area.setText( String.valueOf(cityDescriptionResponse.getCity().get(0).getArea()));
                population.setText(String.valueOf(cityDescriptionResponse.getCity().get(0).getPopulation()));
            }
        });

        cityDescViewModel.getCityDescription(cityId);


        return builder.create();
    }


}
