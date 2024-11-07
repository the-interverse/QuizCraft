package use_case.create_quiz;
import java.io.File;

public class CreateQuizInputData {
    private String quizName;
    private int numQuestions;
    private String difficulty;
    private File pdf;

    public CreateQuizInputData(String quizName, int numQuestions, String difficulty, File pdf) {
        this.quizName = quizName;
        this.numQuestions = numQuestions;
        this.difficulty = difficulty;
        this.pdf = pdf;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public File getPdf() {
        return pdf;
    }
}
