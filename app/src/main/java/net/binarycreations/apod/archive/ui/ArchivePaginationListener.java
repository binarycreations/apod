package net.binarycreations.apod.archive.ui;

import net.binarycreations.apod.domain.AstroItem;

/**
 *
 *
 * @author graham.
 */
public interface ArchivePaginationListener {

    void onNextPagination(AstroItem atEnd);

}
