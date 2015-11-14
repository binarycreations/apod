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

import java.util.List;

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
    private static final LocalDate DATE_13_11_15 = LocalDate.parse("2015-11-13");
    private static final LocalDate DATE_14_11_15 = LocalDate.parse("2015-11-14");
    private static final LocalDate DATE_16_11_15 = LocalDate.parse("2015-11-16");
    private static final LocalDate DATE_17_11_15 = LocalDate.parse("2015-11-17");
    private static final LocalDate DATE_25_10_15 = LocalDate.parse("2015-10-25");
    private static final LocalDate DATE_24_10_15 = LocalDate.parse("2015-10-24");

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
    public void testInsert_shouldAddRecordToDatabase() {
        AstroPick pick = fromDate(DATE_24_10_15);

        long rowId = sut.insert(pick);
        assertEquals(1, rowId);
        assertAstroPickAtRow(pick, rowId);
    }

    @Test
    public void findAllBetweenDates_shouldIncludeFromDate() {
        AstroPick expected = fromDate(DATE_10_11_15);
        sut.insert(expected);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertFalse(actual.isEmpty());
        assertAstroPickEquals(expected, actual.get(0));
    }

    @Test
    public void findAllBetweenDates_shouldIncludeToDate() {
        AstroPick expected = fromDate(DATE_16_11_15);
        sut.insert(expected);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertFalse(actual.isEmpty());
        assertAstroPickEquals(expected, actual.get(0));
    }

    @Test
    public void findAllBetweenDates_shouldReturnDateInBetween() {
        AstroPick expected = fromDate(DATE_13_11_15);
        sut.insert(expected);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertFalse(actual.isEmpty());
        assertAstroPickEquals(expected, actual.get(0));
    }

    @Test
    public void findAllBetweenDates_shouldReturnDatesOnBoundariesInAscOrder() {
        AstroPick expectedFirst = fromDate(DATE_16_11_15);
        AstroPick expectedSecond = fromDate(DATE_10_11_15);

        sut.insert(expectedFirst);
        sut.insert(expectedSecond);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertFalse(actual.isEmpty());
        assertAstroPickEquals(expectedFirst, actual.get(0));
        assertAstroPickEquals(expectedSecond , actual.get(1));
    }

    @Test
    public void findAllBetweenDates_shouldReturnDatesInBetweenInAscOrder() {
        AstroPick expectedFirst = fromDate(DATE_14_11_15);
        AstroPick expectedSecond = fromDate(DATE_13_11_15);

        sut.insert(expectedFirst);
        sut.insert(expectedSecond);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertFalse(actual.isEmpty());
        assertAstroPickEquals(expectedFirst, actual.get(0));
        assertAstroPickEquals(expectedSecond , actual.get(1));
    }

    @Test
    public void findAllBetweenDates_shouldReturnEmptyListWhenDateIsBeforeRange() {
        AstroPick expected = fromDate(DATE_9_11_15);
        sut.insert(expected);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void findAllBetweenDates_shouldReturnEmptyListWhenDateIsAfterRange() {
        AstroPick expected = fromDate(DATE_17_11_15);
        sut.insert(expected);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_16_11_15);

        assertTrue(actual.isEmpty());
    }


    @Test(expected = IllegalArgumentException.class)
    public void findAllBetweenDates_shouldThrowExceptionGivenFromIsAfterToDate() {
        sut.findAllBetweenDates(DATE_14_11_15, DATE_13_11_15);
    }

    @Test
    public void findAllBetweenDates_shouldFindDateGivenSingleDayRange() {
        AstroPick expected = fromDate(DATE_10_11_15);
        sut.insert(expected);

        List<AstroPick> actual = sut.findAllBetweenDates(DATE_10_11_15, DATE_10_11_15);

        assertFalse(actual.isEmpty());
        assertAstroPickEquals(expected, actual.get(0));
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

    private void assertAstroPickEquals(AstroPick expected, AstroPick actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getExplanation(), actual.getExplanation());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getDate(), actual.getDate()  );
    }

    private AstroPick fromDate(LocalDate date) {
        return new AstroPick("title", "explanation", "url", AstroPick.MediaType.IMAGE, date);
    }
}
