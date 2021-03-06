package duke.tasks;

import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** This class represents a Deadline. */
public class Deadline extends Task {
    protected LocalDate date;
    protected LocalTime time;

    /**
     * Constructs a Deadline object associated with a description and a date.
     * @param description The description of the task.
     * @param date The date of the deadline.
     */
    public Deadline(String description, LocalDate date) {
        super(description);
        this.date = date;
    }

    /**
     * Constructs a Deadline object associated with a description, a date and a time.
     * @param description The description of the task.
     * @param date The date of the deadline.
     * @param time The time of the deadline.
     */
    public Deadline(String description, LocalDate date, LocalTime time) {
        super(description);
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        assert this.date != null : "date parameter of Deadline object cannot be null";
        return this.date;
    }

    /**
     * @return A String representing the Deadline object, to be used when saving deadlines to the storage file.
     */
    @Override
    public String txtFileFormat() {
        return "D" + DELIMITER + super.txtFileFormat() + DELIMITER + this.date.toString()
                + (this.time != null ? DELIMITER + this.time.toString() : "");
    }

    /**
     * @return A String representing the Deadline object, to be used when printing the Deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + (time != null ? " " + time.format(DateTimeFormatter.ofPattern("hh:mm a")) : "") + ")";
    }

    @Override
    public void updateDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void updateTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public void updateTime(LocalTime startTime, LocalTime endTime) throws DukeException {
        throw new DukeException("Deadlines don't have end times!");
    }
}
