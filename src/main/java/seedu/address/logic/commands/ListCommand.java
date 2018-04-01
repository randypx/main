package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleListEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Item List Changed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show listing of item selected. \n"
            + "Parameter: ITEM\n"
            + "Item list: person, appointments\n"
            + "Example: " + COMMAND_WORD + " appointment";

    private String item;

    public ListCommand(String item) {
        this.item = item;
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (type) {
        case TYPE_CONTACT:
            model.changeCurrentActiveListType(TYPE_CONTACT);
            EventsCenter.getInstance().post(new ToggleListEvent(TYPE_CONTACT));
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS + TYPE_CONTACT);

        case TYPE_STUDENT:
            model.changeCurrentActiveListType(TYPE_CONTACT);
            EventsCenter.getInstance().post(new ToggleListEvent(TYPE_CONTACT));
            model.updateFilteredPersonList(PREDICATE_SHOW_ONLY_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS + TYPE_STUDENT);

        case TYPE_APPOINTMENT:
            model.changeCurrentActiveListType(TYPE_APPOINTMENT);
            EventsCenter.getInstance().post(new ToggleListEvent(TYPE_APPOINTMENT));
            return new CommandResult(MESSAGE_SUCCESS + TYPE_APPOINTMENT);

        case TYPE_TASK:
            model.changeCurrentActiveListType(TYPE_TASK);
            EventsCenter.getInstance().post(new ToggleListEvent(TYPE_TASK));
            return new CommandResult(MESSAGE_SUCCESS + TYPE_TASK);

        default:
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.item.equals(((ListCommand) other).item)); // state check
    }
}
