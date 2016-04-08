package net.binarycreations.apod.archive;

import net.binarycreations.apod.domain.AstroPick;

import java.util.List;

/**
 * Interface that describes the behaviour to show recently astronomy pictures.
 *
 * @author graham.
 */
interface ArchiveView {

    void displayPictures(List<AstroPick> toShow);

    void displayNoConnectivity();

    void displayArchiveUnavailable();

    void displayAstroExplanation(AstroPick item);
}
