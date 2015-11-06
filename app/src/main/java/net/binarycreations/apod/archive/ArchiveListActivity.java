package net.binarycreations.apod.archive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.binarycreations.apod.R;
import net.binarycreations.apod.app.ApodApp;
import net.binarycreations.apod.archive.ui.ArchivePaginationListener;
import net.binarycreations.apod.archive.ui.AstroPictureAdapter;
import net.binarycreations.apod.detail.AstroDetailActivity;
import net.binarycreations.apod.domain.AstroItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Show a list of recent astronomy pictures of the day.
 *
 * @author graham.
 */
public class ArchiveListActivity extends AppCompatActivity implements ArchiveView, ArchivePaginationListener, View
        .OnClickListener {

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
        mAdapter.setHasStableIds(true);
        mAdapter.setOnClickListener(this);
        mAdapter.setPaginationListener(this);
        mAstroList.setAdapter(mAdapter);

        mPresenter = ApodApp.getInstance().getArchiveFactory().getArchivePresenter();
        mPresenter.setView(this);

        loadArchivePictures(fromLastWeek(), toToday());
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
            loadArchivePictures(fromLastWeek(), toToday());
        }
    }

    private synchronized void loadArchivePictures(Date from, Date to) {
        mIsLoading = true;
        mPresenter.loadArchivePictures(from, to);
    }

    private Date toToday() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
    }

    private Date fromLastWeek() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        return calendar.getTime();
    }

    @Override
    public void displayPictures(List<AstroItem> toShow) {
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
    public void displayAstroExplanation(AstroItem item) {
        Intent explanationScreen = new Intent(this, AstroDetailActivity.class);
        explanationScreen.putExtra(AstroDetailActivity.ASTRO_PICK_EXTRA, item);
        startActivity(explanationScreen);
    }

    @Override
    public void onNextPagination(AstroItem atEnd) {
        Date lastDay = atEnd.getDate();
        mPresenter.loadArchivePictures(previousWeek(lastDay), previousDay(lastDay));
    }

    private Date previousDay(Date lastDay) {
        Calendar previousCalendarDay = Calendar.getInstance();
        previousCalendarDay.setTime(lastDay);
        previousCalendarDay.setTimeZone(TimeZone.getTimeZone("UTC"));
        previousCalendarDay.add(Calendar.DAY_OF_YEAR, -1);

        return previousCalendarDay.getTime();
    }

    private Date previousWeek(Date lastDay) {
        Calendar previousCalendarWeek = Calendar.getInstance();
        previousCalendarWeek.setTime(lastDay);
        previousCalendarWeek.setTimeZone(TimeZone.getTimeZone("UTC"));
        previousCalendarWeek.add(Calendar.DAY_OF_YEAR, -7);

        return previousCalendarWeek.getTime();
    }

    public void onClick(View v) {
        int position = mAstroList.getChildAdapterPosition(v);
        AstroItem item = mAdapter.getItem(position);
        mPresenter.onAstroPictureClick(item);
    }
}
