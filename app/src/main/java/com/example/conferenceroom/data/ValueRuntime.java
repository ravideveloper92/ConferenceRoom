package com.example.conferenceroom.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ValueRuntime implements Parcelable {

    @SerializedName("value")
    private int value;

    public ValueRuntime(int runtime)
    {
        this.value = runtime;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value);
    }

    protected ValueRuntime(Parcel in) {
        this.value = in.readInt();
    }

    public static final Creator<ValueRuntime> CREATOR = new Creator<ValueRuntime>() {
        @Override
        public ValueRuntime createFromParcel(Parcel source) {
            return new ValueRuntime(source);
        }

        @Override
        public ValueRuntime[] newArray(int size) {
            return new ValueRuntime[size];
        }
    };
}
