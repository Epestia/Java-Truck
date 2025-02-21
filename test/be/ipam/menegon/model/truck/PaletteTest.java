package test.be.ipam.menegon.model.truck;

import be.ipam.menegon.model.truck.Palette;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

// Classe de test pour la classe Palette
public class PaletteTest extends TestCase {

    // Test du constructeur de la classe Palette
    @Test
    public void testConstructor(){
        // Cas valides
        try {
            // Création d'un objet Palette avec des valeurs valides
            Palette palette = new Palette("123", 10, 5.0);
            // Vérification que l'identifiant correspond à la valeur attendue
            assertEquals("L'identifiant ne correspond pas.", "123", palette.getId());
            // Vérification que le poids correspond à la valeur attendue
            assertEquals("Le poids ne correspond pas.", 10, palette.getWeight());
            // Vérification que le volume correspond à la valeur attendue
            assertEquals("Le volume ne correspond pas.", 5.0, palette.getVolume(), 0.01);
        } catch (Exception e) {
            // Si une exception est levée pour des valeurs valides, le test échoue
            fail("Une exception inattendue a été levée pour des valeurs valides : " + e.getMessage());
        }

        // Cas invalides

        // Test avec un ID null
        try {
            // Tentative de création d'un objet Palette avec un ID null
            new Palette(null, 10, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un ID null.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("L'ID ne peut pas être null ou vide.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un ID vide
        try {
            // Tentative de création d'un objet Palette avec un ID vide
            new Palette("", 10, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un ID vide.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("L'ID ne peut pas être null ou vide.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un poids égal à zéro
        try {
            // Tentative de création d'un objet Palette avec un poids de 0
            new Palette("123", 0, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un poids égal à zéro.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le poids ne peut pas être égal à zéro.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un poids négatif
        try {
            // Tentative de création d'un objet Palette avec un poids négatif
            new Palette("123", -10, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un poids négatif.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le poids doit être positif.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un volume égal à zéro
        try {
            // Tentative de création d'un objet Palette avec un volume de 0
            new Palette("123", 10, 0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un volume égal à zéro.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le volume doit être positif.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un volume négatif
        try {
            // Tentative de création d'un objet Palette avec un volume négatif
            new Palette("123", 10, -5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un volume négatif.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le volume doit être positif.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }
    }

    // Test de la méthode equals de la classe Palette
    @Test
    public void testEquals() {
        try {
            // Création de deux objets Palette avec des valeurs identiques
            Palette palette1 = new Palette("ID123", 10, 5.0);
            Palette palette2 = new Palette("ID123", 10, 5.0);
            // Création d'un troisième objet Palette avec des valeurs différentes
            Palette palette3 = new Palette("ID124", 12, 6.0);

            // Vérification que les deux premiers objets sont égaux
            assertEquals("Les objets Palette ne sont pas égaux", palette1, palette2);
            // Vérification que le troisième objet est différent du premier
            assertNotEquals("Les objets Palette devraient être différents", palette1, palette3);
        } catch (Exception e) {
            // Si une exception est levée, le test échoue
            fail("Une exception inattendue a été levée lors du testEquals: " + e.getMessage());
        }
    }

    // Test de la méthode hashCode de la classe Palette
    @Test
    public void testHashCode() {
        try {
            // Création de deux objets Palette avec des valeurs identiques
            Palette palette1 = new Palette("ID123", 10, 5.0);
            Palette palette2 = new Palette("ID123", 10, 5.0);
            // Création d'un troisième objet Palette avec des valeurs différentes
            Palette palette3 = new Palette("ID124", 12, 6.0);

            // Vérification que les codes de hachage des deux premiers objets sont égaux
            assertEquals("Les codes de hachage doivent être égaux pour des objets égaux", palette1.hashCode(), palette2.hashCode());
            // Vérification que les codes de hachage du premier et du troisième objet sont différents
            assertNotEquals("Les codes de hachage doivent être différents pour des objets différents", palette1.hashCode(), palette3.hashCode());
        } catch (Exception e) {
            // Si une exception est levée, le test échoue
            fail("Une exception inattendue a été levée lors du testHashCode: " + e.getMessage());
        }
    }

    @Test
    public void testToString() {
        try {
            // Création d'un objet Palette
            Palette palette = new Palette("ID123", 10, 5.0);
            // Chaîne de caractères attendue
            String expectedToString = "Palette [ID=ID123, Weight=10.00 kg, Volume=5.00 m³]";
            // Vérification que la méthode toString retourne la chaîne attendue
            assertEquals("La méthode toString() ne retourne pas la chaîne attendue", expectedToString, palette.toString());
        } catch (Exception e) {
            // Si une exception est levée, le test échoue
            fail("Une exception inattendue a été levée lors du testToString: " + e.getMessage());
        }
    }
}
