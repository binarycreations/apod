package net.binarycreations.apod.app.background;

/**
 * @author graham.
 */
public interface Conclusion<T> {

    void onError(Exception exception);

    void onConclusion(T result);

}
