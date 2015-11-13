package net.binarycreations.apod.domain.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import static net.binarycreations.apod.domain.AstroPick.MediaType;
import static net.binarycreations.apod.domain.dao.ApodContract.Picks;

/**
 * Use SQLite based DAO to access {@link AstroPick}.
 *
 * @author graham.
 */
public class SqliteAstroPickDao implements AstroPickDao {

    private final ApodDatabaseHelper mDatabaseHelper;

    public SqliteAstroPickDao(ApodDatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public long insert(AstroPick pick) {

        ContentValues values = new ContentValues();
        values.put(Picks.TITLE, pick.getTitle());
        values.put(Picks.EXPLANATION, pick.getExplanation());
        values.put(Picks.MEDIA_TYPE, pick.getType().toString());
        values.put(Picks.URL, pick.getUrl());
        values.put(Picks.DATE, pick.getDate().format(DateTimeFormatter.ISO_DATE));

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        return database.insert(Picks.TABLE_NAME, null, values);
    }

    @Override
    public List<AstroPick> findAllBetweenDates(LocalDate from, LocalDate to) {
        List<AstroPick> result = new ArrayList<>();

        String fromInUtc = from.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.query(Picks.TABLE_NAME, null, "SELECT * FROM " + Picks.TABLE_NAME + " WHERE " +
                Picks.DATE + " >= ? ", new String[] { fromInUtc }, null, null, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            result.add(from(cursor));
        }

        return result;
    }

    private AstroPick from(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(Picks.TITLE));
        String explanation = cursor.getString(cursor.getColumnIndex(Picks.EXPLANATION));
        String date = cursor.getString(cursor.getColumnIndex(Picks.DATE));
        String mediaType = cursor.getString(cursor.getColumnIndex(Picks.MEDIA_TYPE));
        String url = cursor.getString(cursor.getColumnIndex(Picks.URL));

        return new AstroPick(title, explanation, url, MediaType.valueOf(mediaType), LocalDate.now());
    }
}
