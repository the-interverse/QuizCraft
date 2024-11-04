package use_case.take_quiz;

/**
 * The Input Data for the Login Use Case.
 */
public class TakeQuizInputData {

    private final String username;
    private final String password;

    public TakeQuizInputData(String username, String password) {
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
