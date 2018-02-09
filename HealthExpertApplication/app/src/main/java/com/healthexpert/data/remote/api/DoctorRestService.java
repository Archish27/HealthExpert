package com.healthexpert.data.remote.api;

import com.healthexpert.data.remote.models.requests.DoctorRegisterRequest;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Archish on 2/9/2018.
 */

public interface DoctorRestService {
    @POST("/auth/register/doctor")
    Observable<UserRegisterResponse> doDoctorRegister(@Body DoctorRegisterRequest userRegisterRequest);

}
