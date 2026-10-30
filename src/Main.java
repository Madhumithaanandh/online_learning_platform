import java.util.Arrays;
import service.ProgressService;

public class Main {

    public static void main(String[] args) {

        ProgressService service = new ProgressService();

        // Define lesson order
        service.setLessonOrder(Arrays.asList(101, 102, 103));

        int studentId = 1;

        // Lesson 1 - Video
        System.out.println("----- LESSON 1: VIDEO -----");
        service.updateVideoProgress(studentId, 101, 90);
        service.displayProgress(studentId);

        // Lesson 2 - Reading
        System.out.println("\n----- LESSON 2: READING -----");
        service.completeReadingLesson(studentId, 102);
        service.displayProgress(studentId);

        // Lesson 3 - Quiz
        System.out.println("\n----- LESSON 3: QUIZ -----");
        service.submitQuiz(studentId, 103, 75);
        service.displayProgress(studentId);
    }
}