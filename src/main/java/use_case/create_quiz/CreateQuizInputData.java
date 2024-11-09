package use_case.create_quiz;
import java.io.File;

public class CreateQuizInputData {
    private String quizName;
    private int numQuestions;
    private String difficulty;
    private String filepath;

    public CreateQuizInputData(String quizName, int numQuestions, String difficulty, String filepath) {
        this.quizName = quizName;
        this.numQuestions = numQuestions;
        this.difficulty = difficulty;
        this.filepath = filepath;
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

    public String getFilepath() {
        return filepath;
    }
}
