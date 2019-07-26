package com.example.e_travel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_travel.model.User;
import com.example.e_travel.response.CommentsResponse;
import com.example.e_travel.viewmodel.CommentsViewModel;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class PanoramaCommentsActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    public static final String PREF_PLACE = "PREF_PLACE_ID";
    public static int userId = 0;
    Button btnSingIn;
    TextView txtPanoramaName;
    TextView txtPanoramaDescription;
    TextView userName;
    SharedPreferences sharedPreferences;

    CommentsViewModel commentsViewModel;

    Button fadePanorama;

    int placeId;
    String placeType;



    public static void start(Context context, int id, String name, String description, float lat, float lon, String placeType) {
        Intent starter = new Intent(context, PanoramaCommentsActivity.class);
        starter.putExtra("placeId", id);
        starter.putExtra("name", name);
        starter.putExtra("description", description);
        starter.putExtra("lat", lat);
        starter.putExtra("lon", lon);
        starter.putExtra("placeType", placeType);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_comments);

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetViewMap);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        String name = (String) getIntent().getExtras().get("name");
        String description = (String) getIntent().getExtras().get("description");

        txtPanoramaName = findViewById(R.id.panoramaName);
        txtPanoramaDescription = findViewById(R.id.panoramaDescription);

        txtPanoramaName.setText(name);
        txtPanoramaDescription.setText(description);


        userName = findViewById(R.id.user_name);
        btnSingIn = findViewById(R.id.signIn);

        // User data
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREF_USER, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("user", "");
        User user = gson.fromJson(json, User.class);
        if (user != null) {
            //Toast.makeText(this, "USER je tu: "+user.getName(), Toast.LENGTH_LONG).show();
            btnSingIn.setVisibility(View.GONE);
            userName.setText(user.getName());
            userName.setVisibility(View.VISIBLE);
            userId = user.getId();
        }

        //TODO: poziv metod za RecycleView i adapter

        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        commentsViewModel.commentsLiveData.observe(this, new Observer<CommentsResponse>() {
            @Override
            public void onChanged(CommentsResponse commentsResponse) {
                // TODO: update Recycler viewa novom listom
            }
        });

        placeId = (int) getIntent().getExtras().get("placeId");
        placeType = (String) getIntent().getExtras().get("placeType");
        commentsViewModel.getCommentList(placeId, placeType);




        fadePanorama = findViewById(R.id.fadePanorama);
        final View panorama = findViewById(R.id.streetViewMap);
        fadePanorama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(panorama.getVisibility() == View.VISIBLE){
                    panorama.setVisibility(View.GONE);
                }else{
                    panorama.setVisibility(View.VISIBLE);
                }

            }
        });



    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        float lan = (float) getIntent().getExtras().get("lat");
        float lon = (float) getIntent().getExtras().get("lon");

        streetViewPanorama.setPosition(new LatLng(lan, lon));
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                btnSingIn.setVisibility(View.GONE);
                Toast.makeText(this, "Successfully logged in", Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO: ovde metod za adapter i RV

    public void addComment(View view){
        commentsViewModel.commentsLiveData.observe(this, new Observer<CommentsResponse>() {
            @Override
            public void onChanged(CommentsResponse commentsResponse) {
                // TODO: samo updatuje RecyclerView sa listom novom i pozove notifyChange
            }
        });

        // uzmem podatke
        EditText edtComment = findViewById(R.id.edtComment);
        String comment = String.valueOf(edtComment.getText());

        if(!comment.equals("")){
        commentsViewModel.insertComment(comment,userId, placeId, placeType);
        }
    }
}
