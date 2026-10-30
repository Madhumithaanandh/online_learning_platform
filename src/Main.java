import java.util.ArrayList;
import java.util.Scanner;

import Enrollment.EnrollmentModels;
import Enrollment.EnrollmentService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create users
        EnrollmentModels.User instructor =
                new EnrollmentModels.User(
                        "I101", "Arun",
                        EnrollmentModels.UserRole.INSTRUCTOR);

        EnrollmentModels.User student1 =
                new EnrollmentModels.User(
                        "S101", "Abinaya",
                        EnrollmentModels.UserRole.STUDENT);

        EnrollmentModels.User student2 =
                new EnrollmentModels.User(
                        "S102", "Priya",
                        EnrollmentModels.UserRole.STUDENT);

        // Create courses
        ArrayList<EnrollmentModels.Course> courses =
                new ArrayList<>();

        courses.add(new EnrollmentModels.Course(
                "C101", "Java Programming", "I101",
                999, EnrollmentModels.CourseStatus.PUBLISHED));

        courses.add(new EnrollmentModels.Course(
                "C102", "Python Basics", "I101",
                0, EnrollmentModels.CourseStatus.PUBLISHED));

        courses.add(new EnrollmentModels.Course(
                "C103", "Web Development", "I101",
                1499, EnrollmentModels.CourseStatus.DRAFT));

        // Create users list
        ArrayList<EnrollmentModels.User> users =
                new ArrayList<>();

        users.add(instructor);
        users.add(student1);
        users.add(student2);

        EnrollmentService service = new EnrollmentService();

        int choice;

        do {
            System.out.println("\n===== ONLINE LEARNING PLATFORM =====");
            System.out.println("1. View Courses");
            System.out.println("2. Enroll in Course");
            System.out.println("3. Make Payment / Retry Payment");
            System.out.println("4. View Enrollments");
            System.out.println("5. View Active Course Count");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\nAVAILABLE COURSES");

                    for (EnrollmentModels.Course c : courses) {

                        System.out.println(
                                c.courseId + " - " + c.title
                                + " | Price: " + c.price
                                + " | Status: " + c.status);
                    }

                    break;

                case 2:

                    System.out.print("Enter Student ID: ");
                    String studentId = sc.nextLine();

                    System.out.print("Enter Course ID: ");
                    String courseId = sc.nextLine();

                    EnrollmentModels.User selectedStudent = null;
                    EnrollmentModels.Course selectedCourse = null;

                    for (EnrollmentModels.User u : users) {

                        if (u.userId.equals(studentId)) {
                            selectedStudent = u;
                            break;
                        }
                    }

                    for (EnrollmentModels.Course c : courses) {

                        if (c.courseId.equals(courseId)) {
                            selectedCourse = c;
                            break;
                        }
                    }

                    if (selectedStudent == null) {
                        System.out.println("Student not found.");
                    } else if (selectedCourse == null) {
                        System.out.println("Course not found.");
                    } else {
                        System.out.println(
                                service.enrollStudent(
                                        selectedStudent,
                                        selectedCourse));
                    }

                    break;

                case 3:

                    System.out.print("Enter Enrollment ID: ");
                    String enrollmentId = sc.nextLine();

                    System.out.print("Was payment successful? (yes/no): ");
                    String answer = sc.nextLine();

                    boolean success = answer.equalsIgnoreCase("yes");

                    System.out.println(
                            service.processPayment(
                                    enrollmentId, success));

                    break;

                case 4:

                    service.displayEnrollments();

                    break;

                case 5:

                    System.out.print("Enter Student ID: ");
                    String id = sc.nextLine();

                    System.out.println(
                            "Active Courses: "
                            + service.countActiveCourses(id));

                    break;

                case 6:

                    System.out.println(
                            "Thank you for using the platform!");

                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}