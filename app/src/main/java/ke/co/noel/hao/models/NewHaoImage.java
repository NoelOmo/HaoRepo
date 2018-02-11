package ke.co.noel.hao.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 11/19/2017.
 */

@IgnoreExtraProperties
public class NewHaoImage {
    String imageOne, imageTwo, imageThree;
    ArrayList<String> images = new ArrayList<>();

    public NewHaoImage() {
    }

    public NewHaoImage(ArrayList<String> images) {
        this.images = images;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("images", images);

        return result;
    }
}
