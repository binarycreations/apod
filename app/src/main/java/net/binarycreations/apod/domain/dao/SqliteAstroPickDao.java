package net.binarycreations.apod.domain.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

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
    public long insert(@NonNull AstroPick pick) {

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
    public AstroPick findPick(@NonNull LocalDate date) {
        AstroPick result = null;

        String dateInUtc = date.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Picks.TABLE_NAME + " WHERE " + Picks.DATE + " = ?",
                new String[] { dateInUtc });

        if (cursor.moveToFirst()) {
            result = astroPickFrom(cursor);
        }

        return result ;
    }

    private AstroPick astroPickFrom(Cursor cursor) {
        AstroPick result = null;

        if (!cursor.isNull(cursor.getColumnIndex(Picks.TITLE))) {
            String title = cursor.getString(cursor.getColumnIndex(Picks.TITLE));
            String explanation = cursor.getString(cursor.getColumnIndex(Picks.EXPLANATION));
            String date = cursor.getString(cursor.getColumnIndex(Picks.DATE));
            String mediaType = cursor.getString(cursor.getColumnIndex(Picks.MEDIA_TYPE));
            String url = cursor.getString(cursor.getColumnIndex(Picks.URL));

            result = new AstroPick(title, explanation, url, MediaType.valueOf(mediaType), ZonedDateTime.parse(date)
                    .toLocalDate());
        }

        return result;
    }
}
