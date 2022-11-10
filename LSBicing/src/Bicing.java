import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import static java.lang.Math.abs;


/**
 * Created by Albert on 04/01/2017.
 */
public class Bicing {
    private Stations[] stations;
    private long updateTime;

    public Stations[] getStations(){
        return stations;
    }

    public void llegirBicing (){
        Gson gson = new Gson();
        try {
            URL oracle = new URL("http://wservice.viabicing.cat/v2/stations");
            BufferedReader br = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String all = "";
            String input_line;
            while ((input_line = br.readLine()) != null)
                all += input_line;
            all += "\n";
            br.close();
            Bicing bicing = gson.fromJson(all, Bicing.class);
            stations = bicing.stations;
            updateTime = bicing.updateTime;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostraEstacionsLlunyanes(){
        int esquerra = 0;
        int dreta = 0;
        double distancia = 0;
        double distancia_aux = 0;
        Ruta ruta = new Ruta();
        System.out.println("Calculant distàncies...");
        for (int i = 0; i < stations.length; i++){
            for (int j = i; j < stations.length; j++){
                distancia_aux = abs(stations[i].getLatitude() - stations[j].getLatitude()) + abs(stations[i].getLongitude() - stations[j].getLongitude());
                if (distancia < distancia_aux){
                    esquerra = i;
                    dreta = j;
                    distancia = distancia_aux;
                }
            }
        }
        ruta.llegirRuta((stations[esquerra].getStreetName().replaceAll("\\/", "").replaceAll(" " , "+") + ",+" + stations[esquerra].getStreetNumber() + ",+Barcelona") , (stations[dreta].getStreetName().replaceAll("\\/", "").replaceAll(" " , "+") + ",+" + stations[dreta].getStreetNumber() + ",+Barcelona") , "walking");
        System.out.println("id estació A: " + stations[esquerra].getId() + "\nid estació B: " +  stations[dreta].getId() + "\n\nadreça estació A: " + stations[esquerra].getStreetName() + ", " + stations[esquerra].getStreetNumber());
        System.out.println("adreça estació B: " + stations[dreta].getStreetName() + ", " + stations[dreta].getStreetNumber() + "\n\ndistància entre estacions: " + ruta.getRows()[0].getElements()[0].getDistance().getValue() + " metres\n");
        System.out.println("tipus d'estació A: " + stations[esquerra].getType() + "\ntipus d'estació B: " + stations[dreta].getType() + "\n\nmàxim de bicicletes estació A: " + (stations[esquerra].getBikes() + stations[esquerra].getSlots()));
        System.out.println("màxim de bicicletes estació B: " + (stations[dreta].getBikes() + stations[dreta].getSlots()) + "\n");
    }

    public String trobaEstacions(int disponibles){
        String place = "+";
        int trobades = 0;
        for(int i = 0; i < stations.length; i++){
            if(stations[i].getBikes() >= disponibles){
                place += "&markers=%7C" + (Math.round(stations[i].getLatitude() * 1000d) / 1000d) + "," + (Math.round(stations[i].getLongitude() * 10000d) / 10000d);
                trobades++;
            }
        }
        if(trobades != 0 && trobades < 395 && place.length() < 8000) {
            System.out.println("\nCarregant el mapa amb " + trobades + " estacions...\n");
        }else if(trobades == 0){
            System.out.println("\nNo s'han trobat estacions amb el mínim de bicicletes disponibles\n");
        }
        if(trobades > 395 || place.length() > 8191){
            System.out.println("\nS'han trobat masses resultats (Or URL Bigger than 8192)\n");
            place = "+";
        }
        return place;
    }

    public static void visualitzaImatge(String place_b){
        Image image = null;
        Gson gson = new Gson();
        try {
            URL oracle = new URL("https://maps.googleapis.com/maps/api/staticmap?size=500x550&maptype=roadmap"+ place_b + "&key=AIzaSyBUsL6-D0VFNzs2_lAs4rEff7GKKYU4hMQ");
            image = ImageIO.read(oracle);
        } catch (Exception e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame();

        JLabel lblimage = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(lblimage, BorderLayout.CENTER);
        frame.setSize(525, 590);
        frame.setVisible(true);
    }

}
