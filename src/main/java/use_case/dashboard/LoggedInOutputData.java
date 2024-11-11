package use_case.dashboard;

/**
 * Output Data for the Login Use Case.
 */
public class LoggedInOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public LoggedInOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
