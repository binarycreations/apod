package net.binarycreations.apod.app.background;

/**
 * @author graham.
 */
public interface BackgroundJob<T> {

    T doInBackground() throws Exception;

}
