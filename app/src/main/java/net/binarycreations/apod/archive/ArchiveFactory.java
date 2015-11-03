package net.binarycreations.apod.archive;

/**
 * Provides access to archive related functionality.
 *
 * @author graham.
 */
public class ArchiveFactory {

    public ArchivePresenter getArchivePresenter() {
        return new ArchivePresenterImpl();
    }

}
