import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Albert on 05/01/2017.
 */
public class Ruta {
    @SerializedName("destination_adresses")
    private String[] destinationAdresses;
    @SerializedName("origin_adresses")
    private String[] originAdresses;
    private Rows[] rows;
    private String status;

    public String[] getDestinationAdresses(){
        return destinationAdresses;
    }

    public String[] getOriginAdresses(){
        return originAdresses;
    }

    public Rows[] getRows(){
        return rows;
    }

    public String getStatus(){
        return status;
    }

    public void llegirRuta(String origens, String destins, String mode){
        Gson gson = new Gson();
        try {
            URL oracle = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origens + "&destinations=" + destins + "&mode=" + mode + "&language=ca-CA&key=AIzaSyBUsL6-D0VFNzs2_lAs4rEff7GKKYU4hMQ");
            BufferedReader br = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String all = "";
            String input_line;
            while ((input_line = br.readLine()) != null)
                all += input_line;
            all += "\n";
            br.close();
            Ruta itinerary = gson.fromJson(all, Ruta.class);
            destinationAdresses = itinerary.destinationAdresses;
            originAdresses = itinerary.originAdresses;
            rows = itinerary.rows;
            status = itinerary.status;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
