package ke.co.noel.hao.models;

/**
 * Created by HP on 12/12/2017.
 */

public class AmenitiesModel {

    String name;
    int icon;
    boolean selected;

    public AmenitiesModel(String name, int icon, boolean selected) {
        this.name = name;
        this.icon = icon;
        this.selected = selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isSelected() {
        return selected;
    }
}
