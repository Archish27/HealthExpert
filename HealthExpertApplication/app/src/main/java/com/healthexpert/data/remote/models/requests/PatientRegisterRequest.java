package com.healthexpert.data.remote.models.requests;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.RequestBody;

/**
 * Created by Archish on 2/8/2018.
 */

public class PatientRegisterRequest implements Parcelable {
    private String name;
    private String dob;
    private String gender;
    private String height;
    private String weight;
    private String emailid;
    private String phoneno;
    private String occupation;
    private String bloodgroup;
    private String symptoms;
    private String history;
    private String investigations;
    private String city;
    private String pincode;
    private String mothername;
    private String mothersymptoms;
    private String fathername;
    private String fathersymptoms;
    private RequestBody image;
    private String password;

    public PatientRegisterRequest(String name, String dob, String gender, String height, String weight, String emailid, String phoneno, String occupation, String bloodgroup, String symptoms, String history, String investigations, String city, String pincode, String mothername, String mothersymptoms, String fathername, String fathersymptoms, RequestBody image, String password) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.emailid = emailid;
        this.phoneno = phoneno;
        this.occupation = occupation;
        this.bloodgroup = bloodgroup;
        this.symptoms = symptoms;
        this.history = history;
        this.investigations = investigations;
        this.city = city;
        this.pincode = pincode;
        this.mothername = mothername;
        this.mothersymptoms = mothersymptoms;
        this.fathername = fathername;
        this.fathersymptoms = fathersymptoms;
        this.image = image;
        this.password = password;
    }

    protected PatientRegisterRequest(Parcel in) {
        name = in.readString();
        dob = in.readString();
        gender = in.readString();
        height = in.readString();
        weight = in.readString();
        emailid = in.readString();
        phoneno = in.readString();
        occupation = in.readString();
        bloodgroup = in.readString();
        symptoms = in.readString();
        history = in.readString();
        investigations = in.readString();
        city = in.readString();
        pincode = in.readString();
        mothername = in.readString();
        mothersymptoms = in.readString();
        fathername = in.readString();
        fathersymptoms = in.readString();
        password = in.readString();
    }

    public static final Creator<PatientRegisterRequest> CREATOR = new Creator<PatientRegisterRequest>() {
        @Override
        public PatientRegisterRequest createFromParcel(Parcel in) {
            return new PatientRegisterRequest(in);
        }

        @Override
        public PatientRegisterRequest[] newArray(int size) {
            return new PatientRegisterRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(emailid);
        dest.writeString(phoneno);
        dest.writeString(occupation);
        dest.writeString(bloodgroup);
        dest.writeString(symptoms);
        dest.writeString(history);
        dest.writeString(investigations);
        dest.writeString(city);
        dest.writeString(pincode);
        dest.writeString(mothername);
        dest.writeString(mothersymptoms);
        dest.writeString(fathername);
        dest.writeString(fathersymptoms);
        dest.writeString(password);
    }
}
