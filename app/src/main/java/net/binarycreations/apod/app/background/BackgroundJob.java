package net.binarycreations.apod.app.background;

/**
 * Represents work that needs to be completed in the background.
 *
 * @see Conclusion
 * @author graham.
 */
public interface BackgroundJob<T> {

    /**
     * Perform a computation on a background thread.
     *
     * @return is the result from the computation.
     * @throws Exception should be thrown when the job cannot be completed.
     */
    T doInBackground() throws Exception;

}
