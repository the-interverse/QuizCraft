package entity;

import com.google.gson.annotations.SerializedName;
import interface_adapter.dashboard.DashboardState;

import java.util.List;

public class Quiz extends DashboardState.QuizDisplayData {
    @SerializedName("Quiz Name")
    private String name;

    @SerializedName("Quiz Questions")
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