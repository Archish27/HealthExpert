package com.healthexpert.auth.doctor;

import android.util.Log;

import com.healthexpert.data.remote.api.DoctorRestService;
import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.DoctorRegisterRequest;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.Doctor;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/13/2017.
 */

public class RegisterPresenter implements RegisterContract.RegisterPresenter {

    RegisterContract.RegisterView view;
    DoctorRestService doctorRestService;

    public RegisterPresenter(DoctorRestService doctorRestService, RegisterContract.RegisterView view) {
        this.view = view;
        this.doctorRestService = doctorRestService;
    }

    @Override
    public void registerDoctor(DoctorRegisterRequest doctorRegisterRequestt) {
        doctorRestService
                .doDoctorRegister(doctorRegisterRequestt)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<UserRegisterResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("UserMessageComplete", "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UserRegisterResponse user) {
                        if (view != null)
                            view.onDoctorRegister(user);
                    }
                });


    }

    @Override
    public void registerPatient(UserRegisterRequest userRegisterRequest) {

    }


}
