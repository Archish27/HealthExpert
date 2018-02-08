package com.healthexpert.doctor.fragments;

import com.healthexpert.common.BaseContract;
import com.healthexpert.data.remote.models.requests.DoctorRequest;
import com.healthexpert.data.remote.models.response.DoctorWrapper;
import com.healthexpert.data.remote.models.response.Patient;
import com.healthexpert.data.remote.models.response.PatientWrapper;

/**
 * Created by Archish on 1/29/2017.
 */

public interface PatientContract {
    interface PatientView extends BaseContract.BaseView{
        void onPatientData(PatientWrapper patientWrapper);
    }
    interface PatientPresenter {
        void fetchPatientData(DoctorRequest patientRequest);
    }
}
