package net.binarycreations.apod.app.background;

import java.lang.ref.WeakReference;

/**
 * @author graham.
 */
public class AsyncTaskRunnerFactory {

    public <T> AsyncTaskRunner<T> createAsyncTaskRunner(BackgroundJob<T> toRun, Conclusion<T> conclusion) {
        return new AsyncTaskRunner<>(new WeakReference<BackgroundJob<T>>(toRun),
                new WeakReference<Conclusion<T>>(conclusion));
    }

}
