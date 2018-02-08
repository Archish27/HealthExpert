package com.healthexpert.data.remote.api;

import com.healthexpert.data.remote.models.requests.DoctorRequest;
import com.healthexpert.data.remote.models.requests.UserLoginRequest;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.DoctorWrapper;
import com.healthexpert.data.remote.models.response.PatientWrapper;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;
import com.healthexpert.data.remote.models.response.UserResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Archish on 2/8/2018.
 */

public interface UserRestService {
    @POST("/auth/login")
    Observable<UserResponse> doLogin(@Body UserLoginRequest userLoginRequest);

    @POST("/auth/register")
    Observable<UserRegisterResponse> doRegister(@Body UserRegisterRequest userRegisterRequest);

    @POST("/admin/doctors")
    Observable<DoctorWrapper> getHomeData();

    @POST("/doctor/patients")
    Observable<PatientWrapper> getPatientData(@Body DoctorRequest doctorRequest);


}
