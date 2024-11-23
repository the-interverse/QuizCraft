package ai_access;

import data_access.DBUserDataAccessObject;
import entity.Quiz;
import entity.QuizFactory;
import entity.QuizQuestion;
import entity.UserFactory;

public class CohereClientTest {
    private final static QuizFactory quizFactory = new QuizFactory();
    private final static DBUserDataAccessObject dataAccess = new DBUserDataAccessObject(new UserFactory());
    public static void main(String[] args) {
        // Example Prompt Material
        String courseMaterial = "ACT A: forecasting the future population of\n" +
                "Moscow\n" +
                "Introduction\n" +
                "In the system of national goals for the period up to 2030, the Government of the Russian\n" +
                "Federation is charged with ensuring sustainable population growth1. With Moscow being the\n" +
                "economic, cultural, and political center of the country, the interest in the dynamics of the\n" +
                "capital’s population significantly increases. In this regard, the forecast of the future population\n" +
                "of Moscow may be used as a benchmark for achieving national goals as well as utilized for\n" +
                "effective urban planning.\n" +
                "Mind map\n" +
                "Figure 1. Mind map of the factors that affect Moscow’s population.\n" +
                "Defining the problem statement\n" +
                "The goal of this project is to produce, based on population data and factors identified in the\n" +
                "mind map, a specific mathematical function that can predict the future population of Moscow\n" +
                "with little to no error.\n" +
                "1“Указ о национальных целях развития России до 2030 года”, 2020\n" +
                "1\n" +
                "Making assumptions\n" +
                "• There are no other factors affecting Moscow’s population, except for those outlined in\n" +
                "the mind map. This assumption would allow me to sharpen the focus of the study and\n" +
                "produce a more specific model.\n" +
                "• When considering external immigration, this research will focus only on immigrants\n" +
                "from the following countries: Kyrgyzstan, Uzbekistan, and Tajikistan. The reason is that\n" +
                "migrants from these states make up 77% of the overall migrant population in Moscow2.\n" +
                "• The quality of education will be measured by comparing the results of USE (Unified State\n" +
                "Examination) of Moscow students to other regions and estimating the yearly inflow of\n" +
                "students coming to study in Moscow.\n" +
                "Defining variables\n" +
                "• Demographics. Its two key aspects in Moscow are birth/death rates and immigration. As\n" +
                "Moscow is economically developed city, many people come from abroad to work there,\n" +
                "bringing their families with them, which significantly impacts the population of Moscow.\n" +
                "• Well-being. The ’Education’ variable mostly impacts youngsters as Moscow has the greatest\n" +
                "number of universities and schools in Russia, while ’Healthcare’ is more relevant for the\n" +
                "elderly population who may choose to move to Moscow to receive better treatment.\n" +
                "• Economics. This variable has the most significant impact on Moscow’s population as\n" +
                "continuous job creation attracts specialists from all over the country.\n" +
                "Building solutions\n" +
                "The method used in this study will be finding the relationship between the historical data of\n" +
                "Moscow’s population and the variables defined above. Namely, the idea is to determine how\n" +
                "suggested factors change over time to determine their impact on the population. Supposedly,\n" +
                "this approach would require technology and will rely on solving a few equations.\n" +
                "Conclusion\n" +
                "With all the details of the research being discussed, my next step would be collecting data\n" +
                "and building the model. My prediction is that Moscow’s population follows an upward-sloping\n" +
                "linear trend.\n" +
                "2Новости, Р. И. А. 2021\n" +
                "2\n";
        String quizTitle = "Earth Quiz";
        int numQuestions = 2;
        String difficulty = "easy";
        CohereAPI cohereAPI = new CohereAPI();
        String username = "kirill";
        String quizJSON = "";

        try {
            quizJSON = cohereAPI.callAPI(courseMaterial, quizTitle, numQuestions, difficulty);
            System.out.println("Generated Quiz JSON:");
            System.out.println(quizJSON);
        } catch (Exception e) {
            System.err.println("Error generating quiz: " + e.getMessage());
            e.printStackTrace();
        }

        Quiz quiz = quizFactory.create(quizJSON, difficulty);
        dataAccess.saveQuiz(quiz, username);


        // Sample output of Quiz object contents
        System.out.println("Quiz Name: " + quiz.getName());
        for (QuizQuestion question : quiz.getQuestions()) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("Answers: " + question.getAnswers());
            System.out.println("Correct Index: " + question.getCorrectIndex());
        }
        System.out.println("Difficulty: " + quiz.getDifficulty());
    }
}