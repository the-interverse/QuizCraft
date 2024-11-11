package entity;

import java.util.List;
import java.util.Map;

public class QuizQuestion {
    private String questionText;
    private List<String> answers;
    private char correctAnswer;

    public QuizQuestion(String questionText, List<String> answers, char correctAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return answer.equals(correctAnswer);
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}