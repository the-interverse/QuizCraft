package use_case.create_quiz;

/**
 * The Input Data for the Login Use Case.
 */
public class CreateQuizInputData {

    private final String username;
    private final String password;

    public CreateQuizInputData(String username, String password) {
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
