package com.healthexpert.data.remote.models.requests;

/**
 * Created by Archish on 2/10/2018.
 */

public class AdminDoctorDetails {
    String accesstoken;
    int status;

    public AdminDoctorDetails(String accesstoken, int status) {
        this.accesstoken = accesstoken;
        this.status = status;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
