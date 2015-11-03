package net.binarycreations.apod.archive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.binarycreations.apod.R;
import net.binarycreations.apod.archive.ui.AstroPictureAdapter;
import net.binarycreations.apod.domain.AstroItem;

import java.util.List;

/**
 * Show a list of recent astronomy pictures of the day.
 *
 * @author graham.
 */
public class ArchiveListActivity extends AppCompatActivity implements ArchiveView {

    private ArchivePresenter mPresenter;

    private RecyclerView mAstroList;
    private AstroPictureAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_list);

        mAstroList = (RecyclerView) findViewById(R.id.rv_astro_list);
        mAstroList.setHasFixedSize(true);
        mAstroList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AstroPictureAdapter();
        mAstroList.setAdapter(mAdapter);

        mPresenter.setView(this);
    }

    @Override
    public void displayPictures(List<AstroItem> toShow) {
        mAdapter.setItems(toShow);
    }
}
