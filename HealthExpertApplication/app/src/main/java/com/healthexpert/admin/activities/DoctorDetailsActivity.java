package com.healthexpert.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.healthexpert.R;
import com.healthexpert.common.BaseActivity;
import com.healthexpert.data.remote.models.response.Doctor;
import com.healthexpert.ui.widgets.BaseButton;
import com.healthexpert.ui.widgets.BaseTextView;

/**
 * Created by Archish on 2/8/2018.
 */

public class DoctorDetailsActivity extends BaseActivity {
    BaseTextView tvName, tvEmailId, tvPhoneno, tvPincode, tvCity, tvSpeciality;
    BaseButton bAccept, bReject;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_doctor_details);
        initViews();
        Doctor doctor = getIntent().getParcelableExtra("doctor");
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
    }
}
