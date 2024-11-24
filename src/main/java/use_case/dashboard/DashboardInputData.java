package use_case.dashboard;

/**
 * The Input Data for the Dashboard Use Case.
 */
public class DashboardInputData {

    private final String username;

    public DashboardInputData(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }


}
