package entity;

import java.util.Map;

public class QuizQuestion {
    private String questionText;
    private Map<Character, String> options;
    private char correctAnswer;

    public QuizQuestion(String questionText, Map<Character, String> options, char correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(char answer) {
        return answer == correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Map<Character, String> getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}