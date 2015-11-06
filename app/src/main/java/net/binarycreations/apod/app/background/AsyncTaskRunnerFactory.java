package net.binarycreations.apod.app.background;

import java.lang.ref.WeakReference;

/**
 * Factory for creating new {@link AsyncTaskRunner) for each {@link Task}.
 *
 * @author graham.
 */
class AsyncTaskRunnerFactory {

    /**
     * Create a new AsyncTaskRunner.
     *
     * @param toRun contains the work that is run on the background thread.
     * @param conclusion the callback interface that is notifiued once the job has concluded.
     * @param <T> the resulting
     * @return new instance.
     */
    <T> AsyncTaskRunner<T> createAsyncTaskRunner(BackgroundJob<T> toRun, Conclusion<T> conclusion) {
        return new AsyncTaskRunner<>(new WeakReference<BackgroundJob<T>>(toRun),
                new WeakReference<Conclusion<T>>(conclusion));
    }

}
