package test.be.ipam.menegon.model.truck;

import be.ipam.menegon.model.truck.Vrac;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertNotEquals;


// Classe de test pour la classe Vrac
public class VracTest extends TestCase {

    // Test du constructeur de la classe Vrac
    @Test
    public void testConstructor(){
        // Cas valides
        try {
            // Création d'un objet Vrac avec des valeurs valides
            Vrac vrac = new Vrac("123", 10, 5.0);
            // Vérification que l'identifiant correspond à la valeur attendue
            assertEquals("L'identifiant ne correspond pas.", "123", vrac.getId());
            // Vérification que le poids correspond à la valeur attendue
            assertEquals("Le poids ne correspond pas.", 10.0, vrac.getWeight(), 0.01);
            // Vérification que le volume correspond à la valeur attendue
            assertEquals("Le volume ne correspond pas.", 5.0, vrac.getVolume(), 0.01);
        } catch (Exception e) {
            // Si une exception est levée pour des valeurs valides, le test échoue
            fail("Une exception inattendue a été levée pour des valeurs valides : " + e.getMessage());
        }

        // Cas invalides

        // Test avec un ID null
        try {
            // Tentative de création d'un objet Vrac avec un ID null
            new Vrac(null, 10, 5.0);
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
            // Tentative de création d'un objet Vrac avec un ID vide
            new Vrac("", 10, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un ID vide.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("L'ID ne peut pas être null ou vide.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un poids non positif (0)
        try {
            // Tentative de création d'un objet Vrac avec un poids de 0
            new Vrac("123", 0, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un poids non positif.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le poids doit être positif.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un poids négatif
        try {
            // Tentative de création d'un objet Vrac avec un poids négatif
            new Vrac("123", -10, 5.0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un poids négatif.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le poids doit être positif.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un volume non positif (0)
        try {
            // Tentative de création d'un objet Vrac avec un volume de 0
            new Vrac("123", 10, 0);
            // Si aucune exception n'est levée, le test échoue
            fail("Une exception aurait dû être lancée pour un volume non positif.");
        } catch (IllegalArgumentException e) {
            // Vérification que le message d'exception est celui attendu
            assertEquals("Le volume doit être positif.", e.getMessage());
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une autre exception inattendue a été levée : " + e.getMessage());
        }

        // Test avec un volume négatif
        try {
            // Tentative de création d'un objet Vrac avec un volume négatif
            new Vrac("123", 10, -5.0);
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

    // Test de la méthode equals de la classe Vrac
    @Test
    public void testEquals() {
        try {
            // Création de deux objets Vrac avec des valeurs identiques
            Vrac vrac1 = new Vrac("ID123", 10, 5.0);
            Vrac vrac2 = new Vrac("ID123", 10, 5.0);
            // Création d'un troisième objet Vrac avec des valeurs différentes
            Vrac vrac3 = new Vrac("ID124", 12, 6.0);

            // Vérification que les deux premiers objets sont égaux
            assertEquals("Les objets Vrac ne sont pas égaux", vrac1, vrac2);
            // Vérification que le troisième objet est différent du premier
            assertNotEquals("Les objets Vrac devraient être différents", vrac1, vrac3);
        } catch (Exception e) {
            // Si une exception est levée, le test échoue
            fail("Une exception inattendue a été levée lors du testEquals: " + e.getMessage());
        }
    }

    // Test de la méthode hashCode de la classe Vrac
    @Test
    public void testHashCode() {
        try {
            // Création de deux objets Vrac avec des valeurs identiques
            Vrac vrac1 = new Vrac("ID123", 10, 5.0);
            Vrac vrac2 = new Vrac("ID123", 10, 5.0);
            // Création d'un troisième objet Vrac avec des valeurs différentes
            Vrac vrac3 = new Vrac("ID124", 12, 6.0);

            // Vérification que les codes de hachage des deux premiers objets sont égaux
            assertEquals("Les codes de hachage doivent être égaux pour des objets égaux", vrac1.hashCode(), vrac2.hashCode());
            // Vérification que les codes de hachage du premier et du troisième objet sont différents
            assertNotEquals("Les codes de hachage doivent être différents pour des objets différents", vrac1.hashCode(), vrac3.hashCode());
        } catch (Exception e) {
            // Si une exception est levée, le test échoue
            fail("Une exception inattendue a été levée lors du testHashCode: " + e.getMessage());
        }
    }

    // Test de la méthode toString de la classe Vrac
    @Test
    public void testToString() {
        Vrac vrac = new Vrac("ID123", 10, 5.0);
        String expected = "Vrac [ID=ID123, Weight=10.00 kg, Volume=5.00 m³]";
        String actual = vrac.toString();
        Assert.assertEquals("La méthode toString() ne retourne pas la chaîne attendue", expected, actual);
    }

}
