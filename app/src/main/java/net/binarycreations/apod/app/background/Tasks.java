package net.binarycreations.apod.app.background;

/**
 * @author graham.
 */
public class Tasks {

    private static final AsyncTaskRunnerFactory TASK_RUNNER_FACTORY = new AsyncTaskRunnerFactory();

    public Task createTask() {
        return new Task(TASK_RUNNER_FACTORY);
    }

}
