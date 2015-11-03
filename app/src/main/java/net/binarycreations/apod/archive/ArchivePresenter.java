package net.binarycreations.apod.archive;

/**
 * Responsbile for
 *
 * @author graham.
 */
interface ArchivePresenter {

    void setView(ArchiveView view);

    /**
     * Load astronomy pictures.
     */
    void loadAstroPictures();

}
