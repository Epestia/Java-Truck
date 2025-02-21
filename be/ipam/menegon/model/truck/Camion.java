package be.ipam.menegon.model.truck;

import be.ipam.menegon.model.exceptions.MaxVolumeReachedException;
import be.ipam.menegon.model.exceptions.MaxWeightReachedException;
import be.ipam.menegon.model.loadmaster.Loadmaster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Représente un camion avec une capacité de poids et de volume limitée.
 * Permet de charger et de décharger des éléments, ainsi que de récupérer des informations
 * sur le chargement actuel trié par différents critères.
 *
 * @author Dylan Menegon
 */
public class Camion {
    private final String id;
    private final int maxWeight; // Poids maximum en int
    private final double maxVolume; // Volume maximum en double
    private int currentWeight; // Poids actuel en int
    private double currentVolume; // Volume actuel en double
    private final List<Chargeable> load;
    private final Loadmaster loadmaster;

    /**
     * Constructeur pour initialiser un camion avec un identifiant, une capacité maximale de poids,
     * un volume maximum, et un Loadmaster associé.
     *
     * @param id L'identifiant du camion. Ne peut pas être null ou vide.
     * @param maxWeight Le poids maximum que le camion peut supporter. Doit être positif.
     * @param maxVolume Le volume maximum que le camion peut supporter. Doit être positif.
     * @param loadmaster Le Loadmaster associé au camion. Ne peut pas être null.
     * @throws IllegalArgumentException Si l'identifiant est null ou vide, ou si le poids ou le volume maximum sont négatifs, ou si le Loadmaster est null.
     */
    public Camion(String id, int maxWeight, double maxVolume, Loadmaster loadmaster) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID ne peut pas être nul ou vide.");
        }
        if (maxWeight < 0) {
            throw new IllegalArgumentException("Le poids maximum doit être positif.");
        }
        if (maxVolume < 0) {
            throw new IllegalArgumentException("Le volume maximum doit être positif.");
        }
        if (loadmaster == null) {
            throw new IllegalArgumentException("Le Loadmaster du camion ne peut pas être null.");
        }
        this.id = id;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.loadmaster = loadmaster;
        this.currentWeight = 0;
        this.currentVolume = 0;
        this.load = new ArrayList<>();
    }

    /**
     * Retourne l'identifiant du camion.
     *
     * @return L'identifiant du camion.
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le poids maximum que le camion peut supporter.
     *
     * @return Le poids maximum du camion.
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Retourne le volume maximum que le camion peut supporter.
     *
     * @return Le volume maximum du camion.
     */
    public double getMaxVolume() {
        return maxVolume;
    }

    /**
     * Retourne le poids actuel du camion.
     *
     * @return Le poids actuel du camion.
     */
    public int getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Retourne le volume actuel du camion.
     *
     * @return Le volume actuel du camion.
     */
    public double getCurrentVolume() {
        return currentVolume;
    }

    /**
     * Charge un élément dans le camion.
     *
     * @param item L'élément à charger. Ne peut pas être null.
     * @throws MaxWeightReachedException Si le poids total après chargement dépasse la capacité maximale du camion.
     * @throws MaxVolumeReachedException Si le volume total après chargement dépasse la capacité maximale du camion.
     */
    public void load(Chargeable item) throws MaxWeightReachedException, MaxVolumeReachedException {
        if (item == null) {
            throw new IllegalArgumentException("L'article à charger ne peut pas être nul.");
        }

        // Vérifier si le poids total après chargement dépasse le poids maximum
        if (currentWeight + item.getWeight() > maxWeight) {
            throw new MaxWeightReachedException("Le poids maximum du camion est atteint.", item);
        }

        // Vérifier si le volume total après chargement dépasse le volume maximum
        if (currentVolume + item.getVolume() > maxVolume) {
            throw new MaxVolumeReachedException("Le volume maximum du camion est atteint.", item);
        }

        // Ajouter l'article à la liste de chargement
        load.add(item);
        currentWeight += item.getWeight();
        currentVolume += item.getVolume();
    }

    /**
     * Décharge un élément du camion.
     *
     * @param item L'élément à décharger. Ne peut pas être null.
     * @throws IllegalArgumentException Si l'élément à décharger n'est pas trouvé dans le camion ou est null.
     */
    public void unload(Chargeable item) {
        if (item == null) {
            throw new IllegalArgumentException("L'article à décharger ne peut pas être nul.");
        }

        if (!load.remove(item)) {
            throw new IllegalArgumentException("L'article à décharger n'est pas trouvé dans le camion.");
        }

        // Mise à jour du poids et du volume après déchargement
        currentWeight -= item.getWeight();
        currentVolume -= item.getVolume();
    }

    /**
     * Retourne la liste des éléments actuellement chargés dans le camion.
     *
     * @return Une liste des éléments actuellement chargés dans le camion.
     */
    public List<Chargeable> getLoad() {
        return new ArrayList<>(load);
    }

    /**
     * Retourne la liste des éléments actuellement chargés dans le camion triés par ID.
     *
     * @return Une liste des éléments triés par ID.
     */
    public List<Chargeable> getLoadSortedById() {
        List<Chargeable> sortedLoad = new ArrayList<>(load);
        sortedLoad.sort(Comparator.comparing(Chargeable::getId));
        return sortedLoad;
    }

    /**
     * Retourne la liste des éléments actuellement chargés dans le camion triés par poids décroissant.
     *
     * @return Une liste des éléments triés par poids décroissant.
     */
    public List<Chargeable> getLoadSortedByWeight() {
        List<Chargeable> sortedLoad = new ArrayList<>(load);
        sortedLoad.sort(Comparator.comparingDouble(Chargeable::getWeight).reversed());
        return sortedLoad;
    }

    /**
     * Retourne la liste des éléments actuellement chargés dans le camion triés par volume décroissant.
     *
     * @return Une liste des éléments triés par volume décroissant.
     */
    public List<Chargeable> getLoadSortedByVolume() {
        List<Chargeable> sortedLoad = new ArrayList<>(load);
        sortedLoad.sort(Comparator.comparingDouble(Chargeable::getVolume).reversed());
        return sortedLoad;
    }
    public void updateCurrentWeight(int delta) {
        this.currentWeight += delta;
    }

    public void updateCurrentVolume(double delta) {
        this.currentVolume += delta;
    }

}