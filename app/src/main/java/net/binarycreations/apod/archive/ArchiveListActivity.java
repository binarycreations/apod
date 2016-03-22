package net.binarycreations.apod.archive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.binarycreations.apod.R;
import net.binarycreations.apod.app.ApodApp;
import net.binarycreations.apod.archive.ui.ArchivePaginationListener;
import net.binarycreations.apod.archive.ui.AstroPictureAdapter;
import net.binarycreations.apod.detail.AstroDetailActivity;
import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.Clock;
import org.threeten.bp.LocalDate;

import java.util.List;

/**
 * Show a list of recent astronomy pictures of the day.
 *
 * @author graham.
 */
public class ArchiveListActivity extends AppCompatActivity implements ArchiveView, ArchivePaginationListener, View
        .OnClickListener {

    private static final String TAG = ArchiveListActivity.class.getSimpleName();

    private ArchivePresenter mPresenter;

    private Toolbar mToolbar;
    private RecyclerView mAstroList;
    private AstroPictureAdapter mAdapter;
    private boolean mIsLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_list);

        mToolbar = (Toolbar) findViewById(R.id.archive_toolbar);
        setSupportActionBar(mToolbar);

        mAstroList = (RecyclerView) findViewById(R.id.rv_archive_list);
        mAstroList.setHasFixedSize(true);
        mAstroList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AstroPictureAdapter();
        mAdapter.setOnClickListener(this);
        mAdapter.setPaginationListener(this);
        mAstroList.setAdapter(mAdapter);

        mPresenter = ApodApp.getInstance().getArchiveFactory().getArchivePresenter();
        mPresenter.setView(this);

        loadArchivePictures(fromLastWeek(), today());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.archive_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_refresh) {
            onRefresh();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onRefresh() {
        // If there is nothing currently being shown and nothing else is being loaded, allow the user to attempt
        // to refresh loading this weeks current pictures.
        if (mAdapter.getItemCount() == 0 && !mIsLoading) {
            loadArchivePictures(fromLastWeek(), today());
        }
    }

    private synchronized void loadArchivePictures(LocalDate from, LocalDate to) {
        if (mIsLoading) {
            Log.d(TAG, "Already loading archive pictures");
        } else {
            mIsLoading = true;
            mPresenter.loadArchivePictures(from, to);
        }
    }

    private LocalDate today() {
        return LocalDate.now(Clock.systemUTC());
    }

    private LocalDate fromLastWeek() {
        return today().minusDays(6);
    }

    @Override
    public void displayPictures(List<AstroPick> toShow) {
        mIsLoading = false;
        mAdapter.appendItems(toShow);
    }

    @Override
    public void displayNoConnectivity() {
        mIsLoading = false;
        Toast.makeText(this, getString(R.string.no_connectivity), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayArchiveUnavailable() {
        mIsLoading = false;
        Toast.makeText(this, getString(R.string.archive_unavailable), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayAstroExplanation(AstroPick item) {
        Intent explanationScreen = new Intent(this, AstroDetailActivity.class);
        explanationScreen.putExtra(AstroDetailActivity.ASTRO_PICK_EXTRA, item);
        startActivity(explanationScreen);
    }

    @Override
    public void onNextPagination(AstroPick atEnd) {
        LocalDate lastDay = atEnd.getDate();
        loadArchivePictures(previousWeek(lastDay), previousDay(lastDay));
    }

    private LocalDate previousDay(LocalDate lastDay) {
        return lastDay.minusDays(1);
    }

    private LocalDate previousWeek(LocalDate lastDay) {
        return lastDay.minusDays(7);
    }

    public void onClick(View v) {
        int position = mAstroList.getChildAdapterPosition(v);
        AstroPick item = mAdapter.getItem(position);
        mPresenter.onAstroPictureClick(item);
    }
}
