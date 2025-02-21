package be.ipam.menegon.model.truck;

/**
 * Représente un objet qui peut être chargé dans un camion.
 * Un objet `Chargeable` doit avoir un identifiant unique, un volume et un poids.
 *
 * @author Dylan Menegon
 */
public interface Chargeable {

    /**
     * Retourne l'identifiant unique du matériel.
     *
     * @return L'identifiant unique du matériel.
     */
    public String getId();

    /**
     * Retourne le volume du matériel.
     *
     * @return Le volume du matériel en unités cubiques.
     */
    public double getVolume();

    /**
     * Retourne le poids du matériel.
     *
     * @return Le poids du matériel en unités de poids.
     */
    public int getWeight();
}
