package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.education.Class;
import seedu.address.model.education.Subject;
import seedu.address.model.education.UniqueClassList;
import seedu.address.model.education.exceptions.DuplicateClassException;
import seedu.address.model.education.exceptions.StudentClassNotFoundException;
import seedu.address.model.event.Appointment;
import seedu.address.model.event.Task;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.UniqueContactList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniqueStudentList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.shortcuts.ShortcutDoubles;
import seedu.address.model.shortcuts.UniqueShortcutDoublesList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueContactList contacts;
    private final UniquePersonList persons;
    private final UniqueStudentList students;
    private final UniqueTagList tags;
    private final UniqueEventList<Appointment> appointments;
    private final UniqueEventList<Task> tasks;
    private final UniqueShortcutDoublesList shortcutCommands;
    private final UniqueClassList classes;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        students = new UniqueStudentList();
        contacts = new UniqueContactList(persons, students);
        tags = new UniqueTagList();
        appointments = new UniqueEventList<>();
        tasks = new UniqueEventList<>();
        shortcutCommands = new UniqueShortcutDoublesList();
        classes = new UniqueClassList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setStudents(List<Student> students) throws DuplicatePersonException {
        this.students.setStudents(students);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setAppointments(List<Appointment> appointments)
            throws DuplicateEventException {
        this.appointments.setEvents(appointments);
    }

    //@@author shanmu9898
    public void setShorcutCommands(List<ShortcutDoubles> shorcutCommands) {
        this.shortcutCommands.setCommandsList(shorcutCommands);
    }

    //@@author
    public void setTasks(List<Task> tasks)
            throws DuplicateEventException {
        this.tasks.setEvents(tasks);
    }

    //@@author randypx
    public void setClasses(List<Class> classes) throws DuplicateClassException {
        this.classes.setClasses(classes);
    }

    //@@author
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<ShortcutDoubles> commandsList = newData.getCommandsList();
        List<Appointment> appointmentList = newData.getAppointmentList();
        List<Task> taskList = newData.getTaskList();
        List<Class> classList = newData.getClassList();
        List<Person> syncedContactList = newData.getContactList().stream()
                .map(this::syncWithMasterTagList).collect(Collectors.toList());

        try {
            setShorcutCommands(commandsList);
            setAppointments(appointmentList);
            setTasks(taskList);
            setClasses(classList);
            persons.setPersons(new UniquePersonList());
            students.setStudents(new UniqueStudentList());
            for (Person contact : syncedContactList) {
                if (contact instanceof Student) {
                    addStudent((Student) contact);
                } else {
                    addPerson(contact);
                }
            }
        } catch (DuplicatePersonException e) {
            throw new AssertionError("TeachConnect should not have duplicate persons");
        } catch (DuplicateEventException e) {
            throw new AssertionError("TeachConnect should not have duplicate events");
        } catch (DuplicateClassException e) {
            throw new AssertionError("TeachConnect should not have duplicate classes");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        if (!students.contains(new Student(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags()))) {
            try {
                persons.add(person);
            } catch (DuplicatePersonException e) {
                removeUnusedTags();
                throw e;
            }
        } else {
            throw new DuplicatePersonException();
        }
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        if (!students.contains(new Student(syncedEditedPerson.getName(), syncedEditedPerson.getPhone(),
                syncedEditedPerson.getEmail(), syncedEditedPerson.getAddress(), syncedEditedPerson.getTags()))) {
            try {
                persons.setPerson(target, syncedEditedPerson);
            } finally {
                removeUnusedTags();
            }
        } else {
            throw new DuplicatePersonException();
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// student-level operations

    //author randypx-reused
    /**
     * Adds a student to the address book.
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the student to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent student already exists.
     */
    public void addStudent(Student s) throws DuplicatePersonException {
        Student student = (Student) syncWithMasterTagList(s);
        if (!persons.contains(student)) {
            try {
                students.add(student);
            } catch (DuplicatePersonException e) {
                removeUnusedTags();
                throw e;
            }
        } else {
            throw new DuplicatePersonException();
        }
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedStudent}.
     *
     * @throws DuplicatePersonException if updating the student's details causes the student to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updateStudent(Student target, Student editedStudent)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedStudent);

        Student syncedEditedStudent = (Student) syncWithMasterTagList(editedStudent);
        if (!persons.contains(syncedEditedStudent)) {
            try {
                students.setStudent(target, syncedEditedStudent);
                for (Class group: classes) {
                    if (group.containStudent(target)) {
                        group.removeStudent(target);
                        group.addStudent(syncedEditedStudent);
                    }
                }
            } finally {
                removeUnusedTags();
            }
        } else {
            throw new DuplicatePersonException();
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeStudent(Student key) throws PersonNotFoundException {
        if (students.remove(key)) {
            for (Class group: classes) {
                if (group.containStudent(key)) {
                    group.removeStudent(key);
                }
            }
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// shortcut-level operations
    //@@author shanmu9898
    public void addShortcutDoubles(ShortcutDoubles s)
            throws UniqueShortcutDoublesList.DuplicateShortcutDoublesException {
        shortcutCommands.add(s);
    }

    /**
     *
     * @param commandShortcut
     * @return a boolean variable
     * @throws UniqueShortcutDoublesList.CommandShortcutNotFoundException
     */
    public boolean removeShortcutDouble(ShortcutDoubles commandShortcut)
            throws UniqueShortcutDoublesList.CommandShortcutNotFoundException {
        if (shortcutCommands.remove(commandShortcut)) {
            return true;
        } else {
            throw new UniqueShortcutDoublesList.CommandShortcutNotFoundException();
        }
    }

    //// tag-level operations
    //@@author
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author shanmu9898
    /**
     * Removes the particular tag for all people in the AddressBook.
     */
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (Person person : persons) {
            removeTagFromPerson(tag, person);
        }
        for (Student student : students) {
            removeTagFromStudent(tag, student);
        }

    }

    /**
     * Removes all {@code Tag}s that are not used by any {@code Person} or {@code Student} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInContacts = contacts.asObservableList().stream().map(Person::getTags).flatMap(Set::stream)
                .collect(Collectors.toSet());

        tags.setTags(tagsInContacts);
    }

    /**
     * Removes the particular tag for that particular person in the AddressBook.
     */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException, DuplicatePersonException {
        Set<Tag> listOfTags = new HashSet<>(person.getTags());

        if (listOfTags.contains(tag)) {
            listOfTags.remove(tag);
        } else {
            return;
        }

        Person updatedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), listOfTags);

        updatePerson(person, updatedPerson);
    }

    //@@author randypx-reused
    /**
     * Removes the particular tag for that particular student in the AddressBook.
     */
    private void removeTagFromStudent(Tag tag, Student student)
            throws PersonNotFoundException, DuplicatePersonException {
        Set<Tag> listOfTags = new HashSet<>(student.getTags());

        if (listOfTags.contains(tag)) {
            listOfTags.remove(tag);
        } else {
            return;
        }

        Student updatedStudent = new Student(student.getName(), student.getPhone(), student.getEmail(),
                student.getAddress(), listOfTags);

        updateStudent(student, updatedStudent);
    }

    //@@author
    /**
     *  Updates the master tag list to include tags in {@code person} or {@code student} that are not in the list.
     *  @return a copy of this {@code person} or {@code student} such that every tag in this person points to a Tag
     *  object in the master list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        if (person instanceof Student) {
            List<Subject> subjectList = ((Student) person).getSubjectList();
            return new Student(
                    person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), correctTagReferences,
                    subjectList);
        } else {
            return new Person(
                    person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), correctTagReferences);
        }
    }

    //// Event-level operations

    //@@author Sisyphus25
    /**
     * Adds an appointment to the address book.
     *
     * @throws DuplicateEventException if an equivalent appointment already exists.
     */
    public void addAppointment(Appointment e) throws DuplicateEventException {
        appointments.add(e);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws EventNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment key) throws EventNotFoundException {
        if (appointments.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    /**
     * Adds a task to the address book.
     *
     * @throws DuplicateEventException if an equivalent appointment already exists.
     */
    public void addTask(Task e) throws DuplicateEventException {
        tasks.add(e);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws EventNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTask(Task key) throws EventNotFoundException  {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    //// class-level operations
    //@@author randypx-reused
    /**
     * Adds a class to the address book.
     *
     * @throws DuplicateClassException if an equivalent class already exists.
     */
    public void addClass(Class c) throws DuplicateClassException {
        classes.add(c);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws StudentClassNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeClass(Class key) throws StudentClassNotFoundException {
        if (classes.remove(key)) {
            for (Student student: students) {
                if (student.isAttending(key)) {
                    student.exitClass(key);
                }
            }
            return true;
        } else {
            throw new StudentClassNotFoundException();
        }
    }

    //// util methods
    //@@author
    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, "
                + students.asObservableList().size() + " students, "
                + tags.asObservableList().size() +  " tags, "
                + appointments.asObservableList().size() + " appointments, "
                + tasks.asObservableList().size() +  " tasks, "
                + classes.asObservableList().size() + " classes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asObservableList();
    }

    @Override
    public ObservableList<Person> getContactList() {
        return contacts.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<ShortcutDoubles> getCommandsList() {
        return shortcutCommands.asObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<Class> getClassList() {
        return classes.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.students.equals(((AddressBook) other).students)
                && this.appointments.equals(((AddressBook) other).appointments)
                && this.tasks.equals(((AddressBook) other).tasks)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.shortcutCommands.equals(((AddressBook) other).shortcutCommands)
                && this.classes.equals(((AddressBook) other).classes));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, appointments, tasks, tags, shortcutCommands, classes);
    }
}
