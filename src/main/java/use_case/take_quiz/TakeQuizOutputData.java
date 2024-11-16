package use_case.take_quiz;


import java.util.Map;

/**
 * Output data for Take Quiz use case
 */
public class TakeQuizOutputData {
    private final boolean useCaseFailed;
    private final Map<Integer, String> feedback;
    private final Integer totalCorrect;
    private final Integer totalPercent;

    public TakeQuizOutputData(boolean useCaseFailed, Map<Integer, String> feedback, Integer totalCorrect, Integer totalPercent) {
        this.useCaseFailed = useCaseFailed;
        this.feedback = feedback;
        this.totalCorrect = totalCorrect;
        this.totalPercent = totalPercent;
    }

    public Integer getTotalCorrect() {
        return totalCorrect;
    }

    public Integer getTotalPercent() {
        return totalPercent;
    }

    public Map<Integer, String> getFeedback() {
        return feedback;
    }
}
