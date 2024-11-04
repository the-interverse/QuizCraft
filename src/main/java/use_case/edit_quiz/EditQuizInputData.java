package use_case.edit_quiz;

/**
 * The Input Data for the Login Use Case.
 */
public class EditQuizInputData {

    private final String username;
    private final String password;

    public EditQuizInputData(String username, String password) {
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
