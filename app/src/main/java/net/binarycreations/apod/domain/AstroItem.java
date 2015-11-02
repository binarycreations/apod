package net.binarycreations.apod.domain;

import java.util.Date;

/**
 * Represents a daily astronomy picture with associated explanation.
 *
 * @author graham.
 */
public class AstroItem {

    private String mTitle;
    private String mExplanation;
    private String mPictureUrl;
    private Date mDate;

    /**
     * Constructor.
     *
     * @param title for the item.
     * @param explanation related to the picture.
     * @param pictureUrl to the astronomical picture.
     * @param date the item occurred.
     */
    public AstroItem(String title, String explanation, String pictureUrl, Date date) {
        mTitle = title;
        mExplanation = explanation;
        mPictureUrl = pictureUrl;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public Date getDate() {
        return mDate;
    }
}
