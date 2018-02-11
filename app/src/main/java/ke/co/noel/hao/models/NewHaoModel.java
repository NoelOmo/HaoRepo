package ke.co.noel.hao.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 11/19/2017.
 */

@IgnoreExtraProperties
public class NewHaoModel {

    public String name, location, description, price, uid;
    String longitude, latitude, photo;
    ArrayList<String> images = new ArrayList<>(), amenities = new ArrayList<>();

    public NewHaoModel(ArrayList<String> images) {
        this.images = images;
    }

    public NewHaoModel() {
    }

    public NewHaoModel(String name, String location, String description, String price, String uid, String longitude, String latitude, ArrayList<String> images, ArrayList<String> amenities) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.amenities = amenities;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("location", location);
        result.put("description", description);
        result.put("price", price);
        result.put("longitude", longitude);
        result.put("latitude", latitude);
        result.put("images", images);
        result.put("amenities", amenities);

        return result;
    }

    @Exclude
    public Map<String, Object> toMap2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("images", images);

        return result;
    }
}
