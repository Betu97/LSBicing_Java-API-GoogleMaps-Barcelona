/**
 * Created by Albert on 08/01/2017.
 */
public class LlocsRanking {
    private String adreça;
    private double latitud;
    private double longitud;
    private boolean esBicing;
    private String tipus;
    private int frequencia;

    public LlocsRanking(String adreça, double latitud, double longitud, boolean esBicing, String tipus, int frequencia){
        this.adreça = adreça;
        this.latitud = latitud;
        this.longitud = longitud;
        this.esBicing = esBicing;
        this.tipus = tipus;
        this.frequencia = frequencia;
    }

    public String getAdreça(){
        return adreça;
    }

    public int getFrequencia(){
        return frequencia;
    }

    public void setFrequencia(int frequencia){
        this.frequencia = frequencia;
    }

    public void printa(int pos){
        System.out.println("\nPosició " + (pos + 1) + ":\nAdreça: " + adreça + "\nLatitud: " + latitud + "\nLongitud: " + longitud);
        if(esBicing){
            System.out.println("És una estació de Bicing\nÉs una estació del tipus: " + tipus);
        }else{
            System.out.println("No és una estació de Bicing");
        }
    }
}
