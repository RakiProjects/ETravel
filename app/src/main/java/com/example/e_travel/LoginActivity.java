package com.example.e_travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_travel.model.User;
import com.example.e_travel.response.UserResponse;
import com.example.e_travel.viewmodel.LoginViewModel;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty
    @Email
    private EditText loginEmail;

    @NotEmpty
    @Password(min = 5, scheme = Password.Scheme.ANY)
    private EditText loginPassword;

    private Validator validator;
    private LoginViewModel loginViewModel;

    String email;
    String password;

    SharedPreferences sharedPreferences;

    public static final String PREF_USER = "PREF_USER";

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        validator = new Validator(this);
        validator.setValidationListener(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.userLiveData.observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse == null) return;
                if (userResponse.getThrowable() != null) {
                    Toast.makeText(LoginActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                }else if(userResponse.getError().equals("error")){
                    Toast.makeText(LoginActivity.this, "Bad email or password.", Toast.LENGTH_LONG).show();
                }else if(userResponse.getError().equals("serverError")){
                    Toast.makeText(LoginActivity.this, "Server problems, try again later.", Toast.LENGTH_LONG).show();
                }else if(userResponse.getError().equals("")){

                    //TODO: uspesno logovanje, cuvanje podataka od korisnika
                    sharedPreferences = getSharedPreferences(PREF_USER, MODE_PRIVATE);
                   // User user = new User();
                    User user = userResponse.getUser();


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json= gson.toJson(user);
                    editor.putString("user", json);
                    editor.apply();

                    Intent panoramaIntent = new Intent();
                    setResult(Activity.RESULT_OK, panoramaIntent);
                    finish();


                }
            }
        });
    }

    public void login(View view) {
        email = loginEmail.getText().toString();
        password = loginPassword.getText().toString();
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        loginViewModel.login(email, password);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void registration(View view) {
        RegistrationActivity.start(this);
    }

}
