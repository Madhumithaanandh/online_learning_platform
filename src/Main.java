import course.CourseService;
import model.Course;
import model.Section;
import model.Lesson;

public class Main {

    public static void main(String[] args) {

        CourseService courseService = new CourseService();

        // Create Course
        Course course = new Course(
                1,
                "Java Programming",
                "Learn Java from basics",
                "Programming",
                "Beginner",
                0
        );

        courseService.createCourse(course);

        // Create Section
        Section section = new Section(1, "Java Basics");

        courseService.addSection(1, section);

        // Create Lessons
        Lesson lesson1 = new Lesson(
                1, "Introduction to Java", "Video", 10, 1
        );

        Lesson lesson2 = new Lesson(
                2, "Java Variables", "Reading", 15, 2
        );

        Lesson lesson3 = new Lesson(
                3, "Java Data Types", "Video", 20, 3
        );

        // Add Lessons
        courseService.addLesson(1, 1, lesson1);
        courseService.addLesson(1, 1, lesson2);
        courseService.addLesson(1, 1, lesson3);

        // Publish Course
        courseService.publishCourse(1);

        // Display Course
        courseService.displayAllCourses();
    }
}