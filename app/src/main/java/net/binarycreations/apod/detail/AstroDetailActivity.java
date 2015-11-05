package net.binarycreations.apod.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.binarycreations.apod.R;
import net.binarycreations.apod.domain.AstroItem;

/**
 * Shows a single daily astronomical picture with an explanation.
 *
 * @author graham.
 */
public class AstroDetailActivity extends AppCompatActivity {

    public static final String ASTRO_PICK = "astro_pick";

    private AstroItem mItem;

    private TextView mTitle;
    private TextView mExplanation;
    private ImageView mVideoIcon;
    private ImageView mDetailPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_detail);

        mTitle = (TextView) findViewById(R.id.tv_astro_detail_title);
        mExplanation = (TextView) findViewById(R.id.tv_astro_detail_explanation);
        mVideoIcon = (ImageView) findViewById(R.id.iv_astro_detail_video);
        mDetailPicture = (ImageView) findViewById(R.id.iv_astro_detail_pic);

        mItem = getIntent().getParcelableExtra(ASTRO_PICK);

        mTitle.setText(mItem.getTitle());
        mExplanation.setText(mItem.getExplanation());

        if (AstroItem.MediaType.IMAGE == mItem.getType()) {
            Picasso.with(this).load(mItem.getUrl()).into(mDetailPicture);
        } else {
            mDetailPicture.setVisibility(View.GONE);
            mVideoIcon.setVisibility(View.VISIBLE);
        }
    }
}
