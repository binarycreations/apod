package net.binarycreations.apod.archive.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.binarycreations.apod.R;

/**
 * Shows the details of a single astronomical picture as a row.
 *
 * @author graham.
 */
public class ArchiveRowView extends RelativeLayout {

    private TextView mTitle;
    private TextView mDate;
    private ImageView mPicture;

    public ArchiveRowView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.astro_row_view, this);

        mTitle = (TextView) findViewById(R.id.tv_astro_row_view_title);
        mDate = (TextView) findViewById(R.id.tv_astro_row_view_date);
        mPicture = (ImageView) findViewById(R.id.iv_astro_row_view_picture);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setDate(String date) {
        mDate.setText(date);
    }

    public void pictureUrl(String url) {
        mPicture.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.apod_today));
    }
}
