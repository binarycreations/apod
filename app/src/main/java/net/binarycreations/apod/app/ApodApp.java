package net.binarycreations.apod.app;

import android.app.Application;
import android.util.Log;

import net.binarycreations.apod.archive.ArchiveFactory;

/**
 * A custom application class used as the central hook for application dependancies and global data.
 *
 * @author graham.
 */
public class ApodApp extends Application {

    private static final String TAG = ApodApp.class.getSimpleName();

    private static ApodApp sInstance;

    private ArchiveFactory mArchiveFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        sInstance = this;
    }

    public static ApodApp getInstance() {
        return sInstance;
    }

    public ArchiveFactory getArchiveFactory() {
        if (mArchiveFactory == null) {
            mArchiveFactory = new ArchiveFactory();
        }

        return mArchiveFactory;
    }

}
