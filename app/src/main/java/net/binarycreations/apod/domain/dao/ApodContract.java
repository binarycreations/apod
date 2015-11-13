package net.binarycreations.apod.domain.dao;

import android.provider.BaseColumns;

/**
 * Definition of Astro Pick SQLite table.
 *
 * @author graham.
 */
public interface ApodContract {

    interface Picks extends BaseColumns {

        String CREATE_PICKS_TABLE = "CREATE TABLE IF NOT EXISTS " + Picks.TABLE_NAME + "(" +
                Picks._ID + " INTEGER PRIMARY KEY," +
                Picks.TITLE + " TEXT NOT NULL," +
                Picks.EXPLANATION + " TEXT NOT NULL," +
                Picks.DATE + " TEXT NOT NULL," +
                Picks.MEDIA_TYPE + " TEXT NOT NULL," +
                Picks.URL + " TEXT NOT NULL" +
                ")";

        String TABLE_NAME = "picks";
        String TITLE = "title";
        String DATE = "date";
        String EXPLANATION = "explanation";
        String MEDIA_TYPE = "media_type";
        String URL = "url";
    }
}
