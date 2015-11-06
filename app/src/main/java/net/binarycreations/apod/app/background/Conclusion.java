package net.binarycreations.apod.app.background;

/**
 * Represents the result or conclustion from running a {@link Task}.
 * <p/>
 * All callback for a Conclusion occur on the main UI thread.
 *
 * @author graham.
 */
public interface Conclusion<T> {

    /**
     * A callback received when a Task has concluded in an error.
     *
     * @param exception that caused to task to error
     */
    void onError(Exception exception);

    /**
     * A callback received when a Task has concluded in an error.
     *
     * @param result the
     */
    void onConclusion(T result);

}
