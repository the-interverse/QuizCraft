package use_case.create_quiz;

import entity.Quiz;

/**
 * Output Data for the Login Use Case.
 */
public class CreateQuizOutputData {

    private final String coursename;
    private final boolean useCaseFailed;
    private final Quiz quiz;

    public CreateQuizOutputData(String coursename, boolean useCaseFailed, Quiz quiz) {
        this.coursename = coursename;
        this.useCaseFailed = useCaseFailed;
        this.quiz = quiz;
    }

    public String getCoursename() {return coursename;}
    public boolean isUseCaseFailed() {return useCaseFailed;}
    public Quiz getQuiz() {return quiz;}

}
