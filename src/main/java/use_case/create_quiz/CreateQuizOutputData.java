package use_case.create_quiz;

/**
 * Output Data for the Login Use Case.
 */
public class CreateQuizOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public CreateQuizOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
