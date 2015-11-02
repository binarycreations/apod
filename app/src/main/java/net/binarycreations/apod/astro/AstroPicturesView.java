package net.binarycreations.apod.astro;

import net.binarycreations.apod.domain.AstroItem;

import java.util.List;

/**
 * Interface that describes the behaviour to show recently astronomy pictures.
 *
 * @author graham.
 */
public interface AstroPicturesView {

    void displayPictures(List<AstroItem> toShow);

}
