package net.binarycreations.apod.domain.dao;

import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import net.binarycreations.apod.app.ApodApp;
import net.binarycreations.apod.domain.AstroPick;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDate;

/**
 * Integration tests for {@link SqliteAstroPickDao}.
 *
 * @author graham.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class SqliteAstroPickDaoIntegrationTest extends AndroidTestCase {

    private ApodDatabaseHelper databaseHelper;

    private SqliteAstroPickDao sut;

    @Before
    public void setup() throws Exception {
        // Create the database
        setContext(ApodApp.getInstance());
        databaseHelper = new ApodDatabaseHelper(getContext());
        databaseHelper.getWritableDatabase();
        sut = new SqliteAstroPickDao(databaseHelper);
    }

    @After
    public void teardown() throws Exception {
        databaseHelper.close();
        getContext().deleteDatabase("apod");
    }

    @Test
    public void testInsert_shouldAddRecordToDatabase() {
        AstroPick pick = new AstroPick("title", "explanation", "url", AstroPick.MediaType.IMAGE, LocalDate.now());

        long rowId = sut.insert(pick);
        assertEquals(1, rowId);
        assertAstroPickAtRow(pick, rowId);
    }

    private void assertAstroPickAtRow(AstroPick expected, long rowId) {
        Cursor cursor = null;

        try {
            cursor = databaseHelper.getWritableDatabase().query(ApodContract.Picks.TABLE_NAME, null, ApodContract
                    .Picks._ID + " = ?", new String[] { String.valueOf(rowId) }, null, null, null);
            cursor.moveToFirst();

            String title = cursor.getString(cursor.getColumnIndex(ApodContract.Picks.TITLE));
            String explanation = cursor.getString(cursor.getColumnIndex(ApodContract.Picks.EXPLANATION));
            String url = cursor.getString(cursor.getColumnIndex(ApodContract.Picks.URL));
            String mediaType = cursor.getString(cursor.getColumnIndex(ApodContract.Picks.MEDIA_TYPE));
            String date = cursor.getString(cursor.getColumnIndex(ApodContract.Picks.DATE));

            assertEquals(expected.getTitle(), title);
            assertEquals(expected.getExplanation(), explanation);
            assertEquals(expected.getUrl(), url);
            assertEquals(expected.getType(), AstroPick.MediaType.valueOf(mediaType));
            assertEquals(expected.getDate(), LocalDate.parse(date));
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
