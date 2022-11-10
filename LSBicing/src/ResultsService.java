import com.google.gson.annotations.SerializedName;

/**
 * Created by Albert on 07/01/2017.
 */
public class ResultsService {
    @SerializedName("formatted_address")
    private String formattedAddress;
    private Geometry geometry;
    private String icon;
    private String id;
    private String name;
    @SerializedName("opening_hours")
    private OpeningHours openingHours;
    private Photos[] photos;
    @SerializedName("place_id")
    private String placeId;
    private String scope;
    @SerializedName("alt_ids")
    private AltIds altIds;
    @SerializedName("price_level")
    private int priceLevel;
    private float rating;
    private String reference;
    private String[] types;
    private String vicinity;

    public String getFormattedAddress(){
        return formattedAddress;
    }

    public String getName(){
        return name;
    }

    public OpeningHours getOpeningHours(){
        return openingHours;
    }

    public float getRating(){
        return rating;
    }
}
