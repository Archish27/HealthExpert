package com.healthexpert.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.healthexpert.R;
import com.healthexpert.common.BaseActivity;
import com.healthexpert.dashboard.MainActivity;
import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.AdminDoctorDetails;
import com.healthexpert.data.remote.models.response.Doctor;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;
import com.healthexpert.dispatcher.RetrofitObj;
import com.healthexpert.ui.widgets.BaseButton;
import com.healthexpert.ui.widgets.BaseTextView;

/**
 * Created by Archish on 2/8/2018.
 */

public class AdminDoctorDetailsActivity extends BaseActivity implements AdminDoctorDetailsContract.AdminDoctorDetailsView {
    BaseTextView tvName, tvEmailId, tvPhoneno, tvPincode, tvCity, tvSpeciality;
    BaseButton bAccept, bReject;
    AdminDoctorDetailsPresenter adminDoctorDetailsPresenter;
    Doctor doctor;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AdminDoctorDetailsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_doctor_details);
        initViews();
        doctor = getIntent().getParcelableExtra("doctor");
        tvName.setText(doctor.getName());
        tvPhoneno.setText(doctor.getPhoneno());
        tvCity.setText(doctor.getCity());
        tvEmailId.setText(doctor.getEmailid());
        tvPincode.setText(doctor.getPincode());
        tvSpeciality.setText(doctor.getSpeciality());

    }

    private void initViews() {
        tvName = (BaseTextView) findViewById(R.id.tvFullName);
        tvEmailId = (BaseTextView) findViewById(R.id.tvEmailId);
        tvPhoneno = (BaseTextView) findViewById(R.id.tvPhoneno);
        tvPincode = (BaseTextView) findViewById(R.id.tvPincode);
        tvCity = (BaseTextView) findViewById(R.id.tvCity);
        tvSpeciality = (BaseTextView) findViewById(R.id.tvSpeciality);
        bAccept = (BaseButton) findViewById(R.id.bAccept);
        bReject = (BaseButton) findViewById(R.id.bReject);
        UserRestService userRestService = RetrofitObj.getInstance().create(UserRestService.class);
        adminDoctorDetailsPresenter = new AdminDoctorDetailsPresenter(userRestService, this);
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDoctorDetailsPresenter.statusDoctor(new AdminDoctorDetails(doctor.getAccesstoken(), 1));
            }
        });
        bReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDoctorDetailsPresenter.statusDoctor(new AdminDoctorDetails(doctor.getAccesstoken(), 99));
            }
        });
    }

    @Override
    public void onData(UserRegisterResponse userRegisterResponse) {
        Toast.makeText(getApplicationContext(), userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }


}
