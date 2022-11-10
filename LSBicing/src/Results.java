import com.google.gson.annotations.SerializedName;

/**
 * Created by Marc on 03/01/2017.
 */
public class Results {
    @SerializedName("address_components")
    private Components[] addressComponents;
    @SerializedName("formatted_address")
    private String formattedAddress;
    private Geometry geometry;
    @SerializedName("partial_match")
    private boolean partialMatch;
    @SerializedName("place_id")
    private String placeId;
    private String[] types;

    public String getFormattedAddress(){
        return formattedAddress;
    }

    public Geometry getGeometry(){
        return geometry;
    }
}
