package it.folicana.milo.ugo.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Created by Milo on 15/04/2015.
 */
public class UserModel implements Parcelable {

    private static final byte PRESENT = 1;
    private static final byte NOT_PRESENT = 0;

    private long mBirthdate;
    private String mUsername;
    private String mEmail;
    private String mLocation;

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>(){
        public UserModel createFromParcel(Parcel in){
            return new UserModel(in);
        }

        public UserModel[] newArray(int size){
            return new UserModel[size];
        }
    };

    public UserModel(Parcel in){
        this.mBirthdate = in.readLong();
        if(in.readByte() == PRESENT) {
            this.mUsername = in.readString();
        }
        if(in.readByte()== PRESENT){
            this.mEmail = in.readString();
        }
        if(in.readByte()== PRESENT){
            this.mLocation = in.readString();
        }
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(mBirthdate);
        if(!TextUtils.isEmpty(mUsername)){
            dest.writeByte(PRESENT);
            dest.writeString(mUsername);
        }else{
            dest.writeByte((NOT_PRESENT));
        }

        if(!TextUtils.isEmpty(mEmail)){
            dest.writeByte(PRESENT);
            dest.writeString(mEmail);
        }else{
            dest.writeByte((NOT_PRESENT));
        }

        if(!TextUtils.isEmpty(mLocation)){
            dest.writeByte(PRESENT);
            dest.writeString(mLocation);
        }else{
            dest.writeByte((NOT_PRESENT));
        }
    }

    //creando un unico costruttore con visibilità private, impediamo di creare
    //istanze al di fuori della classe stessa
    private UserModel(final long birthdate){
        this.mBirthdate = birthdate;

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
        return this.mBirthdate;
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
