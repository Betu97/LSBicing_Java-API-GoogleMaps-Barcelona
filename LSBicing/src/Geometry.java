import com.google.gson.annotations.SerializedName;

/**
 * Created by Marc on 03/01/2017.
 */
public class Geometry {
    private Viewport bounds;
    private Location location;
    @SerializedName("location_type")
    private String locationType;
    private Viewport viewport;

    public Location getLocation(){
        return location;
    }
}
