package com.healthexpert.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.healthexpert.MainActivity;
import com.healthexpert.R;
import com.healthexpert.common.BaseActivity;
import com.healthexpert.data.local.SharedPreferenceManager;
import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;
import com.healthexpert.dispatcher.RetrofitObj;
import com.healthexpert.ui.widgets.BaseButton;
import com.healthexpert.ui.widgets.BaseEditText;

import java.util.ArrayList;


/**
 * Created by Archish on 1/13/2017.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.RegisterView {
    BaseEditText etFullName, etEmailId, etPhoneNo, etPassword, etCPassword, etSpeciality, etCity, etPincode;
    BaseButton bRegister;
    RegisterPresenter presenter;
    LinearLayout workerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (new SharedPreferenceManager(getApplicationContext()).getMainPage() != 0) {
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        initViews();
        UserRestService userRestService = RetrofitObj.getInstance().create(UserRestService.class);
        presenter = new RegisterPresenter(userRestService, this);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status = validate();
                if (status) {
                    showProgressDialog();
                    presenter.registerUser(new UserRegisterRequest(etFullName.getText().toString(), etEmailId.getText().toString(),
                            etSpeciality.getText().toString(), etCity.getText().toString(), etPincode.getText().toString()
                            , etPhoneNo.getText().toString(), etPassword.getText().toString()));
                }


            }
        });

    }

    private void initViews() {
        etFullName = (BaseEditText) findViewById(R.id.etFullName);
        etPhoneNo = (BaseEditText) findViewById(R.id.etPhoneno);
        etEmailId = (BaseEditText) findViewById(R.id.etEmailId);
        etCity = (BaseEditText) findViewById(R.id.etCity);
        etPincode = (BaseEditText) findViewById(R.id.etPincode);
        etSpeciality = (BaseEditText) findViewById(R.id.etSpeciality);
        etPassword = (BaseEditText) findViewById(R.id.etPassword);
        etCPassword = (BaseEditText) findViewById(R.id.etCPassword);
        bRegister = (BaseButton) findViewById(R.id.bRegister);

    }

    private boolean validate() {
        if (etFullName.getText().toString().isEmpty()) {
            etFullName.setError("First name cannot be empty");
            etFullName.setFocusable(true);
            return false;
        } else if (etCity.getText().toString().isEmpty()) {
            etCity.setError("City cannot be empty");
            etCity.setFocusable(true);
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmailId.getText().toString()).matches()) {
            etEmailId.setError("Invalid Email id");
            etEmailId.setFocusable(true);

            return false;
        } else if (etPhoneNo.getText().toString().isEmpty() || etPhoneNo
                .getText().length() < 10) {
            etPhoneNo.setError("Mobile number should be of 10 digits");
            etPhoneNo.setFocusable(true);

            return false;
        } else if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6) {
            etPassword.setError("Password length should be more than 6 characters");
            etPassword.setFocusable(true);
            return false;
        } else if (!etPassword.getText().toString().equals(etCPassword.getText().toString())) {
            etCPassword.setError("Password doesn't match");
            etCPassword.setFocusable(true);
            return false;
        }

        return true;
    }


    @Override
    public void onRegister(UserRegisterResponse userResponse) {
        dismissProgressDialog();
        boolean status = userResponse.isStatus();
        if (status) {
            Toast.makeText(RegisterActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
        Log.d("ServerException", String.valueOf(e));
    }

}
