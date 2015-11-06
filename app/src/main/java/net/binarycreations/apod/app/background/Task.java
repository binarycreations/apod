package net.binarycreations.apod.app.background;

import android.os.AsyncTask;

/**
 * A task is a light weight and simple mechanism to execute code in the background, with memory safety in mind.
 *
 * @author graham.
 */
public class Task {

    private final AsyncTaskRunnerFactory mTaskRunnerFactory;

    Task(AsyncTaskRunnerFactory taskRunnerFactory) {
        mTaskRunnerFactory = taskRunnerFactory;
    }

    /**
     * Execute a task, running a background job until it concludes by finishing with a result.
     *
     * @param toRun the job to run in the background.
     * @param conclusion callback
     * @param <T> the type of the resulting data.
     */
    public <T> void executeTask(BackgroundJob<T> toRun, Conclusion<T> conclusion) {
        AsyncTaskRunner<T> runner = mTaskRunnerFactory.createAsyncTaskRunner(toRun, conclusion);
        runner.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, (Void) null);
    }
}
