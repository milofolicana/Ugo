package it.folicana.milo.ugo.model;

import android.text.TextUtils;

/**
 * Created by Milo on 15/04/2015.
 */
public class UserModel {

    private long mBirthDate;

    //creando un unico costruttore con visibilità private, impediamo di creare
    //istanze al di fuori della classe stessa
    private UserModel(final long birthDate){
        this.mBirthDate = birthDate;

    }

    public static UserModel create(final long birthDate){
        final UserModel userModel = new UserModel(birthDate);
        return userModel;
    }

    public boolean isAnonymous() {
        return TextUtils.isEmpty(this.mUsername);
    }

    public boolean isLogged() {
        return !TextUtils.isEmpty(this.mUsername);
    }
}
