package net.binarycreations.apod.archive;

import java.util.Date;

/**
 * Responsbile for
 *
 * @author graham.
 */
public interface ArchivePresenter {

    void setView(ArchiveView view);

    /**
     * Load astronomy pictures.
     */
    void loadArchivePictures(Date from, Date to);

}
