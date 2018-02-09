package com.healthexpert.auth.doctor;


import com.healthexpert.common.BaseContract;
import com.healthexpert.data.remote.models.requests.DoctorRegisterRequest;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.Doctor;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;
import com.healthexpert.data.remote.models.response.UserResponse;

/**
 * Created by Archish on 1/13/2017.
 */

public interface RegisterContract {
    interface RegisterView extends BaseContract.BaseView {
        void onDoctorRegister(UserRegisterResponse userResponse);
        void onPatientRegister(UserRegisterResponse userResponse);

    }

    interface RegisterPresenter {
        void registerDoctor(DoctorRegisterRequest doctorRegisterRequest);
        void registerPatient(UserRegisterRequest userRegisterRequest);

    }
}
