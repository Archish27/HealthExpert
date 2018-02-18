package com.healthexpert.data.remote.models.requests;

/**
 * Created by Archish on 2/18/2018.
 */

public class MessageRequest {
    String source_fuid;
    String destination_fuid;

    public MessageRequest(String source_fuid, String destination_fuid) {
        this.source_fuid = source_fuid;
        this.destination_fuid = destination_fuid;
    }

    public String getSource_fuid() {
        return source_fuid;
    }

    public void setSource_fuid(String source_fuid) {
        this.source_fuid = source_fuid;
    }

    public String getDestination_fuid() {
        return destination_fuid;
    }

    public void setDestination_fuid(String destination_fuid) {
        this.destination_fuid = destination_fuid;
    }
}
