/**
 * Created by Albert on 29/12/2016.
 */
/* Importem la llibreria de LinkedList*/
import com.google.gson.Gson;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.*;

public class Fitxers {
    private LinkedList <Favorite> favorites;

    public Fitxers (){
        favorites = new LinkedList <Favorite>();
    }

    public void addTerm (String nom, String adreça){
        Favorite aux;
        Favorite eux;
        aux = new Favorite(nom, adreça);
        boolean found = false;
        for (int i = 0; i < favorites.size() ; i++) {
            eux = favorites.get(i);
            if(eux.getAdreça().equals(aux.getAdreça())){
                found = true;
            }
        }
        if(!found) {
            favorites.add(aux);
        }
    }

    public boolean mostraFitxer(){
        Scanner SC = new Scanner(System.in);
        System.out.println("\n------- Llocs preferits -------");
        for (int i = 0; i < favorites.size(); i++){
            System.out.println((i + 1) + ". " + favorites.get(i).getNom() + " (" + favorites.get(i).getAdreça() + ")");
        }
        System.out.print("\nSelecciona un dels possibles " + favorites.size() + " llocs per visualitzar: ");
        try {
            int opcio = SC.nextInt();
            while (opcio > favorites.size() || opcio < 0){
                System.out.print("La opció escollida no existeix\nSelecciona un dels possibles " + favorites.size() + " llocs per visualitzar: ");
                opcio = SC.nextInt();
            }
            System.out.println("Generant mapa de " + favorites.get(opcio - 1).getNom() + "...\n");
            visualitzaOpcio(favorites.get(opcio - 1).getAdreça().replaceAll(" " , "+"));
        }catch (InputMismatchException e){
            System.out.println("Un caràcter no és una opció possible\n");
            return false;
        }
        return true;
    }

    public int sizeList(){
        return favorites.size();

    }

    public void combinaLlista(Fitxers q) {
        for (int i = 0; i < q.sizeList(); i++) {
            favorites.add(q.favorites.get(i));
        }
    }

    public static void visualitzaOpcio(String place_b){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.google.es/maps/place/" + place_b));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean carregaFitxer(){
        Gson gson = new Gson();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\BetuFolder\\uni\\2nAny\\DPO\\Practiques\\favorite_places.json"));
            Fitxers q = gson.fromJson(br, Fitxers.class);
            combinaLlista(q);
            return false;
        }catch (FileNotFoundException e){
            System.out.println("El fitxer favorite_places.json no s'ha pogut trobar\nEl programa es tancarà...\n\n");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean guardaFitxer() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try {
            FileWriter writer = new FileWriter("C:\\Users\\BetuFolder\\uni\\2nAny\\DPO\\Practiques\\favorite_places.json");
            writer.write(json);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
