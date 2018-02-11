package ke.co.noel.hao.models;

/**
 * Created by HP on 12/12/2017.
 */

public class BookingRequestModel  {
    String userName, email, userID, phoneNumber, roomType, stayLength, specialRequirements;

    public BookingRequestModel(String userName, String email, String userID, String phoneNumber, String roomType, String stayLength, String specialRequirements) {
        this.userName = userName;
        this.email = email;
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.roomType = roomType;
        this.stayLength = stayLength;
        this.specialRequirements = specialRequirements;
    }

    public BookingRequestModel() {
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getStayLength() {
        return stayLength;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }
}
