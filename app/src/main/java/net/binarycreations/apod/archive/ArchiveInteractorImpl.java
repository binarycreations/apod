package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.background.BackgroundJob;
import net.binarycreations.apod.app.background.Conclusion;
import net.binarycreations.apod.app.background.Tasks;
import net.binarycreations.apod.domain.AstroItem;
import net.binarycreations.apod.client.NasaApodClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Provides functionality for searching and retrieving astronomy pictures from the NASA archive.
 *
 * @author graham.
 */
public class ArchiveInteractorImpl implements ArchiveInteractor {

    private final NasaApodClient mClient;

    private final Tasks mTasks;

    ArchiveInteractorImpl(Tasks tasks, NasaApodClient client) {
        mTasks = tasks;
        mClient = client;
    }

    @Override
    public void getArchiveItems(final Date from, final Date to, Conclusion<List<AstroItem>> archiveItems) {
        mTasks.createTask().executeTask(new BackgroundJob<List<AstroItem>>() {

            @Override
            public List<AstroItem> doInBackground() throws Exception {
                List<AstroItem> result = new ArrayList<AstroItem>();

                List<Date> daysBetween = getDaysBetweenInReverse(from, to);
                for(Date day : daysBetween) {
                    result.add(mClient.requestAstronomyPick(day));
                }

                return result;
            }
        }, archiveItems);
    }

    private List<Date> getDaysBetweenInReverse(Date from, Date to) {
        List<Date> daysBetween = new ArrayList<>();

        Calendar fromCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        fromCalendar.setTime(from);

        Calendar toCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        toCalendar.setTime(to);

        while(toCalendar.after(fromCalendar)) {
            daysBetween.add(toCalendar.getTime());
            toCalendar.add(Calendar.DAY_OF_YEAR, -1);
        }

        daysBetween.add(fromCalendar.getTime());

        return daysBetween;
    }
}
