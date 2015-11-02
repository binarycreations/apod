package net.binarycreations.apod.astro;

/**
 * Responsbile for
 *
 * @author graham.
 */
public interface AstroPicturePresenter {

    void setView(AstroPicturesView view);

    /**
     * Load astronomy pictures.
     */
    void loadAstroPictures();

}
