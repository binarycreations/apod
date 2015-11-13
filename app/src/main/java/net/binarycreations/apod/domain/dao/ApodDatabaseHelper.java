package net.binarycreations.apod.domain.dao;

import static net.binarycreations.apod.domain.dao.ApodContract.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Stores all astronomy pick of the day data offline.
 *
 * @author graham.
 */
public class ApodDatabaseHelper extends SQLiteOpenHelper {

    public static final String APOD_DATABASE = "apod";

    private static final int DB_VERSION = 1;

    private static final String TAG = ApodDatabaseHelper.class.getSimpleName();

    /**
     * Constructor.
     *
     * @param context used to open or create the database.
     */
    public ApodDatabaseHelper(Context context) {
        super(context, APOD_DATABASE, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Log.d(TAG, "onConfigure");

        // Enforce relational constraints
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(Picks.CREATE_PICKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
    }
}
