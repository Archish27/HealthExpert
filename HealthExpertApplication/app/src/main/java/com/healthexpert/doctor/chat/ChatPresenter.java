package com.healthexpert.doctor.chat;

import com.healthexpert.data.remote.api.DoctorRestService;
import com.healthexpert.data.remote.models.requests.MessageRequest;

/**
 * Created by Archish on 2/18/2018.
 */

public class ChatPresenter implements ChatContracts.ChatPresenter {
    DoctorRestService doctorRestService;

    public ChatPresenter(DoctorRestService doctorRestService) {
        this.doctorRestService = doctorRestService;
    }

    @Override
    public void sendNotification(MessageRequest messageRequest) {
        doctorRestService.sendNotification(messageRequest);
    }
}
