import java.util.Scanner;

class Student {
    private String id;
    private String name;
    private double marks;
    private String rank;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = calculateRank(marks);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public String getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
        this.rank = calculateRank(marks);
    }

    private String calculateRank(double marks) {
        if (marks < 5.0) {
            return "Fail";
        } else if (marks < 6.5) {
            return "Medium";
        } else if (marks < 7.5) {
            return "Good";
        } else if (marks < 9.0) {
            return "Very Good";
        } else {
            return "Excellent";
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + rank;
    }
}

class StudentStack {
    private Student[] stack;
    private int top;

    public StudentStack(int capacity) {
        stack = new Student[capacity];
        top = -1;
    }

    public void push(Student student) {
        if (top < stack.length - 1) {
            stack[++top] = student;
        } else {
            System.out.println("Stack is full");
        }
    }

    public Student pop() {
        if (top >= 0) {
            return stack[top--];
        } else {
            System.out.println("Stack is empty");
            return null;
        }
    }

    public Student peek() {
        if (top >= 0) {
            return stack[top];
        } else {
            System.out.println("Stack is empty");
            return null;
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public Student findStudentById(String id) {
        for (int i = 0; i <= top; i++) {
            if (stack[i].getId().equals(id)) {
                return stack[i];
            }
        }
        return null;
    }

    public void displayStudents() {
        for (int i = 0; i <= top; i++) {
            System.out.println(stack[i]);
        }
    }
}

public class Main {
    private static StudentStack students = new StudentStack(100);  // Tạo stack với kích thước tối đa là 100 sinh viên

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students");
            System.out.println("5. Search Student");
            System.out.println("6. Display Students");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    editStudent(scanner);
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    sortStudents();
                    break;
                case 5:
                    searchStudent(scanner);
                    break;
                case 6:
                    displayStudents();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);

        scanner.close();
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student Marks: ");
        double marks = scanner.nextDouble();

        students.push(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    private static void editStudent(Scanner scanner) {
        System.out.print("Enter Student ID to edit: ");
        String id = scanner.nextLine();
        Student student = students.findStudentById(id);

        if (student != null) {
            System.out.print("Enter new Student Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new Student Marks: ");
            double marks = scanner.nextDouble();

            student.setName(name);
            student.setMarks(marks);
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();
        StudentStack tempStack = new StudentStack(students.size());
        boolean found = false;

        while (!students.isEmpty()) {
            Student student = students.pop();
            if (student.getId().equals(id)) {
                found = true;
                break;
            }
            tempStack.push(student);
        }

        while (!tempStack.isEmpty()) {
            students.push(tempStack.pop());
        }

        if (found) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void sortStudents() {
        Student[] array = new Student[students.size()];
        int i = 0;
        while (!students.isEmpty()) {
            array[i++] = students.pop();
        }

        for (int j = 0; j < array.length - 1; j++) {
            for (int k = j + 1; k < array.length; k++) {
                if (array[j].getMarks() < array[k].getMarks()) {
                    Student temp = array[j];
                    array[j] = array[k];
                    array[k] = temp;
                }
            }
        }

        for (Student student : array) {
            students.push(student);
        }

        System.out.println("Students sorted by marks in descending order.");
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine();
        Student student = students.findStudentById(id);

        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            students.displayStudents();
        }
    }
}
