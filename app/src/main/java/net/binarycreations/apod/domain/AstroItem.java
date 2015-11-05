package net.binarycreations.apod.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Represents a daily astronomy picture with associated explanation.
 *
 * @author graham.
 */
public class AstroItem implements Parcelable {

    public static final Parcelable.Creator<AstroItem> CREATOR = new Parcelable.Creator<AstroItem>() {

        public AstroItem createFromParcel(Parcel in) {
            return new AstroItem(in);
        }

        public AstroItem[] newArray(int size) {
            return new AstroItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mExplanation);
        dest.writeString(mUrl);
        dest.writeString(mType.toString());
        dest.writeLong(mDate.getTime());
    }

    public enum MediaType { IMAGE, VIDEO }

    private String mTitle;
    private String mExplanation;
    private String mUrl;
    private MediaType mType;
    private Date mDate;

    /**
     * Constructor.
     *
     * @param title for the item.
     * @param explanation related to the item.
     * @param url to the astronomical picture/video.
     * @param type the media type at the given url.
     * @param date the item occurred.
     */
    public AstroItem(String title, String explanation, String url, MediaType type, Date date) {
        mTitle = title;
        mExplanation = explanation;
        mUrl = url;
        mType = type;
        mDate = date;
    }

    /**
     * Construct an Astro Item from a parcel.
     *
     * @param in containing required data.
     */
    public AstroItem(Parcel in) {
        mTitle = in.readString();
        mExplanation = in.readString();
        mUrl = in.readString();
        mType = MediaType.valueOf(in.readString());
        mDate = new Date(in.readLong());
    }

    public String getTitle() {
        return mTitle;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public MediaType getType() {
        return mType;
    }

    public String getUrl() {
        return mUrl;
    }

    public Date getDate() {
        return mDate;
    }

    @Override
    public String toString() {
        return "AstroItem{" +
                "mTitle='" + mTitle + '\'' +
                ", mDate=" + mDate +
                '}';
    }
}
