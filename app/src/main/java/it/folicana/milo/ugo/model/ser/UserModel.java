package it.folicana.milo.ugo.model.ser;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Milo on 16/04/2015.
 */
public class UserModel implements Serializable {

    private long mBirthDate;
    private String mUsername;
    private String mEmail;
    private String mLocation;

    //creando un unico costruttore con visibilità private, impediamo di creare
    //istanze al di fuori della classe stessa
    private UserModel(final long birthDate){
        this.mBirthDate = birthDate;

    }

    public static UserModel create(final long birthDate){
        final UserModel userModel = new UserModel(birthDate);
        return userModel;
    }

    public UserModel withEmail(final String email){
        if (email==null){
            throw new IllegalArgumentException("Email cannot be null here!");
        }
        this.mEmail = email;
        return this;
    }

    public UserModel withUsername(final String username){
        if (username==null){
            throw new IllegalArgumentException("Username cannot be null here!");
        }
        this.mUsername = username;
        return this;
    }

    public UserModel withLocation(final String location){
        if (location==null){
            throw new IllegalArgumentException("Location cannot be null here!");
        }
        this.mLocation = location;
        return this;
    }

    public String getUsername() {
        return mUsername;
    }

    public long getBirthday() {
        return this.mBirthDate;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getLocation() {
        return this.mLocation;
    }

    public boolean isAnonymous() {
        return TextUtils.isEmpty(this.mUsername);
    }

    public boolean isLogged() {
        return !TextUtils.isEmpty(this.mUsername);
    }
}
