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
import org.threeten.bp.ZonedDateTime;

/**
 * Integration tests for {@link SqliteAstroPickDao}.
 *
 * @author graham.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class SqliteAstroPickDaoIntegrationTest extends AndroidTestCase {

    private static final LocalDate DATE_9_11_15 = LocalDate.parse("2015-11-09");
    private static final LocalDate DATE_10_11_15 = LocalDate.parse("2015-11-10");
    private static final LocalDate DATE_11_11_15 = LocalDate.parse("2015-11-11");
    private static final LocalDate DATE_24_10_15 = LocalDate.parse("2015-10-24");

    private static final AstroPick ASTRO_PICK = fromDate(DATE_24_10_15);

    private ApodDatabaseHelper databaseHelper;

    private SqliteAstroPickDao sut;

    @Before
    public void setUp() throws Exception {
        // Create the database
        setContext(ApodApp.getInstance());
        databaseHelper = new ApodDatabaseHelper(getContext());
        databaseHelper.getWritableDatabase();
        sut = new SqliteAstroPickDao(databaseHelper);
    }

    @After
    public void tearDown() throws Exception {
        databaseHelper.close();
        getContext().deleteDatabase(ApodDatabaseHelper.APOD_DATABASE);
    }

    @Test
    public void insert_shouldAddRecordToDatabase() {
        long rowId = sut.insert(ASTRO_PICK);

        assertEquals(1, rowId);
        assertAstroPickAtRow(ASTRO_PICK, rowId);
    }

    @Test
    public void insert_shouldRecordDateInIso8601UtcTimestamp() {
        long rowId = sut.insert(ASTRO_PICK);

        assertEquals(1, rowId);
        assertAstroPickAtRow(ASTRO_PICK, 1);
    }

    @Test
    public void insert_shouldReturnMinusOneWhenInsertFails() {
        // This should return minus one and fail to insert, as the null fields will break non-null schema constraints.
        assertEquals(-1, sut.insert(new AstroPick(null, null, null, AstroPick.MediaType.IMAGE, DATE_24_10_15)));
    }

    @Test
    public void findOne_shouldReturnNullWhenNothingFound() {
        assertNull(sut.findPick(ASTRO_PICK.getDate()));
    }

    @Test
    public void findOne_shouldReturnPickForDate() {
        sut.insert(ASTRO_PICK);
        final AstroPick found = sut.findPick(ASTRO_PICK.getDate());
        assertAstroPickEquals(ASTRO_PICK, found);
    }

    @Test
    public void findOne_shouldReturnNullWhenDatesDiffer() {
        sut.insert(fromDate(DATE_9_11_15));
        sut.insert(fromDate(DATE_11_11_15));
        assertNull(sut.findPick(DATE_10_11_15));
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
            assertEquals(expected.getDate(), ZonedDateTime.parse(date).toLocalDate());
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void assertAstroPickEquals(AstroPick expected, AstroPick actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getExplanation(), actual.getExplanation());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getDate(), actual.getDate());
    }

    // Static to allow init of test data at the start of the fixture.
    private static AstroPick fromDate(LocalDate date) {
        return new AstroPick("title", "explanation", "url", AstroPick.MediaType.IMAGE, date);
    }
}
