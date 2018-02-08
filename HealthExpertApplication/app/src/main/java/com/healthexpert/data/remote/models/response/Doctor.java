package com.healthexpert.data.remote.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 2/8/2018.
 */

public class Doctor implements Parcelable{
    String name;
    String emailid;
    String pincode;
    String phoneno;
    String city;
    String speciality;

    public Doctor(String name, String emailid, String pincode, String phoneno, String city, String speciality) {
        this.name = name;
        this.emailid = emailid;
        this.pincode = pincode;
        this.phoneno = phoneno;
        this.city = city;
        this.speciality = speciality;
    }

    protected Doctor(Parcel in) {
        name = in.readString();
        emailid = in.readString();
        pincode = in.readString();
        phoneno = in.readString();
        city = in.readString();
        speciality = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(emailid);
        dest.writeString(pincode);
        dest.writeString(phoneno);
        dest.writeString(city);
        dest.writeString(speciality);
    }
}
