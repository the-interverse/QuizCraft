package use_case.dashboard;

/**
 * The Input Data for the Login Use Case.
 */
public class DashboardInputData {

    private final String username;
    private final String password;

    public DashboardInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
