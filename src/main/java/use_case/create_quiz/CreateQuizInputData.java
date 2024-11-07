package use_case.create_quiz;

import java.io.File;
import java.util.List;

/**
 * The Input Data for the Login Use Case.
 */
public class CreateQuizInputData {

    private final String courseName;
    private final String userUsername;
    private final List<File> courseMaterial;
    private final int numQuestions;
    private final String difficulty;
    private final String language;
    private final String additionalInfo;

    public CreateQuizInputData(String courseName, String userUsername, List<File> courseMaterial, int numQuestions,
                               String difficulty, String language, String additionalInfo) {
        this.courseName = courseName;
        this.userUsername = userUsername;
        this.courseMaterial = courseMaterial;
        this.numQuestions = numQuestions;
        this.difficulty = difficulty;
        this.language = language;
        this.additionalInfo = additionalInfo;
    }

    public String getCourseName() {return courseName;}
    public String getUserUsername() {return userUsername;}
    public List<File> getCourseMaterial() {return courseMaterial;}
    public int getNumQuestions() {return numQuestions;}
    public String getDifficulty() {return difficulty;}
    public String getLanguage() {return language;}
    public String getAdditionalInfo() {return additionalInfo;}

}
