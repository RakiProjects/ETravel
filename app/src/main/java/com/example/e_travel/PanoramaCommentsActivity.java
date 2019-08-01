package com.example.e_travel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_travel.adapter.CommentsAdapter;
import com.example.e_travel.model.CityContent;
import com.example.e_travel.model.Comment;
import com.example.e_travel.model.User;
import com.example.e_travel.response.CommentsResponse;
import com.example.e_travel.viewmodel.CommentsViewModel;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PanoramaCommentsActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    public static final String PREF_PLACE = "PREF_PLACE_ID";
    public static int userId = 0;
    Button btnSingIn;
    TextView txtPanoramaName;
    TextView txtPanoramaDescription;
    TextView userName;
    TextView hello;
    SharedPreferences sharedPreferences;

    Button logout;

    LinearLayout layout;

    CommentsViewModel commentsViewModel;

    ImageButton fadePanorama;

    int placeId;
    String placeType;

    User user;

    CommentsAdapter commentsAdapter;
    RecyclerView recyclerView;

    CityContent cityContent;


    public static void start(Context context, CityContent item) {
        Intent starter = new Intent(context, PanoramaCommentsActivity.class);
        starter.putExtra("item" , item);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_comments);

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetViewMap);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        cityContent = (CityContent) getIntent().getSerializableExtra("item");

        txtPanoramaName = findViewById(R.id.panoramaName);
        txtPanoramaDescription = findViewById(R.id.panoramaDescription);
        hello = findViewById(R.id.hello);

        txtPanoramaName.setText(cityContent.getName());
        txtPanoramaDescription.setText(cityContent.getDescription());

        recyclerView = findViewById(R.id.comments_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);

        layout = findViewById(R.id.panorama_comments_layout);


        userName = findViewById(R.id.user_name);
        btnSingIn = findViewById(R.id.signIn);


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId = 0;
                SharedPreferences pref = getSharedPreferences(LoginActivity.PREF_USER, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("user");
                editor.apply();
                recreate();
            }
        });

        // User data
        getUserData();

        generateCommentRecyclerView();

        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        commentsViewModel.commentsLiveData.observe(this, new Observer<CommentsResponse>() {
            @Override
            public void onChanged(CommentsResponse commentsResponse) {
                if (commentsResponse == null) return;
                if (commentsResponse.getThrowable() != null) {
                    Toast.makeText(PanoramaCommentsActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                } else {
                    commentsAdapter.updateCommentList(commentsResponse.getCommentList());
                }

            }
        });

        commentsViewModel.getCommentList(cityContent.getId(), cityContent.getTargetType());

        // panorama Fade button
        fadePanorama = findViewById(R.id.fadePanorama);
        final View panorama = findViewById(R.id.streetViewMap);
        fadePanorama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (panorama.getVisibility() == View.VISIBLE) {
                    panorama.setVisibility(View.GONE);
                    fadePanorama.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
                } else {
                    panorama.setVisibility(View.VISIBLE);
                    fadePanorama.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
                }
            }
        });
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(new LatLng(cityContent.getLat(), cityContent.getLon()));
    }


    private void generateCommentRecyclerView() {

        commentsAdapter = new CommentsAdapter(PanoramaCommentsActivity.this, new ArrayList<Comment>(), userId, new CommentsAdapter.Listener() {
            @Override
            public void onDeleteClick(int commentId) {
                commentsViewModel.deleteComment(commentId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Successfully logged in", Toast.LENGTH_LONG).show();
                getUserData();

                //TODO: ??
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recreate();
                    }
                });

            }
        }
    }

    public void signIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    public void addComment(View view) {
        // uzmem podatke
        final EditText edtComment = findViewById(R.id.edtComment);
        String comment = String.valueOf(edtComment.getText()).trim();


        commentsViewModel.commentsLiveData.observe(this, new Observer<CommentsResponse>() {
            @Override
            public void onChanged(CommentsResponse commentsResponse) {
                if (commentsResponse.getThrowable() == null) {
                    commentsAdapter.updateCommentList(commentsResponse.getCommentList());

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);
                    edtComment.setText("");
                    edtComment.clearFocus();
                }else{
                    Toast.makeText(PanoramaCommentsActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!comment.equals("") && userId != 0) {
            commentsViewModel.insertComment(comment, userId, placeId, placeType);
        } else if (comment.equals("")) {
            Toast.makeText(this, "Comment field cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (userId == 0) {
            Toast.makeText(this, "Sign in to comment", Toast.LENGTH_SHORT).show();
        }
    }

    public void getUserData() {
        SharedPreferences pref = getSharedPreferences(LoginActivity.PREF_USER, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString("user", "");
        user = gson.fromJson(json, User.class);
        if (user != null) {
            hello.setVisibility(View.VISIBLE);
            btnSingIn.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            userName.setText(user.getName());
            userName.setVisibility(View.VISIBLE);
            userId = user.getId();
           //Log.v("Panorama", String.valueOf(userId));
        }
    }
}
