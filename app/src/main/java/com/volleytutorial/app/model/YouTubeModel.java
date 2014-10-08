package com.volleytutorial.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by R4D3K on 2014-06-01.
 */
public class YouTubeModel implements Parcelable {

    private String mTitle;
    private String mDescription;
    private String mThumbSmall;
    private String mThumbBig;

    public YouTubeModel(String title, String thumbSmall) {
        this.mTitle = title;
        this.mThumbSmall = thumbSmall;
    }

    public YouTubeModel(String title, String description, String thumbBig) {
        this.mTitle = title;
        this.mDescription = description;
        this.mThumbBig = thumbBig;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getThumbSmall() {
        return mThumbSmall;
    }

    public void setThumbSmall(String thumbSmall) {
        mThumbSmall = thumbSmall;
    }

    public String getThumbBig() {
        return mThumbBig;
    }

    public void setThumbBig(String thumbBig) {
        mThumbBig = thumbBig;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mThumbSmall);
        dest.writeString(this.mThumbBig);
    }

    private YouTubeModel(Parcel in) {
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mThumbSmall = in.readString();
        this.mThumbBig = in.readString();
    }

    public static final Parcelable.Creator<YouTubeModel> CREATOR = new Parcelable.Creator<YouTubeModel>() {
        public YouTubeModel createFromParcel(Parcel source) {
            return new YouTubeModel(source);
        }

        public YouTubeModel[] newArray(int size) {
            return new YouTubeModel[size];
        }
    };
}
