package com.healthexpert.doctor.fragments;

import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.DoctorRequest;
import com.healthexpert.data.remote.models.response.DoctorWrapper;
import com.healthexpert.data.remote.models.response.PatientWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/29/2017.
 */

public class PatientPresenter implements PatientContract.PatientPresenter {

    UserRestService userRestService;
    PatientContract.PatientView patientView;

    public PatientPresenter(UserRestService userRestService, PatientContract.PatientView patientView) {
        this.userRestService = userRestService;
        this.patientView = patientView;
    }

    @Override
    public void fetchPatientData(DoctorRequest doctorRequest) {
        userRestService.getPatientData(doctorRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PatientWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (patientView != null)
                            patientView.onNetworkException(e);
                    }

                    @Override
                    public void onNext(PatientWrapper patientWrapper) {
                        if (patientView != null)
                            patientView.onPatientData(patientWrapper);
                    }
                });
    }
}
