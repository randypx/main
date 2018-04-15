package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOTUSED;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.education.Class;
import seedu.address.model.event.Appointment;
import seedu.address.model.event.Task;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.shortcuts.ShortcutDoubles;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getStudentList());
        assertEquals(Collections.emptyList(), addressBook.getContactList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getAppointmentList());
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
        assertEquals(Collections.emptyList(), addressBook.getCommandsList());

    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        AddressBookStub newData = new AddressBookStub(newPersons);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getAppointmentList().remove(0);
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }

    //@@author shanmu9898
    @Test
    public void getShortcutList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getCommandsList().remove(0);
    }
    //@@author shanmu9898

    /**
     * A stub ReadOnlyAddressBook whose persons, tags and events lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Person> contacts = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<ShortcutDoubles> commandslist = FXCollections.observableArrayList();
        private final ObservableList<Class> classes = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Student> students,
                        Collection<? extends Tag> tags, Collection<Appointment> appointments,
                        Collection<Task> tasks, Collection<ShortcutDoubles> commands,
                        Collection<Class> classes) {
            this.persons.setAll(persons);
            this.students.setAll(students);
            this.tags.setAll(tags);
            this.tasks.setAll(tasks);
            this.appointments.setAll(appointments);
            this.commandslist.setAll(commands);
            this.classes.setAll(classes);
        }

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
            this.contacts.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Person> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<ShortcutDoubles> getCommandsList() {
            return commandslist;
        }

        @Override
        public ObservableList<Class> getClassList() {
            return classes;
        }
    }

    @Test
    public void updatePerson_modifiedAddressBooks_noError() throws PersonNotFoundException, DuplicatePersonException {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(BOB).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).build();

        testAddressBook.updatePerson(BOB, AMY);

        assertEquals(testAddressBook, expectedAddressBook);
    }

    //@@author shanmu9898
    @Test
    public void removeTag_tagNotPresent_addressBookUnchanged() throws PersonNotFoundException,
                                                                      DuplicatePersonException {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();

        testAddressBook.removeTag(new Tag(VALID_TAG_NOTUSED));

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();

        assertEquals(expectedAddressBook, testAddressBook);
    }

    @Test
    public void removeTag_tagUsedByMultiplePeople_tagRemoved() throws PersonNotFoundException,
                                                                       DuplicatePersonException {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();
        testAddressBook.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(bobWithoutFriendTag)
                                              .withPerson(amyWithoutFriendTag).build();

        assertEquals(expectedAddressBook, testAddressBook);
    }
    //@@author

}
