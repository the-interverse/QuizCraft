package use_case.dashboard;

/**
 * The Input Data for the Login Use Case.
 */
public class LoggedInInputData {

    private final String username;
    private final String password;

    public LoggedInInputData(String username, String password) {
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
