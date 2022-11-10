/**
 * Created by Albert on 29/12/2016.
 */
/*Afegim la llibreira del scanner*/
import java.awt.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import java.net.*;

public class main {
    public static void main(String[] args){
        Fitxers fitxer = new Fitxers();
        Ranking ranking = new Ranking();
        RutaRanking ruta_ranking = new RutaRanking();
        Scanner SC = new Scanner(System.in);
        int opcio = 0;
        boolean errorFitxer = fitxer.carregaFitxer();
        if(errorFitxer == false) {
            System.out.println("Dades carregades correctament. Benvingut a LSBicing!\n\n\n");
            System.out.println("************ LSBicing menu ************\n1. Geolocalització\n2. Buscar estació de Bicing més propera\n3. Creació d'una ruta\n4. Visualització d'estacions de Bicing\n5. Cerca d'ubicacions\n6. Visualitzar ubicació guardada\n7. Estacions més llunyanes\n8. Rànquing\n9. Sortir\n");
            System.out.print("Introdueix una opció: ");
            try {
                opcio = SC.nextInt();
                while (opcio != 9) {
                    if (opcio == 1) {
                        String place_b;
                        System.out.print("Introdueix una ubicació: ");
                        SC.nextLine();
                        place_b = SC.nextLine().replaceAll(" ", "+");
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
                            if (q.getStatus().equals("OK")) {
                                System.out.println("Carregant Google Maps...");
                                if (Desktop.isDesktopSupported()) {
                                    try {
                                        Desktop.getDesktop().browse(new URI("https://www.google.es/maps/place/" + place_b));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                System.out.println("No s'han trobat resultats per aquesta ubicació.");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.print("\n");
                    } else if (opcio == 2) {
                        String place;
                        String place_b;
                        System.out.print("Introdueix una ubicació: ");
                        SC.nextLine();
                        place_b = SC.nextLine().replaceAll(" ", "+");
                        int id = 0;
                        Bicing bicing = new Bicing();
                        bicing.llegirBicing();
                        Geocoding q = new Geocoding();
                        boolean state = q.llegirGeocoding(place_b);
                        if (state) {
                            System.out.print("L'estació de bicing més propera es troba a: ");
                            int nearest = q.trobaNearest(bicing);
                            System.out.println(bicing.getStations()[nearest].getStreetName() + ", nº" + bicing.getStations()[nearest].getStreetNumber() + "\namb un total de " + bicing.getStations()[nearest].getBikes() + " de " + (bicing.getStations()[nearest].getSlots() + bicing.getStations()[nearest].getBikes()) + " bicicletes disponibles\n");
                            int[] nearby_stations = bicing.getStations()[nearest].revelaNearby();
                            System.out.println("Les estacions més properes a aquesta són " + nearby_stations[nearby_stations.length - 1] + ":\n-----------------------------------------------");
                            for (int i = 0; i < nearby_stations[nearby_stations.length - 1]; i++) {
                                for (int j = 0; j < bicing.getStations().length; j++) {
                                    if (bicing.getStations()[j].getId() == nearby_stations[i] - 1) {
                                        id = j;
                                    }
                                }
                                System.out.print("\n" + (i + 1) + ". " + bicing.getStations()[id].mostraLocalitzacio());
                            }
                            place = bicing.getStations()[nearest].getStreetName() + ", " + bicing.getStations()[nearest].getStreetNumber() + ", Barcelona";
                            place_b = place.replaceAll("\\/", "").replaceAll(" ", "+");
                            q.visualitzaLocalitzacio(place_b);
                        }
                        System.out.print("\n");
                    } else if (opcio == 3) {
                        String place;
                        String place_a;
                        String place_b;
                        String place_c;
                        String place_d;
                        System.out.print("Introdueix una primera ubicació: ");
                        SC.nextLine();
                        place = SC.nextLine();
                        place_a = place.replaceAll(" ", "+");
                        System.out.print("Introdueix una segona ubicació: ");
                        place = SC.nextLine();
                        place_d = place.replaceAll(" ", "+");
                        Bicing bicing = new Bicing();
                        bicing.llegirBicing();
                        Geocoding origen = new Geocoding();
                        boolean origen_ok = origen.llegirGeocoding(place_a);
                        Geocoding desti = new Geocoding();
                        boolean desti_ok = desti.llegirGeocoding(place_d);
                        if (origen_ok && desti_ok) {
                            place_a = origen.getResults()[0].getFormattedAddress().replaceAll(" ", "+");
                            place_d = desti.getResults()[0].getFormattedAddress().replaceAll(" ", "+");
                            int nearest_a = origen.trobaNearestFull(bicing);
                            long metres = 0;
                            float minuts = 0.0f;
                            if (nearest_a != -1) {
                                int nearest_b = desti.trobaNearest(bicing);
                                if (nearest_b != -1) {
                                    place_b = bicing.getStations()[nearest_a].getStreetName().replaceAll(" ", "+") + ",+" + bicing.getStations()[nearest_a].getStreetNumber() + ",+Barcelona";
                                    place_c = bicing.getStations()[nearest_b].getStreetName().replaceAll(" ", "+") + ",+" + bicing.getStations()[nearest_b].getStreetNumber() + ",+Barcelona";
                                    Ruta ruta = new Ruta();
                                    ruta.llegirRuta((place_a + "|" + place_c), (place_b + "|" + place_d), "walking");
                                    if (ruta.getRows()[0].getElements()[0].getStatus().equals("OK") && ruta.getRows()[1].getElements()[1].getStatus().equals("OK")) {
                                        metres = ruta.getRows()[0].getElements()[0].getDistance().getValue() + ruta.getRows()[1].getElements()[1].getDistance().getValue();
                                        minuts = ruta.getRows()[0].getElements()[0].getDuration().getValue() + ruta.getRows()[1].getElements()[1].getDuration().getValue();
                                        ruta.llegirRuta(place_b, place_c, "bicycling");
                                        metres += ruta.getRows()[0].getElements()[0].getDistance().getValue();
                                        minuts += ruta.getRows()[0].getElements()[0].getDuration().getValue();
                                        minuts = Math.round(minuts / 60);
                                        int minutos = (int) minuts;
                                        ranking.addOpcio3(bicing.getStations()[nearest_a], bicing.getStations()[nearest_b]);
                                        ruta_ranking.add(origen, desti, bicing.getStations()[nearest_a], bicing.getStations()[nearest_b], metres);
                                        System.out.println("\n--------- Generant ruta ---------\n-- Origen: " + origen.getResults()[0].getFormattedAddress() + "\n-- Destí: " + desti.getResults()[0].getFormattedAddress() + "\n-- Distància: " + metres + " metres" + "\n-- Duració: " + minutos + " minuts\n");
                                        System.out.println("Estació d'origen més propera: " + bicing.getStations()[nearest_a].getStreetName() + ", " + bicing.getStations()[nearest_a].getStreetNumber() + "\nEstació destí més propera: " + bicing.getStations()[nearest_b].getStreetName() + ", " + bicing.getStations()[nearest_b].getStreetNumber() + "\n");
                                        System.out.println("Obrint Google Maps amb la ruta....\n");
                                        String way = place_a + "/" + place_b + "/" + place_c + "/" + place_d;
                                        origen.visualitzaRuta(way);
                                    } else {
                                        System.out.println("No s'ha pogut crear una ruta entre les destinacions, torni-ho a probar!\n");
                                    }
                                }
                            }
                        }
                    } else if (opcio == 4) {
                        System.out.print("Mínim de bicis disponibles?: ");
                        SC.nextLine();
                        String place;
                        int disponibles = SC.nextInt();
                        Bicing bicing = new Bicing();
                        bicing.llegirBicing();
                        place = bicing.trobaEstacions(disponibles);
                        if (!place.equals("+")) {
                            bicing.visualitzaImatge(place);
                        }
                    } else if (opcio == 5) {
                        System.out.print("Introdueix un lloc a cercar: ");
                        SC.nextLine();
                        String place = SC.nextLine();
                        System.out.println("\nBuscant " + place + "...\n");
                        String place_b = place.replaceAll(" ", "+");
                        Services services = new Services();
                        boolean services_ok = services.llegirServices(place_b);
                        if (services_ok) {
                            int num_guardar = services.mostraPerGuardar();
                            if (num_guardar != -1) {
                                ranking.addOpcio5(services.getResults()[num_guardar].getFormattedAddress());
                                String nom = services.getResults()[num_guardar].getName();
                                String adreça = services.getResults()[num_guardar].getFormattedAddress();
                                fitxer.addTerm(nom, adreça);
                                fitxer.guardaFitxer();
                                System.out.println("Lloc desat en el fitxer!\n");
                            } else {
                                System.out.println("Cap resultat guardat\n");
                            }
                        }
                    } else if (opcio == 6) {
                        fitxer.mostraFitxer();
                    } else if (opcio == 7) {
                        Bicing bicing = new Bicing();
                        bicing.llegirBicing();
                        bicing.mostraEstacionsLlunyanes();
                    } else if (opcio == 8) {
                        System.out.println("------- Rànquing -------\n");
                        ranking.mostraRanking(ruta_ranking);
                    } else if (opcio != 9) {
                        System.out.println("Error en el nombre de la opció");
                    }
                    System.out.println("************ LSBicing menu ************\n1. Geolocalització\n2. Buscar estació de Bicing més propera\n3. Creació d'una ruta\n4. Visualització d'estacions de Bicing\n5. Cerca d'ubicacions\n6. Visualitzar ubicació guardada\n7. Estacions més llunyanes\n8. Rànquing\n9. Sortir\n");
                    System.out.print("Introdueix una opció: ");
                    opcio = SC.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("Tipus de variable incorrecte\n");
            }
        }
        System.out.println("Gràcies per fer servir el nostre servei! Fins un altre!\n");
    }
}
