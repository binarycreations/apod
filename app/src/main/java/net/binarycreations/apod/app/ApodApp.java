package net.binarycreations.apod.app;

import android.app.Application;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.squareup.okhttp.OkHttpClient;

import net.binarycreations.apod.BuildConfig;
import net.binarycreations.apod.app.background.Tasks;
import net.binarycreations.apod.archive.ArchiveFactory;
import net.binarycreations.apod.client.NasaApodClient;

/**
 * A custom application class used as the central hook for application dependencies and global data.
 *
 * @author graham.
 */
public class ApodApp extends Application {

    private static final String TAG = ApodApp.class.getSimpleName();

    private static ApodApp sInstance;

    private ArchiveFactory mArchiveFactory;

    private NasaApodClient mApodClient;

    private Tasks mTasks;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        // Required to init three ten timezone information from Android assets
        AndroidThreeTen.init(this);

        // Create graph of dependencies required by the application.
        mTasks = new Tasks();
        mApodClient = new NasaApodClient(new OkHttpClient(), BuildConfig.API_KEY);
        mArchiveFactory = new ArchiveFactory(mTasks, mApodClient);

        sInstance = this;
    }

    public static ApodApp getInstance() {
        return sInstance;
    }

    public ArchiveFactory getArchiveFactory() {
        return mArchiveFactory;
    }
}
