package ke.co.noel.hao.models;

/**
 * Created by root on 10/1/17.
 */

public class HaoModel {

    String name, location, description, price, uid;
    String longitude, latitude, photo;

    public HaoModel() {
    }

    public HaoModel(String name, String location, String description, String price, String longitude, String latitude, String uid, String photo) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
        this.longitude = longitude;
        this.latitude = latitude;
        this.uid = uid;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getUid() {
        return uid;
    }

}
