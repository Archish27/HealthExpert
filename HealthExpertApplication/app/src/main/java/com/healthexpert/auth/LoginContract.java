package com.healthexpert.auth;


import com.healthexpert.common.BaseContract;
import com.healthexpert.data.remote.models.requests.UserLoginRequest;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.UserResponse;

/**
 * Created by Archish on 1/13/2017.
 */

public interface LoginContract {
    interface LoginView extends BaseContract.BaseView {
        void onLogin(UserResponse userResponse);

    }

    interface LoginPresenter {
        void login(UserLoginRequest userLoginRequest);

    }
}
