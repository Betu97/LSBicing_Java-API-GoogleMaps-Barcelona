import java.util.LinkedList;

/**
 * Created by Albert on 08/01/2017.
 */
public class Ranking {
    private LinkedList<LlocsRanking> llocs;

    public Ranking (){
        llocs = new LinkedList <LlocsRanking>();
    }

    public void addTerm (String adreça, double latitud, double longitud, boolean esBicing, String tipus){
        LlocsRanking aux;
        LlocsRanking eux;
        aux = new LlocsRanking(adreça, latitud, longitud, esBicing, tipus, 1);
        boolean found = false;
        for (int i = 0; i < llocs.size() ; i++) {
            eux = llocs.get(i);
            if(eux.getAdreça().equals(aux.getAdreça())){
                found = true;
                eux.setFrequencia(eux.getFrequencia() + 1);
                llocs.remove(i);
                llocs.add(eux);
            }
        }
        if(!found) {
            llocs.add(aux);
        }
    }

    public void addOpcio3(Stations a, Stations b){
        addTerm((a.getStreetName().replaceAll("\\/", "") + ", " + a.getStreetNumber() + ", Barcelona"), a.getLatitude(), a.getLongitude(), true, a.getType());
        addTerm((b.getStreetName().replaceAll("\\/", "") + ", " + b.getStreetNumber() + ", Barcelona"), b.getLatitude(), b.getLongitude(), true, b.getType());
    }

    public void addOpcio5(String adreça){
        Geocoding q = new Geocoding();
        q.llegirGeocoding(adreça.replaceAll(" " , "+"));
        addTerm(adreça, q.getResults()[0].getGeometry().getLocation().getLat(), q.getResults()[0].getGeometry().getLocation().getLng(), false, "");
    }

    public void mostraRanking (RutaRanking ruta_ranking){
        LlocsRanking aux;
        float gran;
        int size = llocs.size();
        int i;
        int [] grans = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        boolean es = false;
        System.out.println("Llocs més emprats:");
        if (!llocs.isEmpty()) {
            for(i = 0; i < 10; i++){
                gran = 0.0f;
                for(int j = 0; j < size; j++){
                    aux = llocs.get(j);
                    for(int k = 0; k < 10; k++){
                        if(j == grans[k]){
                            es = true;
                        }
                    }
                    if(aux.getFrequencia() > gran && !es){
                        gran = aux.getFrequencia();
                        grans[i] = j;
                    }
                    es = false;
                }
            }
            i = 0;
            while(i < 10 && grans[i] != -1){
                llocs.get(grans[i]).printa(i);
                i++;
            }
            if (i != 10){
                System.out.println("\nS'han de fer més cerques per completar el rànquing\n");
            }
        }else{
            System.out.println("\nS'han de fer més cerques per completar el rànquing\n");
        }
        System.out.println("Ruta més llarga:");
        if(ruta_ranking.getDistancia() != -1){
            System.out.println("Direcció de l'origen: " +ruta_ranking.getOrigen() + "\nDirecció del destí: " + ruta_ranking.getDesti()+ "\nDirecció de l'estació d'origen: " + ruta_ranking.getBiciOrigen() + "\nDirecció de l'estació destí: " + ruta_ranking.getBiciDesti() + "\nDistància de la ruta: " + ruta_ranking.getDistancia() + " Kilómetres\n");
        }else{
            System.out.println("Com a mínim s'ha de crear una ruta per poder completar el rànquing de la ruta més llarga\n");
        }
    }
}
