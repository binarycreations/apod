package net.binarycreations.apod.app;

import android.app.Application;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import net.binarycreations.apod.BuildConfig;
import net.binarycreations.apod.app.background.Tasks;
import net.binarycreations.apod.archive.ArchiveFactory;
import net.binarycreations.apod.client.NasaApodClient;

/**
 * A custom application class used as the central hook for application dependancies and global data.
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

        sInstance = this;
    }

    public static ApodApp getInstance() {
        return sInstance;
    }

    private synchronized Tasks getTasks() {
        if (mTasks == null) {
            mTasks = new Tasks();
        }
        return mTasks;
    }

    private synchronized NasaApodClient getApodClient() {
        if (mApodClient == null) {
            mApodClient = new NasaApodClient(new OkHttpClient(), BuildConfig.API_KEY);
        }
        return mApodClient;
    }

    public synchronized ArchiveFactory getArchiveFactory() {
        if (mArchiveFactory == null) {
            mArchiveFactory = new ArchiveFactory(getTasks(), getApodClient());
        }
        return mArchiveFactory;
    }
}
