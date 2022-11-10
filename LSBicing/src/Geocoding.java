import com.google.gson.Gson;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import static java.lang.Math.abs;

/**
 * Created by Marc on 03/01/2017.
 */
public class Geocoding {
    private Results[] results;
    private String status;

    public Results[] getResults(){
        return results;
    }

    public String getStatus(){
        return status;
    }

    public int trobaNearest(Bicing bicing){
        double value;
        int nearest = 0;
        double smallest = abs(results[0].getGeometry().getLocation().getLat() - bicing.getStations()[0].getLatitude()) + abs(results[0].getGeometry().getLocation().getLng() - bicing.getStations()[0].getLongitude());
        for(int i = 0; i < bicing.getStations().length; i++){
            value = abs(results[0].getGeometry().getLocation().getLat() - bicing.getStations()[i].getLatitude()) + abs(results[0].getGeometry().getLocation().getLng() - bicing.getStations()[i].getLongitude());
            if(value < smallest){
                smallest = value;
                nearest = i;
            }
        }
        return nearest;
    }

    public int trobaNearestFull(Bicing bicing){
        double value;
        int nearest = 0;
        double smallest = abs(results[0].getGeometry().getLocation().getLat() - bicing.getStations()[0].getLatitude()) + abs(results[0].getGeometry().getLocation().getLng() - bicing.getStations()[0].getLongitude());
        for(int i = 0; i < bicing.getStations().length; i++){
            value = abs(results[0].getGeometry().getLocation().getLat() - bicing.getStations()[i].getLatitude()) + abs(results[0].getGeometry().getLocation().getLng() - bicing.getStations()[i].getLongitude());
            if(value < smallest && bicing.getStations()[i].getBikes() > 0){
                smallest = value;
                nearest = i;
            }
        }
        if(nearest == 0 && bicing.getStations()[nearest].getBikes() == 0){
            System.out.println("No s'ha trobat cap estació de bicing amb bicicletes disponibles, disculpi les molèsties i torni-ho a probar més tard");
            nearest = -1;
        }
        return nearest;
    }

    public boolean llegirGeocoding (String place_b){
        Gson gson = new Gson();
        try {
            URL oracle = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + place_b + "&key=AIzaSyBUsL6-D0VFNzs2_lAs4rEff7GKKYU4hMQ");
            BufferedReader br = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String all = "";
            String input_line;
            while ((input_line = br.readLine()) != null)
                all += input_line;
            all += "\n";
            br.close();
            Geocoding q = gson.fromJson(all, Geocoding.class);
            if(q.status.equals("OK")){
                results = q.results;
                status = q.status;
                return true;
            }else{
                System.out.println("No s'han trobat resultats per aquesta ubicació.\n");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static void visualitzaLocalitzacio(String place_b){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.google.es/maps/place/" + place_b));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void visualitzaRuta(String place_b){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.google.es/maps/dir/" + place_b + "/data=!4m2!4m1!3e1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
