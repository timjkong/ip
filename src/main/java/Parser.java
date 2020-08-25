import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Command parse(String command) throws DukeException {
        if (command.equals("bye")) {
            return new ByeCommand();
        } else if (command.startsWith("list")) {
            return parseListCommand(command);
        } else if (command.startsWith("done ")) {
            int i = Integer.valueOf(command.substring(5, command.length()));
            return new DoneCommand(i);
        } else if (command.startsWith("delete ")) {
            int i = Integer.valueOf(command.substring(7, command.length()));
            return new DeleteCommand(i);
        } else if (command.startsWith("todo ")) {
            ToDo todo = new ToDo(command.substring(5, command.length()));
            return new AddCommand(todo);
        } else if (command.startsWith("deadline ")) {
            return parseDeadlineCommand(command);
        } else if (command.startsWith("event ")) {
            return parseEventCommand(command);
        } else {
            throw new DukeException("Sorry I don't understand!");
        }
    }

    public static ListCommand parseListCommand(String command) throws DukeException {
        String[] splits = command.split(" ");
        if (command.equals("list")) {
            return new ListCommand();
        } else if (splits[0].equals("list") && splits.length == 2) {
            LocalDate date = LocalDate.parse(splits[1]);
            return new ListCommand(date);
        } else {
            throw new DukeException("Sorry I don't understand");
        }
    }

    public static AddCommand parseDeadlineCommand(String command) throws DukeException {
        int cut = command.indexOf(" /by ");
        if (cut >= 9) {
            String desc = command.substring(9, cut);
            String by = command.substring(cut + 5, command.length());
            String[] dateAndTime = by.split(" ");
            LocalDate date = LocalDate.parse(dateAndTime[0]);
            Deadline deadline = null;
            if (dateAndTime.length == 2) {
                deadline = new Deadline(desc, date, LocalTime.parse(dateAndTime[1]));
            } else {
                deadline = new Deadline(desc, date);
            }
            return new AddCommand(deadline);
        } else {
            throw new DukeException("Invalid deadline input");
        }
    }

    public static AddCommand parseEventCommand(String command) throws DukeException {
        int cut = command.indexOf(" /at ");
        try {
            if (cut >= 9) {
                String desc = command.substring(6, cut);
                String at = command.substring(cut + 5, command.length());
                String[] dateAndTime = at.split(" ");
                LocalDate date = LocalDate.parse(dateAndTime[0]);
                Event event = null;
                if (dateAndTime.length == 2) {
                    String[] startAndEndTime = dateAndTime[1].split("-");
                    LocalTime startTime = LocalTime.parse(startAndEndTime[0]);
                    if (startAndEndTime.length == 2) {
                        event = new Event(desc, date, startTime, LocalTime.parse(startAndEndTime[1]));
                    } else {
                        event = new Event(desc, date, startTime);
                    }
                } else {
                    event = new Event(desc, date);
                }
                return new AddCommand(event);
            } else {
                throw new DukeException("Invalid event input");
            }
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid event input");
        }
    }
}
