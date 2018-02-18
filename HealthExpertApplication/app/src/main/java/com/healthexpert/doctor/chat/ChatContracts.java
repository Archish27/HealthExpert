package com.healthexpert.doctor.chat;

import com.healthexpert.data.remote.models.requests.MessageRequest;

/**
 * Created by Archish on 2/18/2018.
 */

public interface ChatContracts {
    interface ChatPresenter {
        void sendNotification(MessageRequest messageRequest);
    }
}
