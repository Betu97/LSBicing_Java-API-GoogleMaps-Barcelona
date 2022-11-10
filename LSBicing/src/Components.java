import com.google.gson.annotations.SerializedName;

/**
 * Created by Marc on 03/01/2017.
 */
public class Components {
    @SerializedName("long_name")
    private String longName;
    @SerializedName("short_name")
    private String shortName;
    private String[] types;
}
