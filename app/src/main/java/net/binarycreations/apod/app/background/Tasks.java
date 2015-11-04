package net.binarycreations.apod.app.background;

/**
 * Entry point to create new {@link Task}.
 *
 * @author graham.
 */
public class Tasks {

    /** Used to create all background async task instances for every {@link Task}. */
    private static final AsyncTaskRunnerFactory TASK_RUNNER_FACTORY = new AsyncTaskRunnerFactory();

    /**
     * Create a new {@link Task}.
     *
     * @return a new instance, ready to run a task.
     */
    public Task createTask() {
        return new Task(TASK_RUNNER_FACTORY);
    }

}
