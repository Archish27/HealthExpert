package com.healthexpert.auth;


import com.healthexpert.common.BaseContract;
import com.healthexpert.data.remote.models.requests.UserRegisterRequest;
import com.healthexpert.data.remote.models.response.UserRegisterResponse;
import com.healthexpert.data.remote.models.response.UserResponse;

/**
 * Created by Archish on 1/13/2017.
 */

public interface RegisterContract {
    interface RegisterView extends BaseContract.BaseView {
        void onRegister(UserRegisterResponse userResponse);

    }

    interface RegisterPresenter {
        void registerUser(UserRegisterRequest userRegisterRequest);

    }
}
