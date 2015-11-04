package net.binarycreations.apod.domain;

import java.util.Date;

/**
 * Represents a daily astronomy picture with associated explanation.
 *
 * @author graham.
 */
public class AstroItem {

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
}
