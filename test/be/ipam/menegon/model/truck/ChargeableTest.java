package test.be.ipam.menegon.model.truck;

import be.ipam.menegon.model.truck.Palette;
import be.ipam.menegon.model.truck.Chargeable;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;


public class ChargeableTest extends TestCase {

    @Test
    public void testChargeableImplementationWithTryCatch() {
        try {
            // Cas valides : Crée un objet Palette avec des valeurs valides
            Chargeable chargeable = new Palette("ID001", 100, 50.0);

            // Vérifie que l'identifiant est correct
            assertEquals("L'identifiant ne correspond pas.", "ID001", chargeable.getId());

            // Vérifie que le poids est correct
            assertEquals("Le poids ne correspond pas.", 100.0, chargeable.getWeight(), 0.01);

            // Vérifie que le volume est correct
            assertEquals("Le volume ne correspond pas.", 50.0, chargeable.getVolume(), 0.01);

            // Cas invalides : Tente de créer un objet avec un ID null
            try {
                new Palette(null, 100, 50.0);

                // Si aucune exception n'est levée, le test échoue
                fail("Une exception aurait dû être lancée pour un ID null.");
            } catch (IllegalArgumentException e) {

                // Vérifie que le message d'erreur est celui attendu
                assertEquals("L'ID ne peut pas être null ou vide.", e.getMessage());
            }

            // Cas invalides : Tente de créer un objet avec un ID vide
            try {
                new Palette("", 100, 50.0);

                // Si aucune exception n'est levée, le test échoue
                fail("Une exception aurait dû être lancée pour un ID vide.");
            } catch (IllegalArgumentException e) {

                // Vérifie que le message d'erreur est celui attendu
                assertEquals("L'ID ne peut pas être null ou vide.", e.getMessage());
            }

            // Cas invalides : Tente de créer un objet avec un poids négatif
            try {
                new Palette("ID002", -100, 50.0);

                // Si aucune exception n'est levée, le test échoue
                fail("Une exception aurait dû être lancée pour un poids négatif.");
            } catch (IllegalArgumentException e) {

                // Vérifie que le message d'erreur est celui attendu
                assertEquals("Le poids doit être positif.", e.getMessage());
            }

            // Cas invalides : Tente de créer un objet avec un volume négatif
            try {
                new Palette("ID003", 100, -50.0);

                // Si aucune exception n'est levée, le test échoue
                fail("Une exception aurait dû être lancée pour un volume négatif.");
            } catch (IllegalArgumentException e) {

                // Vérifie que le message d'erreur est celui attendu
                assertEquals("Le volume doit être positif.", e.getMessage());
            }

        } catch (Exception e) {

            // Capture toute autre exception inattendue et fait échouer le test
            fail("Une exception inattendue a été levée lors des tests : " + e.getMessage());
        }
    }
}
