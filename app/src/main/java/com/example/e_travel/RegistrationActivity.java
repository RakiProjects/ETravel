package com.example.e_travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_travel.model.User;
import com.example.e_travel.response.RegistrationResponse;
import com.example.e_travel.viewmodel.RegistrationViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @Email
    private EditText registrationEmail;

    @NotEmpty
    @Password(min = 5, scheme = Password.Scheme.ANY)
    private EditText registrationPassword;

    @ConfirmPassword
    private EditText registrationConfirmPassword;

    @NotEmpty
    private EditText registrationName;

    private Validator validator;

    String email, password, confirmPassword, name;

    private RegistrationViewModel regViewModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegistrationActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationEmail = findViewById(R.id.registration_email);
        registrationPassword = findViewById(R.id.registration_password);
        registrationConfirmPassword = findViewById(R.id.registration_confirm_password);
        registrationName = findViewById(R.id.regstration_name);

        validator = new Validator(this);
        validator.setValidationListener(this);

        regViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        regViewModel.regLiveData.observe(this, new Observer<RegistrationResponse>() {
            @Override
            public void onChanged(RegistrationResponse registrationResponse) {
                if(registrationResponse == null) return;
                if(registrationResponse.getThrowable() != null){
                    // greska
                    Toast.makeText(RegistrationActivity.this, "No internet connection!", Toast.LENGTH_LONG).show();
                }
                if(registrationResponse.getError().equals("badEmail")){
                    Toast.makeText(RegistrationActivity.this, "This email is already taken", Toast.LENGTH_LONG).show();
                } if(registrationResponse.getError().equals("noResponse")){
                    Toast.makeText(RegistrationActivity.this, "Server is busy, please try later", Toast.LENGTH_LONG).show();
                }
                if(registrationResponse.getError().equals("")){
                    registrationResponse.setError(null);
                    LoginActivity.start(RegistrationActivity.this);
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.registration_activity), "Successfull registration!", Snackbar.LENGTH_INDEFINITE);
//                    snackbar.setAction(R.string.snackbar, new SnackbarListener());
//                    snackbar.show();
                }
            }
        });
    }

    public void register(View view) {

        email = registrationEmail.getText().toString();
        password = registrationPassword.getText().toString();
        confirmPassword = registrationConfirmPassword.getText().toString();
        name = registrationName.getText().toString();
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        //User user = new User(email, password, name);
        regViewModel.register(email, password, name);
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

    private class SnackbarListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            LoginActivity.start(RegistrationActivity.this);
        }
    }
}
