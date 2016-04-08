package net.binarycreations.apod.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.binarycreations.apod.R;
import net.binarycreations.apod.domain.AstroPick;

/**
 * Shows a single daily astronomical picture with an explanation.
 *
 * @author graham.
 */
public class AstroDetailActivity extends AppCompatActivity {

    public static final String ASTRO_PICK_EXTRA = "astro_pick";

    private AstroPick mItem;

    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mExplanation;
    private ImageView mVideoIcon;
    private ImageView mDetailPicture;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_detail);

        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);

        mTitle = (TextView) findViewById(R.id.tv_astro_detail_title);
        mExplanation = (TextView) findViewById(R.id.tv_astro_detail_explanation);
        mVideoIcon = (ImageView) findViewById(R.id.iv_astro_detail_video);
        mDetailPicture = (ImageView) findViewById(R.id.iv_astro_detail_pic);

        mItem = getIntent().getParcelableExtra(ASTRO_PICK_EXTRA);

        mTitle.setText(mItem.getTitle());
        mExplanation.setText(mItem.getExplanation());

        if (AstroPick.MediaType.IMAGE == mItem.getType()) {
            Picasso.with(this).load(mItem.getUrl()).into(mDetailPicture);
        } else {
            mDetailPicture.setVisibility(View.GONE);
            mVideoIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        MenuItem item = menu.findItem(R.id.mi_action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(createShareUrlIntent());

        return super.onCreateOptionsMenu(menu);
    }

    private Intent createShareUrlIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_url));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, mItem.getTitle(), mItem.getUrl()));
        return intent;
    }
}
