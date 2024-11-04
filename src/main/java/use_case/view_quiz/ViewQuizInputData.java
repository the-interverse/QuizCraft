package use_case.view_quiz;

/**
 * The Input Data for the Login Use Case.
 */
public class ViewQuizInputData {

    private final String username;
    private final String password;

    public ViewQuizInputData(String username, String password) {
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
