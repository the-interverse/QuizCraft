package use_case.view_quiz;

/**
 * Output Data for the Login Use Case.
 */
public class ViewQuizOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public ViewQuizOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
