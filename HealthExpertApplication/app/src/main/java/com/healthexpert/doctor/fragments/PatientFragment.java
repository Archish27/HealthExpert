package com.healthexpert.doctor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.healthexpert.R;
import com.healthexpert.common.BaseFragment;
import com.healthexpert.data.local.SharedPreferenceManager;
import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.DoctorRequest;
import com.healthexpert.data.remote.models.response.Patient;
import com.healthexpert.data.remote.models.response.PatientWrapper;
import com.healthexpert.dispatcher.RetrofitObj;
import com.healthexpert.doctor.activities.PatientSymptomActivity;
import com.healthexpert.doctor.adapters.PatientAdapter;

import java.util.ArrayList;

/**
 * Created by Archish on 1/28/2017.
 */

public class PatientFragment extends BaseFragment implements PatientContract.PatientView, PatientAdapter.LikeItemUpdateListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvHome;
    PatientPresenter patientPresenter;
    ProgressBar pgProgress;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = (RecyclerView) view.findViewById(R.id.rvHome);
        pgProgress = (ProgressBar) view.findViewById(R.id.pgProgress);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlHome);
        rvHome.setHasFixedSize(true);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        UserRestService userRestService = RetrofitObj.getInstance().create(UserRestService.class);
        patientPresenter = new PatientPresenter(userRestService, this);
        patientPresenter.fetchPatientData(new DoctorRequest(new SharedPreferenceManager(getActivity().getApplicationContext()).getAccessToken()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                patientPresenter.fetchPatientData(new DoctorRequest(new SharedPreferenceManager(getActivity().getApplicationContext()).getAccessToken()));
            }
        });
        return view;
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
                    patientWrapper.data.get(i).getEmailid(),
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
                    patientWrapper.data.get(i).getPhoto());
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
        Intent i = new Intent(getActivity(), PatientSymptomActivity.class);
        i.putExtra("patient", patient);
        startActivity(i);
    }
}