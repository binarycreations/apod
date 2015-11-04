package net.binarycreations.apod.app.background;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 *
 *
 * @author graham.
 */
final class AsyncTaskRunner<T> extends AsyncTask<Void, Void, T> {

    private WeakReference<BackgroundJob<T>> mJob;

    private WeakReference<Conclusion<T>> mConclusion;

    private Exception mJobError;

    AsyncTaskRunner(WeakReference<BackgroundJob<T>> backgroundJob, WeakReference<Conclusion<T>> conclusion) {
        mJob = backgroundJob;
        mConclusion = conclusion;
    }

    @Override
    protected T doInBackground(Void... params) {
        T result = null;

        BackgroundJob<T> toRun = mJob.get();
        if (toRun != null) {
            try {
                result = toRun.doInBackground();
            } catch (Exception e) {
                mJobError = e;
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);

        Conclusion<T> toCallBack = mConclusion.get();
        if (toCallBack != null) {
            if (mJobError != null) {
                toCallBack.onError(mJobError);
            } else if (result != null) {
                toCallBack.onConclusion(result);
            }
        }
    }
}
