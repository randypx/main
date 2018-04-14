package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.education.Subject;
import seedu.address.model.event.Time;
import seedu.address.model.person.Name;

//@@author randypx
public class FormCommandTest {

    private static final Name VALID_NAME = new Name("CS2103T");
    private static final Subject VALID_SUBJECT = new Subject("Mathematics");
    private static final Time VALID_START_DATE = new Time("10/10/2018", true);
    private static final Time VALID_END_DATE = new Time("10/12/2018", true);
    private static final List<Index> VALID_INDEXES =
            new ArrayList<>(Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2)));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FormCommand(null, VALID_SUBJECT, VALID_START_DATE, VALID_END_DATE, VALID_INDEXES);
    }

    @Test
    public void constructor_nullSubject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FormCommand(VALID_NAME, null, VALID_START_DATE, VALID_END_DATE, VALID_INDEXES);
    }

    @Test
    public void constructor_nullStartDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FormCommand(VALID_NAME, VALID_SUBJECT, null, VALID_END_DATE, VALID_INDEXES);
    }

    @Test
    public void constructor_nullEndDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FormCommand(VALID_NAME, VALID_SUBJECT, VALID_START_DATE, null, VALID_INDEXES);
    }

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FormCommand(VALID_NAME, VALID_SUBJECT, VALID_START_DATE, VALID_END_DATE, null);
    }

    /**
     * Generates a new FormCommand with the details of the given details.
     */
    private FormCommand getFormCommand(Name name, Subject subject, Time start, Time end,
                                      List<Index> indexes, Model model) {
        FormCommand command = new FormCommand(name, subject, start, end, indexes);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
