package use_case.dashboard;

/**
 * Output Data for the Login Use Case.
 */
public class DashboardOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public DashboardOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

}
