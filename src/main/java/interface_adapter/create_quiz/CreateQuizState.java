package interface_adapter.create_quiz;

public class CreateQuizState {
    private String quizName = "";
    private int questionAmount = 0;
    private String difficulty = "";
    private String pdfFileName = "No file selected";
    private String errorMessage;

    public CreateQuizState(CreateQuizState copy) {
        this.quizName = copy.quizName;
        this.questionAmount = copy.questionAmount;
        this.difficulty = copy.difficulty;
        this.pdfFileName = copy.pdfFileName;
        this.errorMessage = copy.errorMessage;
    }

    public CreateQuizState() {}

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
