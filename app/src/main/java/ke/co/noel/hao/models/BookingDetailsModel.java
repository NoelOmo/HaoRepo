package ke.co.noel.hao.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 11/28/2017.
 */

@IgnoreExtraProperties
public class BookingDetailsModel {
    String userName, email, userID, phoneNumber, roomType, stayLength, specialRequirements;

    public BookingDetailsModel(String userName, String email, String userID, String phoneNumber, String roomType, String stayLength, String specialRequirements) {
        this.userName = userName;
        this.email = email;
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.roomType = roomType;
        this.stayLength = stayLength;
        this.specialRequirements = specialRequirements;
    }

    public BookingDetailsModel() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("email", email);
        result.put("userID", userID);
        result.put("phoneNumber", phoneNumber);
        result.put("roomType", roomType);
        result.put("stayLength", stayLength);
        result.put("specialRequirements", specialRequirements);

        return result;
    }
}
