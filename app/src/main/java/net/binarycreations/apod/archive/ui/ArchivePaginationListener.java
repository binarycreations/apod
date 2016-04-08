package net.binarycreations.apod.archive.ui;

import net.binarycreations.apod.domain.AstroPick;

/**
 *
 *
 * @author graham.
 */
public interface ArchivePaginationListener {

    void onNextPagination(AstroPick atEnd);

}
