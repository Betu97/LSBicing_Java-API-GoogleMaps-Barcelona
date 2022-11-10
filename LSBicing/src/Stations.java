/**
 * Created by Albert on 04/01/2017.
 */
public class Stations {
    private int id;
    private String type;
    private double latitude;
    private double longitude;
    private String streetName;
    private String streetNumber;
    private int altitude;
    private int slots;
    private int bikes;
    private String nearbyStations;
    private String status;

    public int getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getStreetName(){
        return streetName;
    }

    public String getStreetNumber(){
        return streetNumber;
    }

    public int getSlots(){
        return slots;
    }

    public int getBikes(){
        return bikes;
    }

    public String getNearbyStations(){
        return nearbyStations;
    }

    public String mostraLocalitzacio() {
        String frase = streetName + ", nº" + streetNumber + "\nAmb un total de " + bikes + " de " + (slots + bikes) + " bicicletes disponibles\n";
        return frase;
    }

    public int[] revelaNearby(){
        int a = 0;
        int j = 0;
        String travel = "";
        for (int i = 0; i < nearbyStations.length(); i++){
            if(nearbyStations.charAt(i) == ','){
                a++;
            }
        }
        int[] nearby = new int[a + 2];
        for (int i = 0; i < nearbyStations.length(); i++){
            while(nearbyStations.charAt(i) != ',' && i < (nearbyStations.length() - 1)) {
                travel += nearbyStations.charAt(i);
                i++;
            }
            if(j == (a)){
                travel += nearbyStations.charAt(i);
                nearby[j] = Integer.parseInt(travel);
            }else{
                nearby[j] = Integer.parseInt(travel);
            }
            i++;
            travel = "";
            j++;
        }
        nearby[a+1] = (a + 1);
        return nearby;
    }

}
