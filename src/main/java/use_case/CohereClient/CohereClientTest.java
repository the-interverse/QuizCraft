package use_case.CohereClient;


import entity.Quiz;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CohereClientTest {
    public static void main(String[] args) {
        /*
        CohereClient client = new CohereClient("qmuC2WGV8FT5vbujGoQW7CV9W6p0ud0JyxTf7epa");

        try {
            String courseMaterial = "The Earth is the third planet from the Sun and the only astronomical object known to harbor life. Mars is the closest planet to Earth and got its name due to its red colour, after the Roman god of war Mars (known as Ares by the Greeks) ";
            String quizTitle = "Earth Quiz";
            int numQuestions = 3;
            String difficulty = "easy";

            // Create the prompt
            String prompt = client.createPrompt(courseMaterial, quizTitle, numQuestions, difficulty);
            System.out.println("Generated Prompt:");
            System.out.println(prompt);

            // Generate questions
            Quiz quiz = client.generateQuestions(prompt, 1000, quizTitle);

            // Convert quiz to JSON
            JSONObject quizJson = client.quizToJson(quiz);
            System.out.println("\nGenerated Quiz JSON:");
            System.out.println(quizJson.toString(2)); // Pretty print the JSON

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

         */

        CohereAPI cohereAPI = new CohereAPI("qmuC2WGV8FT5vbujGoQW7CV9W6p0ud0JyxTf7epa");

        // Example Prompt Material
        String courseMaterial = "The Earth is the third planet from the Sun and the only astronomical object known to harbor life. Mars is the closest planet to Earth and got its name due to its red colour, after the Roman god of war Mars (known as Ares by the Greeks) ";
        String quizTitle = "Earth Quiz";
        int numQuestions = 3;
        String difficulty = "easy";

        String prompt = cohereAPI.createPrompt(courseMaterial, quizTitle, numQuestions, difficulty);

        try {
            String quizJson = cohereAPI.callAPI(prompt);
            System.out.println("Generated Quiz JSON:");
            System.out.println(quizJson);
        } catch (Exception e) {
            System.err.println("Error generating quiz: " + e.getMessage());
            e.printStackTrace();
        }

    }
}