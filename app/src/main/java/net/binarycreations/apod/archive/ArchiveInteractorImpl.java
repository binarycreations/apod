package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.background.BackgroundJob;
import net.binarycreations.apod.app.background.Conclusion;
import net.binarycreations.apod.app.background.Tasks;
import net.binarycreations.apod.client.NasaApodClient;
import net.binarycreations.apod.domain.AstroPick;
import net.binarycreations.apod.domain.dao.AstroPickDao;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality for searching and retrieving astronomy pictures from the NASA archive.
 *
 * @author graham.
 */
public class ArchiveInteractorImpl implements ArchiveInteractor {

    private final NasaApodClient mClient;

    private final Tasks mTasks;

    private final AstroPickDao mPickDao;

    ArchiveInteractorImpl(Tasks tasks, NasaApodClient client, AstroPickDao pickDao) {
        mTasks = tasks;
        mClient = client;
        mPickDao = pickDao;
    }

    @Override
    public void getArchiveItems(final LocalDate from, final LocalDate to, Conclusion<List<AstroPick>> archiveItems) {
        mTasks.createTask().executeTask(new BackgroundJob<List<AstroPick>>() {

            @Override
            public List<AstroPick> doInBackground() throws Exception {
                List<AstroPick> result = new ArrayList<>();

                List<LocalDate> daysBetween = getDaysBetweenInReverse(from, to);
                for(LocalDate day : daysBetween) {
                    AstroPick pick = mPickDao.findPick(day);
                    if (pick == null) {
                        pick = mClient.requestAstronomyPick(day);
                        mPickDao.insert(pick);
                    }
                    result.add(pick);
                }

                return result;
            }
        }, archiveItems);
    }

    /**
     * Get the date for each day between two points in reverse order.
     * <p/>
     *
     * @param from the start date for the sequence
     * @param to the end date of the sequence
     * @return a new List containing the date for each day, starting with the most recent date.
     */
    private List<LocalDate> getDaysBetweenInReverse(LocalDate from, LocalDate to) {
        List<LocalDate> daysBetween = new ArrayList<>();

        ZonedDateTime fromInUtc = from.atStartOfDay(ZoneOffset.UTC);
        ZonedDateTime toInUtc = to.atStartOfDay(ZoneOffset.UTC);

        while(toInUtc.isAfter(fromInUtc)) {
            daysBetween.add(toInUtc.toLocalDate());
            toInUtc = toInUtc.minusDays(1);
        }

        daysBetween.add(fromInUtc.toLocalDate());

        return daysBetween;
    }
}
