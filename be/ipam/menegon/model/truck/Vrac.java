package be.ipam.menegon.model.truck;

import java.util.Locale;
import java.util.Objects;

/**
 * Représente un matériel en vrac pouvant être chargé dans un camion.
 * Un objet Vrac a un identifiant unique, un poids et un volume.
 *
 * @author Dylan Menegon
 */
public class Vrac implements Chargeable {

    private final String id;
    private final int weight;
    private final double volume;

    /**
     * Crée un nouvel objet Vrac avec les spécifications fournies.
     *
     * @param id L'identifiant unique du matériel en vrac. Ne peut pas être null ou vide.
     * @param weight Le poids du matériel en vrac. Doit être positif.
     * @param volume Le volume du matériel en vrac. Doit être positif.
     * @throws IllegalArgumentException Si l'ID est null ou vide, ou si le poids ou le volume sont négatifs.
     */
    public Vrac(String id, int weight, double volume) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID ne peut pas être null ou vide.");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Le poids doit être positif.");
        }
        if (volume <= 0) {
            throw new IllegalArgumentException("Le volume doit être positif.");
        }
        this.id = id;
        this.weight = weight;
        this.volume = volume;
    }

    /**
     * Retourne l'identifiant unique du matériel en vrac.
     *
     * @return L'identifiant du matériel en vrac.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Retourne le poids du matériel en vrac.
     *
     * @return Le poids du matériel en vrac en unités de poids.
     */
    @Override
    public int getWeight() {
        return weight;
    }

    /**
     * Retourne le volume du matériel en vrac.
     *
     * @return Le volume du matériel en vrac en unités cubiques.
     */
    @Override
    public double getVolume() {
        return volume;
    }

    /**
     * Compare cette instance de Vrac avec une autre pour déterminer leur égalité.
     *
     * @param o L'objet à comparer avec cette instance de Vrac.
     * @return {@code true} si les deux objets sont égaux, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vrac vrac = (Vrac) o;
        return Objects.equals(id, vrac.id);
    }

    /**
     * Calcule le code de hachage de l'objet Vrac.
     *
     * @return Le code de hachage de l'objet Vrac.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du matériel en vrac.
     *
     * @return Une chaîne de caractères représentant le matériel en vrac, incluant son ID, son poids et son volume.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vrac [ID=").append(id)
                .append(", Weight=").append(String.format("%d.00", weight)).append(" kg")
                .append(", Volume=").append(String.format(Locale.US, "%.2f", volume)).append(" m³")
                .append("]");
        return sb.toString();
    }
}
