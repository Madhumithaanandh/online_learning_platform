package course;

import java.util.ArrayList;

import model.Course;
import model.Section;
import model.Lesson;

public class CourseService {

    private ArrayList<Course> courses = new ArrayList<>();

    // Create Course
    public void createCourse(Course course) {

        if (course.getPrice() < 0) {
            System.out.println("Price cannot be negative");
            return;
        }

        courses.add(course);

        System.out.println("Course created successfully!");
    }

    // Display All Courses
    public void displayAllCourses() {

        if (courses.isEmpty()) {
            System.out.println("No courses available");
            return;
        }

        for (Course course : courses) {
            course.displayCourse();
            System.out.println("--------------------");
        }
    }

    // Find Course
    public Course findCourseById(int courseId) {

        for (Course course : courses) {

            if (course.getCourseId() == courseId) {
                return course;
            }
        }

        return null;
    }

    // Add Section
    public void addSection(int courseId, Section section) {

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (course.isPublished()) {
            System.out.println("Cannot add section after publishing");
            return;
        }

        course.addSection(section);

        System.out.println("Section added successfully!");
    }

    // Add Lesson
    public void addLesson(int courseId, int sectionId, Lesson lesson) {

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        for (Section section : course.getSections()) {

            if (section.getSectionId() == sectionId) {

                section.addLesson(lesson);

                System.out.println("Lesson added successfully!");
                return;
            }
        }

        System.out.println("Section not found");
    }

    // Publish Course
    public void publishCourse(int courseId) {

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (course.isPublished()) {
            System.out.println("Course is already published");
            return;
        }

        if (course.getSectionCount() < 1) {
            System.out.println("Course must have at least one section");
            return;
        }

        if (course.getTotalLessonCount() < 3) {
            System.out.println("Course must have at least three lessons");
            return;
        }

        course.publishCourse();

        System.out.println("Course published successfully!");
    }

    // Update Course Title
    public void updateCourseTitle(int courseId, String newTitle) {

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        course.setTitle(newTitle);

        System.out.println("Course title updated successfully!");
    }

    // Update Course Description
    public void updateCourseDescription(int courseId, String newDescription) {

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        course.setDescription(newDescription);

        System.out.println("Course description updated successfully!");
    }

    // Update Course Price
    public void updateCoursePrice(int courseId, double newPrice) {

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (newPrice < 0) {
            System.out.println("Price cannot be negative");
            return;
        }

        course.setPrice(newPrice);

        System.out.println("Course price updated successfully!");
    }
}