package use_case.createquiz;

import entity.Quiz;
import entity.QuizQuestion;
import use_case.create_quiz.CohereAPI;

public class CohereClientTest {
    public static void main(String[] args) {
        CohereAPI cohereAPI = new CohereAPI("qmuC2WGV8FT5vbujGoQW7CV9W6p0ud0JyxTf7epa");

        // Example Prompt Material
        String courseMaterial = "The Earth is the third planet from the Sun and the only astronomical object known to harbor life. Mars is the closest planet to Earth and got its name due to its red colour, after the Roman god of war Mars (known as Ares by the Greeks) ";
        String quizTitle = "Earth Quiz";
        int numQuestions = 3;
        String difficulty = "easy";

        String prompt = cohereAPI.createPrompt(courseMaterial, quizTitle, numQuestions, difficulty);

        String quizJSON = "";

        try {
            quizJSON = cohereAPI.callAPI(prompt);
            System.out.println("Generated Quiz JSON:");
            System.out.println(quizJSON);
        } catch (Exception e) {
            System.err.println("Error generating quiz: " + e.getMessage());
            e.printStackTrace();
        }

        Quiz quiz = CohereAPI.parseQuiz(quizJSON, difficulty);

        // Sample output of Quiz object contents
        System.out.println("Quiz Name: " + quiz.getName());
        for (QuizQuestion question : quiz.getQuestions()) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("Answers: " + question.getAnswers());
            System.out.println("Correct Index: " + question.getCorrectAnswer());
        }
        System.out.println("Difficulty: " + quiz.getDifficulty());
    }
}