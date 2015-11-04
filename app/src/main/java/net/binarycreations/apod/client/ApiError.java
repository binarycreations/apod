package net.binarycreations.apod.client;

/**
 * Represents a recoverable but unexpected error whilst interacting with the Apod API.
 *
 * @author graham.
 */
public class ApiError extends Exception {

    /**
     * Construct an API error.
     *
     * @param detailMessage reason for failure.
     */
    public ApiError(String detailMessage) {
        super(detailMessage);
    }
}
