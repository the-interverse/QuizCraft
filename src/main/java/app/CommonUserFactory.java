package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements entity.UserFactory {

    @Override
    public User create(String name, String password) {
        return new entity.CommonUser(name, password);
    }
}
