package net.binarycreations.apod.domain.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
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

    private final SQLiteOpenHelper mDatabaseHelper;

    public SqliteAstroPickDao(SQLiteOpenHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public long insert(AstroPick pick) {

        ContentValues values = new ContentValues();
        values.put(Picks.TITLE, pick.getTitle());
        values.put(Picks.EXPLANATION, pick.getExplanation());
        values.put(Picks.MEDIA_TYPE, pick.getType().toString());
        values.put(Picks.URL, pick.getUrl());
        values.put(Picks.DATE, pick.getDate().atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        return database.insert(Picks.TABLE_NAME, null, values);
    }

    @Override
    public List<AstroPick> findAllBetweenDates(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before to date");
        }

        List<AstroPick> result = new ArrayList<>();

        String fromInUtc = from.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        String toInUtc = to.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.query(Picks.TABLE_NAME, null, Picks.DATE + " >= ? AND " + Picks.DATE + " <= ?",
                new String[] { fromInUtc, toInUtc }, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                result.add(from(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    private AstroPick from(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(Picks.TITLE));
        String explanation = cursor.getString(cursor.getColumnIndex(Picks.EXPLANATION));
        String date = cursor.getString(cursor.getColumnIndex(Picks.DATE));
        String mediaType = cursor.getString(cursor.getColumnIndex(Picks.MEDIA_TYPE));
        String url = cursor.getString(cursor.getColumnIndex(Picks.URL));

        return new AstroPick(title, explanation, url, MediaType.valueOf(mediaType), ZonedDateTime.parse(date)
                .toLocalDate());
    }
}
