package forms.formtemplates.SocialLogin;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CHANDRASAIMOHAN on 9/25/2016.
 */
public class SocialDTO implements Parcelable {
    private String profileName;
    private  String profileEmail;
     private  String profileImage;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Storing the Student data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileName);
        dest.writeString(profileEmail);
        dest.writeString(profileImage);
    }
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public SocialDTO(){
    }
    /**
     * Retrieving Social data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    public  SocialDTO(Parcel in){
        this.profileName = in.readString();
        this.profileEmail = in.readString();
        this.profileImage = in.readString();
    }

    public static final Parcelable.Creator<SocialDTO> CREATOR = new Parcelable.Creator<SocialDTO>() {

        @Override
        public SocialDTO createFromParcel(Parcel source) {
            return new SocialDTO(source);
        }

        @Override
        public SocialDTO[] newArray(int size) {
            return new SocialDTO[size];
        }
    };
}
