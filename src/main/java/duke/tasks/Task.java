package duke.tasks;

import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.LocalTime;

/** A generic class from which all task-related classes will inherit from. */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    protected final static String DELIMITER = " ~/~ ";

    /**
     * Constructs a task associated with a description.
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * @return A tick if the task has been marked as done and a cross otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    /**
     * @return A String representing the Task object, to be used when saving tasks to the storage file.
     */
    public String txtFileFormat() {
        return (this.isDone ? 1 : 0) + " ~/~ " + this.description;
    }

    /**
     * @return A String representing the Task object, to be used when printing the Task.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public abstract void updateDate(LocalDate date) throws DukeException;

    public abstract void updateTime(LocalTime time) throws DukeException;

    public abstract void updateTime(LocalTime startTime, LocalTime endTime) throws DukeException;

    public abstract LocalDate getDate();
}
