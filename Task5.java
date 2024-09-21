
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class task5 {
    private static final Map<String, Course> courses = new HashMap<>();
    private static final Map<String, Student> students = new HashMap<>();
    private static final JFrame frame = new JFrame("Student Course Registration System");
    private static final JTextArea textArea = new JTextArea(10, 30);
    private static final JComboBox<String> studentComboBox = new JComboBox<>();
    private static final JComboBox<String> courseComboBox = new JComboBox<>();
    private static final JTextField studentNameField = new JTextField();
    private static Student currentStudent;

    public static void main(String[] args) {
        // Initialize courses and students
        initializeData();

        // Setup GUI
        setupGUI();

        // Display the main window
        frame.setVisible(true);
    }

    private static void initializeData() {
        // Adding some initial courses
        addCourse("CS101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "Mon-Wed-Fri 10:00-11:00");
        addCourse("MATH101", "Calculus I", "Introduction to calculus", 25, "Tue-Thu 09:00-10:30");

        // Adding some initial students
        addStudent("S001", "Alice Johnson");
        addStudent("S002", "Bob Smith");
    }

    private static void addCourse(String code, String title, String description, int capacity, String schedule) {
        courses.put(code, new Course(code, title, description, capacity, schedule));
    }

    private static void addStudent(String id, String name) {
        students.put(id, new Student(id, name));
        studentComboBox.addItem(id + ": " + name);
    }

    private static void setupGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // North panel for displaying course information
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        northPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(northPanel, BorderLayout.NORTH);

        // Center panel for student and course selection
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 2, 5, 5));

        centerPanel.add(new JLabel("Select Student:"));
        centerPanel.add(studentComboBox);

        centerPanel.add(new JLabel("Select Course:"));
        courseComboBox.addItem("Select Course");
        updateCourseList();
        centerPanel.add(courseComboBox);

        JButton registerButton = new JButton("Register for Course");
        registerButton.addActionListener(new RegisterButtonListener());
        centerPanel.add(registerButton);

        JButton dropButton = new JButton("Drop Course");
        dropButton.addActionListener(new DropButtonListener());
        centerPanel.add(dropButton);

        centerPanel.add(new JLabel("New Student Name:"));
        centerPanel.add(studentNameField);

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(new AddStudentButtonListener());
        centerPanel.add(addStudentButton);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    private static void updateCourseList() {
        courseComboBox.removeAllItems();
        courseComboBox.addItem("Select Course");
        for (Course course : courses.values()) {
            courseComboBox.addItem(course.getCode() + ": " + course.getTitle());
        }
    }

    private static void updateTextArea() {
        if (currentStudent == null) {
            textArea.setText("No student selected.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Student: ").append(currentStudent.getName()).append("\n");
        sb.append("Registered Courses:\n");
        for (String courseCode : currentStudent.getCourses()) {
            Course course = courses.get(courseCode);
            if (course != null) {
                sb.append(course).append("\n");
            }
        }
        textArea.setText(sb.toString());
    }

    private static class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStudent == null) {
                JOptionPane.showMessageDialog(frame, "Please select a student.");
                return;
            }

            String selectedCourseCode = getSelectedCourseCode();
            Course course = courses.get(selectedCourseCode);

            if (course == null) {
                JOptionPane.showMessageDialog(frame, "Invalid course selected.");
                return;
            }

            if (course.getAvailableSlots() <= 0) {
                JOptionPane.showMessageDialog(frame, "No available slots for this course.");
                return;
            }

            if (currentStudent.getCourses().contains(selectedCourseCode)) {
                JOptionPane.showMessageDialog(frame, "You are already registered for this course.");
                return;
            }

            currentStudent.getCourses().add(selectedCourseCode);
            course.registerStudent();
            updateTextArea();
            JOptionPane.showMessageDialog(frame, "Successfully registered for " + course.getTitle());
        }
    }

    private static class DropButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStudent == null) {
                JOptionPane.showMessageDialog(frame, "Please select a student.");
                return;
            }

            String selectedCourseCode = getSelectedCourseCode();
            Course course = courses.get(selectedCourseCode);

            if (course == null || !currentStudent.getCourses().contains(selectedCourseCode)) {
                JOptionPane.showMessageDialog(frame, "You are not registered for this course.");
                return;
            }

            currentStudent.getCourses().remove(selectedCourseCode);
            course.removeStudent();
            updateTextArea();
            JOptionPane.showMessageDialog(frame, "Successfully dropped " + course.getTitle());
        }
    }

    private static class AddStudentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentName = studentNameField.getText().trim();
            if (studentName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a student name.");
                return;
            }

            String studentId = "S" + String.format("%03d", students.size() + 1);
            addStudent(studentId, studentName);
            studentNameField.setText("");
            JOptionPane.showMessageDialog(frame, "Student added with ID: " + studentId);
        }
    }

    private static String getSelectedCourseCode() {
        String selectedItem = (String) courseComboBox.getSelectedItem();
        if (selectedItem != null && !selectedItem.equals("Select Course")) {
            return selectedItem.split(":")[0].trim();
        }
        return null;
    }

    static {
        studentComboBox.addActionListener(e -> {
            String selectedItem = (String) studentComboBox.getSelectedItem();
            if (selectedItem != null) {
                String studentId = selectedItem.split(":")[0].trim();
                currentStudent = students.get(studentId);
                updateTextArea();
            }
        });
    }
}

class Course {
    private final String code;
    private final String title;
    private final String description;
    private final String schedule;
    private int capacity;
    private int registeredStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSlots() {
        return capacity - registeredStudents;
    }

    public void registerStudent() {
        if (getAvailableSlots() > 0) {
            registeredStudents++;
        }
    }

    public void removeStudent() {
        if (registeredStudents > 0) {
            registeredStudents--;
        }
    }

    @Override
    public String toString() {
        return String.format("Course Code: %s\nTitle: %s\nDescription: %s\nSchedule: %s\nAvailable Slots: %d",
                code, title, description, schedule, getAvailableSlots());
    }
}

class Student {
    private final String id;
    private final String name;
    private final Set<String> courses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getCourses() {
        return courses;
    }
}
