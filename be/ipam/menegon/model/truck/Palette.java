package be.ipam.menegon.model.truck;

import java.util.Locale;
import java.util.Objects;

/**
 * Représente une palette qui peut être chargée dans un camion.
 * Une palette a un identifiant unique, un poids et un volume.
 *
 * @author Dylan Menegon
 */
public class Palette implements Chargeable {

    private final String id;
    private final int weight;
    private final double volume;

    /**
     * Crée une nouvelle palette avec les spécifications fournies.
     *
     * @param id L'identifiant unique de la palette. Ne peut pas être null ou vide.
     * @param weight Le poids de la palette. Doit être positif.
     * @param volume Le volume de la palette. Doit être positif.
     * @throws IllegalArgumentException Si l'ID est null ou vide, ou si le poids ou le volume sont négatifs.
     */
    public Palette(String id, int weight, double volume) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID ne peut pas être null ou vide.");
        }
        if (weight == 0) {
            throw new IllegalArgumentException("Le poids ne peut pas être égal à zéro.");
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
     * Retourne l'identifiant unique de la palette.
     *
     * @return L'identifiant de la palette.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Retourne le poids de la palette.
     *
     * @return Le poids de la palette en unités de poids.
     */
    @Override
    public int getWeight() {
        return weight;
    }

    /**
     * Retourne le volume de la palette.
     *
     * @return Le volume de la palette en unités cubiques.
     */
    @Override
    public double getVolume() {
        return volume;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la palette.
     *
     * @return Une chaîne de caractères représentant la palette, incluant son ID, son poids et son volume.
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append("Palette [ID=").append(id)
                .append(", Weight=").append(String.format(Locale.US, "%.2f", (double) weight)).append(" kg")
                .append(", Volume=").append(String.format(Locale.US, "%.2f", volume)).append(" m³")
                .append("]").toString();
    }

    /**
     * Calcule le code de hachage de la palette.
     *
     * @return Le code de hachage de la palette.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, weight, volume);
    }

    /**
     * Vérifie si deux palettes sont égales.
     *
     * @param o L'objet à comparer avec cette palette.
     * @return {@code true} si les deux palettes sont égales, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palette palette = (Palette) o;
        return weight == palette.weight &&
                Double.compare(palette.volume, volume) == 0 &&
                Objects.equals(id, palette.id);
    }
}
