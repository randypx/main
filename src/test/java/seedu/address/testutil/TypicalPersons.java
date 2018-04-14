package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.education.Class;
import seedu.address.model.education.exceptions.DuplicateClassException;
import seedu.address.model.event.Appointment;
import seedu.address.model.event.Task;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withTags().build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("smart").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withTags().build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withTags().build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withTags().build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withTags().build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withTags().build();
    public static final Person DING = new PersonBuilder().withName("Ding Thunderstorm").withPhone("81524871")
            .withEmail("hansolo@example.com").withAddress("Science Park Road").withTags().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    //@@author randypx
    // Students
    public static final Student STUDENT_ANGUS = new StudentBuilder().withName("Angus Wyndham")
            .withPhone("9867723").withEmail("wynd@example.com").withAddress("Centre Street")
            .withSubjects("CS2103T", "Biology").withTags().build();
    public static final Student STUDENT_BRUCE = new StudentBuilder().withName("Bruce Wayne")
            .withPhone("9575232").withEmail("star@example.com").withAddress("Hollywood")
            .withSubjects().withTags().build();
    public static final Student STUDENT_COOPER = new StudentBuilder().withName("Cooper Crouch")
            .withPhone("9247637").withEmail("freeman@example.com").withAddress("Kansas")
            .withSubjects("Mathematics", "Biology").withTags().build();
    public static final Student STUDENT_DAVID = new StudentBuilder().withName("David Gray")
            .withPhone("9234718").withEmail("alien@example.com").withAddress("Chinatown")
            .withSubjects("Mathematics").withTags().build();
    public static final Student STUDENT_EMILY = new StudentBuilder().withName("Emily Walter")
            .withPhone("8537425").withEmail("lily@example.com").withAddress("4th Avenue")
            .withSubjects("Biology", "Physics").withTags().build();

    // Manually added
    public static final Student STUDENT_FAUST = new StudentBuilder().withName("Faust Meier")
            .withPhone("9524284").withEmail("mephist@example.com").withAddress("raffles hall")
            .withTags("absent").withSubjects().build();
    public static final Student STUDENT_GUASS = new StudentBuilder().withName("Guass Muller")
            .withPhone("8824681").withEmail("greg@example.com").withAddress("university town")
            .withSubjects().build();
    public static final Student STUDENT_HELEN = new StudentBuilder().withName("Hellen Wetscott")
            .withPhone("8315264").withEmail("knight@example.com").withAddress("Sentosa Resort")
            .withSubjects().build();
    public static final Student STUDENT_ILLYA = new StudentBuilder().withName("Illya Einzbern")
            .withPhone("9275423").withEmail("berserker@example.com").withAddress("fuyuki")
            .withSubjects().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Student STUDENT_AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().withSubjects().build();
    public static final Student STUDENT_BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_STUDENT)
            .withSubjects().build();

    //@@author
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalContacts()) {
            try {
                if (!(person instanceof Student)) {
                    ab.addPerson(person);
                } else {
                    ab.addStudent((Student) person);
                }
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Appointment ap : TypicalEvents.getTypicalAppointments()) {
            try {
                ab.addAppointment(ap);
            } catch (DuplicateEventException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Task t : TypicalEvents.getTypicalTasks()) {
            try {
                ab.addTask(t);
            } catch (DuplicateEventException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Class c : TypicalClass.getTypicalClasses()) {
            try {
                ab.addClass(c);
            } catch (DuplicateClassException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Person> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, STUDENT_ANGUS, STUDENT_BRUCE,
                CARL, DANIEL, STUDENT_COOPER, ELLE, STUDENT_DAVID, FIONA, STUDENT_EMILY, GEORGE));
    }
}
