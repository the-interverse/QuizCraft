package use_case.create_quiz;
import java.io.File;

public class CreateQuizInputData {
    private String quizName;
    private int numQuestions;
    private String difficulty;
    private String filepath;
    private String username;

    public CreateQuizInputData(String quizName, int numQuestions, String difficulty, String filepath, String username) {
        this.quizName = quizName;
        this.numQuestions = numQuestions;
        this.difficulty = difficulty;
        this.filepath = filepath;
        this.username = username;
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

    public String getUsername() { return username; }
}
