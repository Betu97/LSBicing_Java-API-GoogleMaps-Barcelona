import com.google.gson.annotations.SerializedName;

/**
 * Created by Albert on 07/01/2017.
 */
public class OpeningHours {
    @SerializedName("open_now")
    private boolean openNow;
    @SerializedName("weekday_text")
    private String[] weekdayText;

    public boolean isOpenNow(){
        return openNow;
    }
}
