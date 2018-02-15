package com.healthexpert.patient.doctors;

import com.healthexpert.common.BaseContract;
import com.healthexpert.data.remote.models.response.DoctorResponse;
import com.healthexpert.data.remote.models.response.DoctorResponseWrapper;
import com.healthexpert.data.remote.models.response.DoctorWrapper;
import com.healthexpert.data.remote.models.response.Speciality;
import com.healthexpert.data.remote.models.response.SpecialityWrapper;

/**
 * Created by Archish on 1/29/2017.
 */

public interface DoctorContract {
    interface DoctorView extends BaseContract.BaseView{
        void onDoctorData(DoctorResponseWrapper doctorResponseWrapper);
        void onSpeciality(SpecialityWrapper specialityWrapper);
    }
    interface DoctorPresenter{
        void getDoctors(Speciality speciality);
        void specialityDoctors();
    }
}
