package net.binarycreations.apod.archive.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.binarycreations.apod.R;

/**
 * Shows the details of a single astronomical picture as a row.
 *
 * @author graham.
 */
public class ArchiveRowView extends RelativeLayout {

    private TextView mTitle;
    private ImageView mVideo;
    private ImageView mPicture;

    public ArchiveRowView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.astro_row_view, this);

        mTitle = (TextView) findViewById(R.id.tv_astro_row_view_title);
        mVideo = (ImageView) findViewById(R.id.iv_astro_row_view_video);
        mPicture = (ImageView) findViewById(R.id.iv_astro_row_view_picture);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void showPicture(String url) {
        Picasso.with(getContext()).load(url).into(mPicture);
        mPicture.setVisibility(VISIBLE);
        mVideo.setVisibility(GONE);
    }

    public void showVideo() {
        mPicture.setImageBitmap(null);
        mPicture.setVisibility(GONE);
        mVideo.setVisibility(VISIBLE);
    }
}
