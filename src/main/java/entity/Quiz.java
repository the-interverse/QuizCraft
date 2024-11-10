package entity;

import java.util.List;

public class Quiz {
    private String name;
    private List<QuizQuestion> questions;
    private String difficulty;

    public Quiz(String name, List<QuizQuestion> questions, String difficulty) {
        this.name = name;
        this.questions = questions;
        this.difficulty = difficulty;
    }

    public Quiz(String name, List<QuizQuestion> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public String getDifficulty() {
        return difficulty;
    }
}