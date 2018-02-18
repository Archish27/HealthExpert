package com.healthexpert.data.remote.api;

import com.healthexpert.data.remote.models.requests.DoctorRegisterRequest;
import com.healthexpert.data.remote.models.requests.DoctorRequest;
import com.healthexpert.data.remote.models.requests.MessageRequest;
import com.healthexpert.data.remote.models.response.DoctorResponseWrapper;
import com.healthexpert.data.remote.models.response.Patient;
import com.healthexpert.data.remote.models.response.PatientWrapper;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Archish on 2/9/2018.
 */

public interface DoctorRestService {

    @Multipart
    @POST("/auth/register/doctor")
    Observable<UserRegisterResponse> doDoctorRegister(@Part("name") RequestBody name,
                                                      @Part("emailid") RequestBody emailid,
                                                      @Part("regid") RequestBody regid,
                                                      @Part("speciality") RequestBody speciality,
                                                      @Part("city") RequestBody city,
                                                      @Part("gender") RequestBody gender,
                                                      @Part("pincode") RequestBody pincode,
                                                      @Part("experience") RequestBody experience,
                                                      @Part("phoneno") RequestBody phoneno,
                                                      @Part("password") RequestBody password,
                                                      @Part("fuid") RequestBody fuid,
                                                      @Part("image\"; filename=\"image.jpg\"") RequestBody i_name);

    @POST("/doctor/alldoctors")
    Observable<DoctorResponseWrapper> getDoctors();

    @POST("/doctor/mypatients")
    Observable<PatientWrapper> getPatients();

    @POST("/doctor/patients")
    Observable<PatientWrapper> getPatientData(@Body DoctorRequest doctorRequest);

    @POST("/messaging/notify")
    void sendNotification(@Body MessageRequest messageRequest);


}
