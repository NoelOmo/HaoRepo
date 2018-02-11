package ke.co.noel.hao.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 12/14/2017.
 */

public class NewUser {

    String userType, instanceID, userID;

    public NewUser(String userType, String instanceID, String userID) {
        this.userType = userType;
        this.instanceID = instanceID;
        this.userID = userID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userType", userType);
        result.put("userID", userID);
        result.put("instanceID", instanceID);

        return result;
    }

}
