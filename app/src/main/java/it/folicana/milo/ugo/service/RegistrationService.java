package it.folicana.milo.ugo.service;

import it.folicana.milo.ugo.model.UserModel;


public final class RegistrationService {

    /**
     * The Tag for the log
     */
    private static final String TAG_LOG = RegistrationService.class.getName();

    /**
     * The Singleton instance
     */
    private static RegistrationService instance;

    /**
     * @return The singleton instance of the LoginService
     */
    public synchronized static RegistrationService get() {
        if (instance == null) {
            instance = new RegistrationService();
        }
        return instance;
    }

    /**
     * This registration implementation that creates the UserModel data.
     *
     * @param username  The username
     * @param password  The password
     * @param email     The email address
     * @param birthDate The birthDate as long
     * @param location  The location as long
     * @return The UserModel with user data if logged and null if not
     */
    public UserModel register(final String username, final String password, final String email,
                              final long birthDate, final String location) {
        // In this implementation we don't save the password but we'll send it
        // to the server for the real registration.
        UserModel userModel = UserModel.create(birthDate).withUsername(username)
                .withLocation(location).withEmail(email);
        // We return the model if any
        return userModel;
    }




}
