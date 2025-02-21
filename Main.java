import be.ipam.menegon.model.exceptions.MaxVolumeReachedException;
import be.ipam.menegon.model.exceptions.MaxWeightReachedException;
import be.ipam.menegon.model.exceptions.TruckNotSettedException;
import be.ipam.menegon.model.loadmaster.Loadmaster;
import be.ipam.menegon.model.truck.Camion;
import be.ipam.menegon.model.truck.Chargeable;
import be.ipam.menegon.model.truck.Palette;
import be.ipam.menegon.model.truck.Vrac;

import java.util.*;

/**
 * Classe principale qui gère l'interaction utilisateur pour l'application de gestion des camions et des Loadmasters.
 * Permet d'ajouter des Loadmasters, gérer les camions associés à chaque Loadmaster, et charger/décharger des éléments.
 * @author Giovanni Menegon
   Class Main
 */
public class Main {

    /**
     * Point d'entrée de l'application.
     * Gère les interactions avec l'utilisateur via un menu en ligne de commande.
     * Permet de choisir parmi plusieurs options pour ajouter un Loadmaster, afficher les Loadmasters,
     * sélectionner un Loadmaster pour gestion détaillée, ou quitter l'application.
     *
     * @param args Arguments en ligne de commande (non utilisés).
       Fonction main
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Loadmaster> loadmasters = new HashMap<>();

        while (true) {
            System.out.println("\n1. Ajouter un Loadmaster");
            System.out.println("2. Afficher la liste des Loadmasters");
            System.out.println("3. Sélectionner un Loadmaster");
            System.out.println("4. Quitter");

            System.out.print("Choisissez une option : ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            switch (option) {
                case 1:
                    // Ajouter un Loadmaster :
                    System.out.print("Entrez le nom du Loadmaster : ");
                    String loadmasterNom = scanner.nextLine().toUpperCase();
                    if (loadmasters.containsKey(loadmasterNom)) {
                        System.out.println("Ce Loadmaster existe déjà.");
                    } else {
                        Loadmaster loadmaster = new Loadmaster(loadmasterNom);
                        loadmasters.put(loadmaster.getNom(), loadmaster);
                        System.out.println("Loadmaster ajouté avec succès.");
                    }
                    break;

                case 2:
                    // Afficher la liste des Loadmasters :
                    if (loadmasters.isEmpty()) {
                        System.out.println("Aucun Loadmaster enregistré.");
                    } else {
                        System.out.println("Liste des Loadmasters :");
                        for (String nom : loadmasters.keySet()) {
                            System.out.println("- " + nom);
                        }
                    }
                    break;

                case 3:
                    // Sélectionner un Loadmaster :
                    Loadmaster loadmaster = null;
                    while (loadmaster == null) {
                        System.out.print("Entrez le nom du Loadmaster : ");
                        loadmasterNom = scanner.nextLine().toUpperCase();
                        loadmaster = loadmasters.get(loadmasterNom);

                        if (loadmaster == null) {
                            System.out.println("Loadmaster non trouvé. Veuillez réessayer.");
                        }
                    }

                    boolean returnToMainMenu = false;
                    while (!returnToMainMenu) {
                        System.out.println("\nGestion du Loadmaster : " + loadmaster.getNom());
                        System.out.println("1. Ajouter un camion");
                        System.out.println("2. Charger un élément dans un camion");
                        System.out.println("3. Décharger un élément d'un camion");
                        System.out.println("4. Afficher les éléments d'un camion");
                        System.out.println("5. Afficher les détails d'un camion");
                        System.out.println("6. Supprimer un camion");
                        System.out.println("7. Retour");

                        System.out.print("Choisissez une option : ");
                        int subOption = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante

                        switch (subOption) {
                            case 1:
                                // Ajouter un camion :
                                boolean validCamion = false;
                                while (!validCamion) {
                                    try {
                                        System.out.print("Entrez l'ID du camion : ");
                                        String camionId = scanner.nextLine();
                                        System.out.print("Entrez le poids maximum du camion : ");
                                        int maxWeight = scanner.nextInt();
                                        System.out.print("Entrez le volume maximum du camion : ");
                                        double maxVolume = scanner.nextDouble();
                                        scanner.nextLine(); // Consomme la ligne restante

                                        // Vérifier si le camion est déjà géré par ce Loadmaster :
                                        if (loadmaster.getCamions().containsKey(camionId)) {
                                            System.out.println("Ce camion est déjà géré par ce Loadmaster.");
                                        } else {
                                            Camion camion = new Camion(camionId, maxWeight, maxVolume, loadmaster);
                                            loadmaster.addCamion(camion);
                                            System.out.println("Camion ajouté avec succès.");
                                            validCamion = true;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                        scanner.nextLine(); // Consomme la ligne restante
                                    }
                                }
                                break;

                            case 2:
                                // Charger un élément dans un camion :
                                boolean validLoad = false;
                                while (!validLoad) {
                                    try {
                                        System.out.print("Entrez l'ID du camion : ");
                                        String camionIdToLoad = scanner.nextLine();
                                        System.out.print("Choisissez le type d'élément à charger : (1) Vrac (2) Palette (3) Custom : ");
                                        int type = scanner.nextInt();
                                        scanner.nextLine(); // Consomme la ligne restante

                                        System.out.print("Entrez l'ID de l'élément : ");
                                        String itemId = scanner.nextLine();
                                        System.out.print("Entrez le poids de l'élément : ");
                                        int weight = scanner.nextInt(); // Changez ici pour int
                                        System.out.print("Entrez le volume de l'élément : ");
                                        double volume = scanner.nextDouble(); // Volume reste en double
                                        scanner.nextLine(); // Consomme la ligne restante

                                        Chargeable item = null;
                                        switch (type) {
                                            case 1:
                                                item = new Vrac(itemId, weight, volume); // Assurez-vous que Vrac utilise int pour le poids et double pour le volume
                                                break;
                                            case 2:
                                                item = new Palette(itemId, weight, volume); // Assurez-vous que Palette utilise int pour le poids et double pour le volume
                                                break;
                                            case 3:
                                                item = new Chargeable() {
                                                    @Override
                                                    public String getId() {
                                                        return itemId;
                                                    }

                                                    @Override
                                                    public int getWeight() { // Changez ici pour int
                                                        return weight;
                                                    }

                                                    @Override
                                                    public double getVolume() { // Volume reste en double
                                                        return volume;
                                                    }

                                                    @Override
                                                    public String toString() {
                                                        StringBuilder sb = new StringBuilder();
                                                        sb.append("Custom [ID=").append(getId())
                                                                .append(", Weight=").append(String.format(Locale.US, "%d", getWeight())).append(" kg") // Format int
                                                                .append(", Volume=").append(String.format(Locale.US, "%.2f", getVolume())).append(" m³") // Format double
                                                                .append("]");
                                                        return sb.toString();
                                                    }
                                                };
                                                break;
                                            default:
                                                System.out.println("Type d'élément invalide.");
                                                break;
                                        }

                                        if (item != null) {
                                            try {
                                                loadmaster.loadItem(camionIdToLoad, item);
                                                System.out.println("Élément chargé avec succès.");
                                                validLoad = true;
                                            } catch (MaxWeightReachedException e) {
                                                System.out.println("Erreur : " + e.getMessage() + " Élément : " + e.getItem());
                                            } catch (MaxVolumeReachedException e) {
                                                System.out.println("Erreur : " + e.getMessage() + " Élément : " + e.getItem());
                                            } catch (TruckNotSettedException e) {
                                                System.out.println("Erreur : " + e.getMessage());
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                        scanner.nextLine(); // Consomme la ligne restante
                                    }
                                }
                                break;

                            case 3:
                                // Décharger un élément d'un camion
                                boolean validUnload = false;
                                while (!validUnload) {
                                    try {
                                        System.out.print("Entrez l'ID du camion : ");
                                        String camionIdToUnload = scanner.nextLine();
                                        System.out.print("Entrez l'ID de l'élément : ");
                                        String itemIdToUnload = scanner.nextLine();

                                        // Créer un élément de type Vrac ou Palette pour décharger
                                        Chargeable itemToUnload = new Vrac(itemIdToUnload, 1, 0.1); // Poids et volume valides
                                        loadmaster.unloadItem(camionIdToUnload, itemToUnload);
                                        System.out.println("Élément déchargé avec succès.");
                                        validUnload = true;
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                    } catch (Exception e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                        scanner.nextLine(); // Consomme la ligne restante
                                    }
                                }
                                break;



                            case 4:
                                // Afficher les éléments d'un camion
                                System.out.print("Entrez l'ID du camion : ");
                                String camionIdToView = scanner.nextLine();

                                Camion camionToView = loadmaster.getCamions().get(camionIdToView);
                                if (camionToView == null) {
                                    System.out.println("Camion non trouvé.");
                                } else {
                                    System.out.println("Éléments dans le camion " + camionIdToView + ":");
                                    List<Chargeable> load = camionToView.getLoad();
                                    for (int i = 0; i < load.size(); i++) {
                                        System.out.println(load.get(i).toString());
                                    }
                                }
                                break;

                            case 5:
                                // Afficher les détails d'un camion
                                System.out.print("Entrez l'ID du camion : ");
                                String camionIdForDetails = scanner.nextLine();

                                Camion camionForDetails = loadmaster.getCamions().get(camionIdForDetails);
                                if (camionForDetails == null) {
                                    System.out.println("Camion non trouvé.");
                                } else {
                                    System.out.println("Détails du camion " + camionIdForDetails + ":");
                                    System.out.printf("Poids actuel : %d kg%n", camionForDetails.getCurrentWeight());
                                    System.out.printf("Volume actuel : %.2f m³%n", camionForDetails.getCurrentVolume());

                                    System.out.println("Éléments triés par ID :");
                                    List<Chargeable> sortedById = camionForDetails.getLoadSortedById();
                                    for (int i = 0; i < sortedById.size(); i++) {
                                        Chargeable item = sortedById.get(i);
                                        System.out.println(item.toString());
                                    }

                                    System.out.println("Éléments triés par poids croissant :");
                                    List<Chargeable> sortedByWeight = camionForDetails.getLoadSortedByWeight();
                                    for (int i = 0; i < sortedByWeight.size(); i++) {
                                        Chargeable item = sortedByWeight.get(i);
                                        System.out.println(item.toString());
                                    }

                                    System.out.println("Éléments triés par volume croissant :");
                                    List<Chargeable> sortedByVolume = camionForDetails.getLoadSortedByVolume();
                                    for (int i = 0; i < sortedByVolume.size(); i++) {
                                        Chargeable item = sortedByVolume.get(i);
                                        System.out.println(item.toString());
                                    }
                                }
                                break;

                            case 6:
                                // Supprimer un camion
                                boolean validDelete = false;
                                while (!validDelete) {
                                    try {
                                        System.out.print("Entrez l'ID du camion à supprimer : ");
                                        String camionIdToDelete = scanner.nextLine();

                                        if (loadmaster.getCamions().containsKey(camionIdToDelete)) {
                                            loadmaster.removeCamion(camionIdToDelete);
                                            System.out.println("Camion supprimé avec succès.");
                                            validDelete = true;
                                        } else {
                                            System.out.println("Camion non trouvé.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Erreur : " + e.getMessage());
                                    }
                                }
                                break;

                            case 7:
                                // Retourner au menu principal
                                returnToMainMenu = true;
                                break;

                            default:
                                System.out.println("Option invalide. Veuillez réessayer.");
                        }
                    }
                    break;

                case 4:
                    // Quitter
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;

                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
}
