package com.healthexpert.doctor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.healthexpert.R;
import com.healthexpert.common.BaseActivity;
import com.healthexpert.common.BaseFragment;
import com.healthexpert.data.local.SharedPreferenceManager;
import com.healthexpert.data.remote.api.DoctorRestService;
import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.DoctorRequest;
import com.healthexpert.data.remote.models.response.Patient;
import com.healthexpert.data.remote.models.response.PatientWrapper;
import com.healthexpert.dispatcher.RetrofitObj;
import com.healthexpert.doctor.activities.PatientSymptomActivity;
import com.healthexpert.doctor.adapters.PatientAdapter;
import com.healthexpert.auth.patient.PatientRegisterActivity;

import java.util.ArrayList;

/**
 * Created by Archish on 1/28/2017.
 */

public class PatientActivity extends BaseActivity implements PatientContract.PatientView, PatientAdapter.LikeItemUpdateListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvHome;
    PatientPresenter patientPresenter;
    ProgressBar pgProgress;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_patient);
        rvHome = (RecyclerView) findViewById(R.id.rvHome);
        pgProgress = (ProgressBar) findViewById(R.id.pgProgress);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlHome);
        rvHome.setHasFixedSize(true);
        rvHome.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        DoctorRestService doctorRestService = RetrofitObj.getInstance().create(DoctorRestService.class);
        patientPresenter = new PatientPresenter(doctorRestService, this);
        patientPresenter.fetchPatientData(new DoctorRequest(new SharedPreferenceManager(getApplicationContext()).getAccessToken()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                patientPresenter.fetchPatientData(new DoctorRequest(new SharedPreferenceManager(getApplicationContext()).getAccessToken()));
            }
        });
    }


    @Override
    public void onPatientData(PatientWrapper patientWrapper) {
        ArrayList<Patient> patients = new ArrayList<>();
        for (int i = 0; i < patientWrapper.data.size(); i++) {
            Patient patient = new Patient(patientWrapper.data.get(i).getPid(),
                    patientWrapper.data.get(i).getName(),
                    patientWrapper.data.get(i).getDob(),
                    patientWrapper.data.get(i).getGender(),
                    patientWrapper.data.get(i).getHeight(),
                    patientWrapper.data.get(i).getWeight(),
                    patientWrapper.data.get(i).getBloodgroup(),
                    patientWrapper.data.get(i).getPhoneno(),
                    patientWrapper.data.get(i).getOccupation(),
                    patientWrapper.data.get(i).getSymptoms(),
                    patientWrapper.data.get(i).getHistory(),
                    patientWrapper.data.get(i).getInvestigations(),
                    patientWrapper.data.get(i).getCity(),
                    patientWrapper.data.get(i).getPincode(),
                    patientWrapper.data.get(i).getMothername(),
                    patientWrapper.data.get(i).getMothersymptoms(),
                    patientWrapper.data.get(i).getFathername(),
                    patientWrapper.data.get(i).getFathersymptoms(),
                    patientWrapper.data.get(i).getPhoto(),
                    patientWrapper.data.get(i).getAccesstoken());
            patients.add(patient);
        }
        PatientAdapter patientAdapter = new PatientAdapter(patients, this);
        rvHome.setAdapter(patientAdapter);
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        pgProgress.setVisibility(View.GONE);
    }


    @Override
    public void onItemCardClicked(Patient patient) {
        Intent i = new Intent(PatientActivity.this, PatientSymptomActivity.class);
        i.putExtra("patient", patient);
        startActivity(i);
    }
}