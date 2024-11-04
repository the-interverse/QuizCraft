package use_case.edit_quiz;

/**
 * Output Data for the Login Use Case.
 */
public class EditQuizOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public EditQuizOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
