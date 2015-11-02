package net.binarycreations.apod.astro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.binarycreations.apod.R;
import net.binarycreations.apod.domain.AstroItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Show a list of recent astronomy pictures of the day.
 *
 * @author graham.
 */
public class AstroPictureList extends AppCompatActivity {

    private RecyclerView mAstroList;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astro_list);

        mAstroList = (RecyclerView) findViewById(R.id.rv_astro_list);
        mAstroList.setHasFixedSize(true);
        mAstroList.setLayoutManager(new LinearLayoutManager(this));


        List<AstroItem> apods = new ArrayList<>();
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));
        apods.add(new AstroItem("first", "second", "thrid", new Date()));

        mAstroList.setAdapter(new AstroAdapter(apods));

    }
}
