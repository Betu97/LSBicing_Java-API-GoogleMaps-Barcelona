/**
 * Created by Albert on 08/01/2017.
 */
public class RutaRanking {
    private String origen;
    private String desti;
    private String biciOrigen;
    private String biciDesti;
    private float distancia;

    public RutaRanking(){
        distancia = -1;
    }

    public String getOrigen(){
        return origen;
    }

    public String getDesti(){
        return desti;
    }

    public String getBiciOrigen(){
        return biciOrigen;
    }

    public String getBiciDesti(){
        return biciDesti;
    }

    public float getDistancia(){
        return distancia;
    }

    public void add(Geocoding origen, Geocoding desti, Stations a, Stations b, long metres){
        if ((metres/1000f) >= distancia){
            this.origen = origen.getResults()[0].getFormattedAddress();
            this.desti = desti.getResults()[0].getFormattedAddress();
            this.biciOrigen = a.getStreetName() + ", " + a.getStreetNumber() + ", Barcelona";
            this.biciDesti = b.getStreetName() + ", " + b.getStreetNumber() + ", Barcelona";
            this.distancia = metres/1000f;
        }
    }
}
