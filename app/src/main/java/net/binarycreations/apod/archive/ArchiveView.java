package net.binarycreations.apod.archive;

import net.binarycreations.apod.domain.AstroItem;

import java.util.List;

/**
 * Interface that describes the behaviour to show recently astronomy pictures.
 *
 * @author graham.
 */
interface ArchiveView {

    void displayPictures(List<AstroItem> toShow);

    void displayNoConnectivity();

    void displayArchiveUnavailable();
}
