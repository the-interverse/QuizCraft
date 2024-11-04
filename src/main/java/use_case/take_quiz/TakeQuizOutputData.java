package use_case.take_quiz;

/**
 * Output Data for the Login Use Case.
 */
public class TakeQuizOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public TakeQuizOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
