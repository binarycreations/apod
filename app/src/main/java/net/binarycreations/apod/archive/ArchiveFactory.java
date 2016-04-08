package net.binarycreations.apod.archive;

import net.binarycreations.apod.app.ApodApp;
import net.binarycreations.apod.app.background.Tasks;
import net.binarycreations.apod.client.NasaApodClient;
import net.binarycreations.apod.domain.dao.ApodDatabaseHelper;
import net.binarycreations.apod.domain.dao.SqliteAstroPickDao;

/**
 * Provides access to archive related functionality.
 *
 * @author graham.
 */
public class ArchiveFactory {

    private final Tasks mTasks;

    private final NasaApodClient mClient;

    public ArchiveFactory(Tasks tasks, NasaApodClient client) {
        mTasks = tasks;
        mClient = client;
    }

    public ArchiveInteractor getArchiveInteractor() {
        return new ArchiveInteractorImpl(mTasks, mClient, new SqliteAstroPickDao(new ApodDatabaseHelper(ApodApp
                .getInstance())));
    }

    public ArchivePresenter getArchivePresenter() {
        return new ArchivePresenterImpl(getArchiveInteractor());
    }

}
