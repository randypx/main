package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.education.Class;
import seedu.address.model.event.Appointment;
import seedu.address.model.event.Task;
import seedu.address.model.person.Person;
import seedu.address.model.shortcuts.ShortcutDoubles;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of appointments */
    ObservableList<Appointment> getFilteredAppointmentList();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    //@@author shanmu9898
    /** Returns an unmodifiable view of the filtered list of Shortcuts */
    ObservableList<ShortcutDoubles> getFilteredShortcutList();

    //@@author randypx-reused
    /** Returns an unmodifiable view of the filtered list of classes */
    ObservableList<Class> getFilteredClassList();

    //@@author
    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /** Returns the item type of the current active list that is shown in the GUI by the address book*/
    String getCurrentActiveListType();

}
