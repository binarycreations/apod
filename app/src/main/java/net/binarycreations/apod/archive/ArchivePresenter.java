package net.binarycreations.apod.archive;

import net.binarycreations.apod.domain.AstroPick;

import org.threeten.bp.LocalDate;

/**
 * Handles user interactions whilst browsing the archive.
 *
 * @author graham.
 */
public interface ArchivePresenter {

    /**
     * Set the view.
     *
     * @param view instance to use.
     */
    void setView(ArchiveView view);

    /**
     * Load astronomy pictures.
     */
    void loadArchivePictures(LocalDate from, LocalDate to);

    /**
     * Handle the selection of an astrology picture.
     *
     * @param item the user has clicked on.
     */
    void onAstroPictureClick(AstroPick item);
}
