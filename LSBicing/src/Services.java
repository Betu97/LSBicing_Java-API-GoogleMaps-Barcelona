import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Albert on 07/01/2017.
 */
public class Services {
    @SerializedName("html_attributions")
    private String[] htmlAttributions;
    @SerializedName("next_page_token")
    private String nextPageToken;
    private ResultsService[] results;
    private String status;

    public ResultsService[] getResults(){
        return results;
    }

    public String getStatus(){
        return status;
    }

    public int mostraPerGuardar(){
        if(results.length > 5){
            System.out.println("S'ha trobat 5 resultats:");
        }else{
            System.out.println("S'ha trobat " + results.length + " resultats:");
        }
        int i = 0;
        Scanner SC = new Scanner(System.in);
        while (i < results.length && i < 5){
            System.out.println("Lloc: " + results[i].getName() + "\n-----------------------------------------------------------------");
            System.out.println("Adreça: " + results[i].getFormattedAddress());
            try{
                if (results[i].getOpeningHours().isOpenNow()) {
                    System.out.println("Actualment obert");
                }else{
                    System.out.println("Actualment tancat");
                }
            }catch(Exception e){
                System.out.println("Actualment desconegut");
            }
            System.out.println("Rating del lloc: " + results[i].getRating());
            System.out.println("Vols guardar-lo en el fitxer favorite_places.json? (Si/No)");
            String opcio = SC.nextLine();
            while (!opcio.equalsIgnoreCase("si") && !opcio.equalsIgnoreCase("no")){
                System.out.println("Opció incorrecte");
                System.out.println("Vols guardar-lo en el fitxer favorite_places.json? (Si/No)");
                opcio = SC.nextLine();
            }
            if (opcio.equalsIgnoreCase("si")) {
                return i;
            }
            i++;
        }
        i = -1;
        return i;
    }
    public boolean llegirServices (String place_b){
        Gson gson = new Gson();
        try {
            URL oracle = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + place_b + "+Barcelona&key=AIzaSyBUsL6-D0VFNzs2_lAs4rEff7GKKYU4hMQ");
            BufferedReader br = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String all = "";
            String input_line;
            while ((input_line = br.readLine()) != null)
                all += input_line;
            all += "\n";
            br.close();
            Services s = gson.fromJson(all, Services.class);
            if(s.status.equals("OK")){
                htmlAttributions = s.htmlAttributions;
                nextPageToken = s.nextPageToken;
                status = s.status;
                results = s.results;
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
}

