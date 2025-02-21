package be.ipam.menegon.model.loadmaster;

import be.ipam.menegon.model.exceptions.MaxVolumeReachedException;
import be.ipam.menegon.model.exceptions.MaxWeightReachedException;
import be.ipam.menegon.model.exceptions.TruckNotSettedException;
import be.ipam.menegon.model.truck.Camion;
import be.ipam.menegon.model.truck.Chargeable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Représente un Loadmaster qui gère une collection de camions.
 * Permet d'ajouter, de supprimer des camions, et de charger/décharger des éléments dans les camions.
 * Les camions sont identifiés par un identifiant unique.
 *
 * @author Dylan Menegon
 */
public class Loadmaster {
    private final Map<String, Camion> camions;
    private String nom;
    private static final Set<String> usedCamionIds = new HashSet<>();

    /**
     * Constructeur pour initialiser un Loadmaster avec un nom.
     *
     * @param nom Le nom du Loadmaster. Ne peut pas être null ou vide.
     * @throws IllegalArgumentException Si le nom est null ou vide.
     */
    public Loadmaster(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du Loadmaster ne peut pas être vide.");
        }
        this.nom = nom.trim().toUpperCase();
        camions = new HashMap<>();
    }

    /**
     * Ajoute un camion à la liste de camions gérés par ce Loadmaster.
     *
     * @param camion Le camion à ajouter. Ne peut pas être null.
     * @throws IllegalArgumentException Si le camion est null ou si un camion avec le même ID existe déjà.
     */
    public void addCamion(Camion camion) {
        if (camion == null) {
            throw new IllegalArgumentException("Camion ne peut pas être nul.");
        }
        if (camions.containsKey(camion.getId())) {
            throw new IllegalArgumentException("Le camion existe déjà dans ce Loadmaster.");
        }
        synchronized (usedCamionIds) {  // Synchronisation pour éviter les accès concurrents
            if (usedCamionIds.contains(camion.getId())) {
                throw new IllegalArgumentException("Le camion avec cet ID est déjà pris en charge par un autre Loadmaster.");
            }
            usedCamionIds.add(camion.getId());
        }
        camions.put(camion.getId(), camion);
    }

    /**
     * Supprime un camion de la liste des camions gérés par ce Loadmaster.
     *
     * @param id L'identifiant du camion à supprimer. Ne peut pas être null ou vide.
     * @throws IllegalArgumentException Si l'identifiant est null, vide ou si le camion n'existe pas.
     */
    public void removeCamion(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant du camion ne peut pas être vide.");
        }
        if (!camions.containsKey(id)) {
            throw new IllegalArgumentException("Le camion avec cet identifiant n'existe pas.");
        }
        synchronized (usedCamionIds) {
            usedCamionIds.remove(id);
        }
        camions.remove(id);
    }

    /**
     * Charge un élément dans un camion spécifié.
     *
     * @param camionId L'identifiant du camion dans lequel charger l'élément.
     * @param item L'élément à charger.
     * @throws MaxWeightReachedException Si le poids total du camion dépasse la capacité maximale après le chargement.
     * @throws MaxVolumeReachedException Si le volume total du camion dépasse la capacité maximale après le chargement.
     * @throws TruckNotSettedException Si le camion avec l'ID spécifié n'existe pas.
     */
    public void loadItem(String camionId, Chargeable item)
            throws MaxWeightReachedException, MaxVolumeReachedException, TruckNotSettedException {
        Camion camion = camions.get(camionId);
        if (camion == null) {
            throw new TruckNotSettedException("Le camion avec l'ID " + camionId + " n'est pas défini.");
        }
        if (camion.getCurrentWeight() + item.getWeight() > camion.getMaxWeight()) {
            throw new MaxWeightReachedException("Le poids maximum du camion est atteint.", item);
        }
        if (camion.getCurrentVolume() + item.getVolume() > camion.getMaxVolume()) {
            throw new MaxVolumeReachedException("Le volume maximum du camion est atteint.", item);
        }
        camion.load(item);
    }

    /**
     * Décharge un élément d'un camion spécifié.
     *
     * @param camionId L'identifiant du camion duquel décharger l'élément.
     * @param item L'élément à décharger.
     * @throws IllegalArgumentException Si le camion avec l'ID spécifié n'existe pas ou si l'élément est null.
     */
    public void unloadItem(String camionId, Chargeable item) {
        Camion camion = camions.get(camionId);
        if (camion == null) {
            throw new IllegalArgumentException("Le camion avec l'ID " + camionId + " n'existe pas.");
        }
        if (item == null) {
            throw new IllegalArgumentException("L'article à décharger ne peut pas être nul.");
        }
        camion.unload(item);
    }

    /**
     * Retourne la carte des camions gérés par ce Loadmaster.
     *
     * @return Une carte contenant les camions, identifiés par leur ID.
     */
    public Map<String, Camion> getCamions() {
        return camions;
    }

    /**
     * Retourne le nom du Loadmaster.
     *
     * @return Le nom du Loadmaster.
     */
    public String getNom() {
        return nom;
    }


}
