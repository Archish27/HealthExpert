package com.healthexpert.auth;

import android.util.Log;

import com.healthexpert.data.remote.api.UserRestService;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 1/13/2017.
 */

public class RegisterPresenter implements RegisterContract.RegisterPresenter {

    RegisterContract.RegisterView view;
    UserRestService userRestService;

    public RegisterPresenter(UserRestService userRestService, RegisterContract.RegisterView view) {
        this.view = view;
        this.userRestService = userRestService;
    }

    @Override
    public void registerUser(UserRegisterRequest userRegisterRequest) {
        userRestService
                .doRegister(userRegisterRequest)
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
                            view.onRegister(user);
                    }
                });


    }


}
