import com.google.gson.annotations.SerializedName;

/**
 * Created by Albert on 07/01/2017.
 */
public class Photos {
    private int height;
    @SerializedName("html_attributions")
    private String[] htmlAttributions;
    @SerializedName("photo_reference")
    private String photoReference;
    private int width;
}
