package it.folicana.milo.ugo.service;

import it.folicana.milo.ugo.model.UserModel;

/**
 * Created by Milo on 16/04/2015.
 */
public final class LoginService {

    private static final String TAG_LOG = LoginService.class.getName();

    private static final String DUMMY_USERNAME = "user";
    private static final String DUMMY_PASSWORD = "password";

    private static LoginService instance;

    public synchronized static LoginService get(){
        if (instance == null){
            instance = new LoginService();

        }
        return instance;
    }

    public UserModel login(final String username, final String password){
        UserModel userModel = null;
        if(DUMMY_USERNAME.equalsIgnoreCase(username)&& DUMMY_PASSWORD.equalsIgnoreCase(password)) {
            userModel = UserModel.create(System.currentTimeMillis()).withEmail("dummy@daisy.com");
        }
        return userModel;
    }
}
