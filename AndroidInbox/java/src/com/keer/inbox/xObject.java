package com.keer.inbox;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class xObject implements Parcelable {

    public final String title;
    public final int id;

    public xObject(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public xObject(Bundle tipBundle) {
        this(tipBundle.getString("title"), tipBundle.getInt("id"));
    }

    public xObject(Parcel in) {
        title = in.readString();
        id = in.readInt();
    }

    public Bundle toBundle() {
        Bundle tipBundle = new Bundle();
        tipBundle.putString("title", title);
        tipBundle.putInt("id", id);
        return tipBundle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public xObject createFromParcel(Parcel in) {
            return new xObject(in);
        }

        public xObject[] newArray(int size) {
            return new xObject[size];
        }
    };

}
