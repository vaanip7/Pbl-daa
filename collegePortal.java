import java.util.*;
class Assignment {
    String title;
    String deadline;
    Assignment(String title, String deadline) {
        this.title = title;
        this.deadline = deadline;
    }
}
class Submission {
    String studentName;
    String assignmentTitle;
    String fileName;
    Submission(String studentName, String assignmentTitle, String fileName) {
        this.studentName = studentName;
        this.assignmentTitle = assignmentTitle;
        this.fileName = fileName;
    }
}
public class CollegePortal {
    static Scanner sc = new Scanner(System.in);
    static List<Assignment> assignments = new ArrayList<>();
    static List<Submission> submissions = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Faculty Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    facultyMenu();
                    break;
                case 2:
                    studentMenu();
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }
    static void facultyMenu() {
        while (true) {
            System.out.println("\n--- Faculty Panel ---");
            System.out.println("1. Create Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. View Submissions");
            System.out.println("4. Logout");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    createAssignment();
                    break;
                case 2:
                    viewAssignments();
                    break;
                case 3:
                    viewSubmissions();
                    break;
                case 4:
                    return;
            }
        }
    }
    static void studentMenu() {
        System.out.print("Enter your name: ");
        String name = sc.next();
        while (true) {
            System.out.println("\n--- Student Panel ---");
            System.out.println("1. View Assignments");
            System.out.println("2. Submit Assignment");
            System.out.println("3. Logout");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    viewAssignments();
                    break;
                case 2:
                    submitAssignment(name);
                    break;
                case 3:
                    return;
            }
        }
    }
    static void createAssignment() {
        sc.nextLine(); 
        System.out.print("Enter Assignment Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Deadline: ");
        String deadline = sc.nextLine();
        assignments.add(new Assignment(title, deadline));
        System.out.println("Assignment Created Successfully!");
    }
    static void viewAssignments() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments available.");
            return;
        }
        System.out.println("\n--- Assignments ---");
        for (int i = 0; i < assignments.size(); i++) {
            Assignment a = assignments.get(i);
            System.out.println((i + 1) + ". " + a.title + " | Deadline: " + a.deadline);
        }
    }
    static void submitAssignment(String studentName) {
        if (assignments.isEmpty()) {
            System.out.println("No assignments available.");
            return;
        }
        viewAssignments();
        System.out.print("Select Assignment Number: ");
        int index = sc.nextInt() - 1;
        if (index < 0 || index >= assignments.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        sc.nextLine();
        System.out.print("Enter File Name (e.g. assignment.pdf): ");
        String fileName = sc.nextLine();
        submissions.add(new Submission(studentName,
                assignments.get(index).title,
                fileName));
        System.out.println("Assignment Submitted Successfully!");
    }
    static void viewSubmissions() {
        if (submissions.isEmpty()) {
            System.out.println("No submissions yet.");
            return;
        }
        System.out.println("\n--- Submissions ---");
        for (Submission s : submissions) {
            System.out.println("Student: " + s.studentName +
                    " | Assignment: " + s.assignmentTitle +
                    " | File: " + s.fileName);
        }
    }
}