package com.healthexpert.data.remote.api;

import com.healthexpert.data.remote.models.requests.DoctorRegisterRequest;
import com.healthexpert.data.remote.models.requests.PatientRegisterRequest;
import com.healthexpert.data.remote.models.requests.PatientRequest;
import com.healthexpert.data.remote.models.requests.PatientRequestNoIcon;
import com.healthexpert.data.remote.models.response.DoctorResponseWrapper;
import com.healthexpert.data.remote.models.response.DoctorWrapper;
import com.healthexpert.data.remote.models.response.Speciality;
import com.healthexpert.data.remote.models.response.SpecialityWrapper;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Archish on 2/9/2018.
 */

public interface PatientRestService {
    @POST("/auth/register/patient")
    Observable<UserRegisterResponse> doPatientRegister(@Body PatientRegisterRequest userRegisterRequest);

    @POST("/auth/register/patient/noicon")
    Observable<UserRegisterResponse> addPatientNoIcon(@Body PatientRequestNoIcon patientRequestNoIcon);

    @POST("/patient/doctors")
    Observable<DoctorResponseWrapper> getDoctors(@Body Speciality speciality);

    @POST("/doctor/speciality")
    Observable<SpecialityWrapper> specialityDoctors();


}
