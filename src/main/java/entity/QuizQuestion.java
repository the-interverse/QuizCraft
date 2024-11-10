package entity;

import java.util.List;
import java.util.Map;

public class QuizQuestion {
    private String question;
    private List<String> answers;
    private int correctIndex;

    public QuizQuestion(String question, List<String> answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public boolean isCorrect(int index) { return index == correctIndex; }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctIndex;
    }
}