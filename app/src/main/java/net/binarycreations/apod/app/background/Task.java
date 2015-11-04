package net.binarycreations.apod.app.background;

import android.os.AsyncTask;

/**
 *
 *
 * @author graham.
 */
public class Task {

    private final AsyncTaskRunnerFactory mTaskRunnerFactory;

    Task(AsyncTaskRunnerFactory taskRunnerFactory) {
        mTaskRunnerFactory = taskRunnerFactory;
    }

    public <T> void executeTask(BackgroundJob<T> toRun, Conclusion<T> conclusion) {
        AsyncTaskRunner<T> runner = mTaskRunnerFactory.createAsyncTaskRunner(toRun, conclusion);
        runner.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, null);
    }
}
