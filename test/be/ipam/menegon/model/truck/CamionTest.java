package test.be.ipam.menegon.model.truck;

import be.ipam.menegon.model.exceptions.MaxWeightReachedException;
import be.ipam.menegon.model.exceptions.MaxVolumeReachedException;
import be.ipam.menegon.model.loadmaster.Loadmaster;
import be.ipam.menegon.model.truck.Camion;
import be.ipam.menegon.model.truck.Chargeable;
import be.ipam.menegon.model.truck.Palette;
import be.ipam.menegon.model.truck.Vrac;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CamionTest extends TestCase {

    private Camion camion;
    private Chargeable palette;
    private Chargeable vrac;
    private Loadmaster loadmaster;

    @Before
    public void setUp() {
        // Crée un objet Loadmaster pour les tests, représentant le gestionnaire de camions.
        loadmaster = new Loadmaster("Loadmaster");

        // Initialise un objet Palette avec un ID "P001", un poids de 5.0, et un volume de 10.0. Cette palette sera utilisée pour tester les opérations de chargement et déchargement dans le camion.
        palette = new Palette("P001", 5, 10.0);

        // Initialise un objet Vrac avec un ID "V001", un poids de 10.0, et un volume de 20.0. Cet objet sera également utilisé pour tester les opérations dans le camion, notamment pour vérifier le poids total.
        vrac = new Vrac("V001", 10, 20.0);

        // Crée un objet Camion avec un ID "C001", une capacité de poids maximale de 10000, et un volume maximal de 50.0. Le Loadmaster est associé au camion pour simuler le gestionnaire de camions dans les tests.
        camion = new Camion("C001", 10000, 50.0, loadmaster);
    }


    @Test
    public void testCamionConstructor() {

        // Cas 1 : Créer un camion avec des paramètres valides
        try {
            // Essaye de créer un camion avec des paramètres valides. Si aucune exception n'est levée, le test passe avec succès.
            Camion camion = new Camion("C001", 10000, 50.0, loadmaster);
            // Si le camion est créé avec succès, le message suivant sera affiché dans la console.
            //System.out.println("Test constructeur réussi : Le camion a été créé avec succès avec des paramètres valides.");
        } catch (Exception e) {
            // Si une exception est levée, cela indique que quelque chose s'est mal passé.
            fail("Une exception ne devrait pas être levée pour des paramètres valides : " + e.getMessage());
        }

        // Cas 2 : Test un camion avec un ID vide
        try {
            // Essaye de créer un camion avec un ID vide. Cela devrait lancer une IllegalArgumentException selon la validation du constructeur.
            new Camion("", 10000, 50.0, loadmaster);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une Lokiexception IllegalArgumentException devrait être levée pour un ID de camion vide");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un ID vide.
            assertEquals("L'ID ne peut pas être nul ou vide.", e.getMessage());
            // Indique que le test a réussi
            //System.out.println("Test constructeur réussi : IllegalArgumentException levée pour un ID de camion vide.");
        }

        // Cas 3 : Test un camion avec un ID null
        try {
            // Essaye de créer un camion avec un ID null. Cela devrait lancer une IllegalArgumentException selon la validation du constructeur.
            new Camion(null, 10000, 50.0, loadmaster);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une exception IllegalArgumentException devrait être levée pour un ID de camion null");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un ID null.
            assertEquals("L'ID ne peut pas être nul ou vide.", e.getMessage());
            //System.out.println("Test constructeur réussi : IllegalArgumentException levée pour un ID de camion null.");
        }

        // Cas 4 : Test un camion avec un poids maximum négatif
        try {
            // Essaye de créer un camion avec un poids maximum négatif. Cela devrait lancer une IllegalArgumentException selon la validation du constructeur.
            new Camion("C001", -10000, 50.0, loadmaster);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une exception IllegalArgumentException devrait être levée pour un poids maximum négatif");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un poids négatif.
            assertEquals("Le poids maximum doit être positif.", e.getMessage());
            //System.out.println("Test constructeur réussi : IllegalArgumentException levée pour un poids maximum négatif.");
        }

        // Cas 5 : Test un camion avec un volume maximum négatif
        try {
            // Essaye de créer un camion avec un volume maximum négatif. Cela devrait lancer une IllegalArgumentException selon la validation du constructeur.
            new Camion("C001", 10000, -50.0, loadmaster);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une exception IllegalArgumentException devrait être levée pour un volume maximum négatif");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un volume négatif.
            assertEquals("Le volume maximum doit être positif.", e.getMessage());
            //System.out.println("Test constructeur réussi : IllegalArgumentException levée pour un volume maximum négatif.");
        }

        // Cas 6 : Test avec un Loadmaster null
        try {
            // Essaye de créer un camion avec un Loadmaster null. Cela devrait lancer une IllegalArgumentException selon la validation du constructeur.
            new Camion("C001", 10000, 50.0, null);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une exception IllegalArgumentException devrait être levée pour un Loadmaster null.");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un Loadmaster null.
            assertEquals("Le Loadmaster du camion ne peut pas être null.", e.getMessage());
            //System.out.println("Test constructeur réussi : IllegalArgumentException levée pour un Loadmaster null.");
        }
    }



    @Test
    public void testLoad() {

        // Cas 1 : Charger un article valide
        try {
            // On tente de charger une palette dans le camion.
            camion.load(palette);
            // Vérifie que le poids actuel du camion est mis à jour correctement.
            assertEquals(5.0, camion.getCurrentWeight(), 0.01);
            // Vérifie que le volume actuel du camion est mis à jour correctement.
            assertEquals(10.0, camion.getCurrentVolume(), 0.01);
            // Vérifie que la palette est bien ajoutée à la liste des articles chargés dans le camion.
            assertTrue(camion.getLoadSortedById().contains(palette));
            //System.out.println("Test Load réussi : L'article valide a été chargé avec succès.");
        } catch (Exception e) {
            // Si une exception inattendue est levée lors du chargement d'un article valide, le test échoue.
            fail("Une exception inattendue a été levée : " + e.getMessage());
        }

        // Cas 2 : Tenter de charger un article null
        try {
            // On tente de charger un article null, ce qui devrait lancer une IllegalArgumentException.
            camion.load(null);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une IllegalArgumentException aurait dû être levée pour un article null");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un article null.
            assertEquals("L'article à charger ne peut pas être nul.", e.getMessage());
            //System.out.println("Test Load réussi : Une IllegalArgumentException a été levée comme prévu pour un article null.");
        } catch (MaxWeightReachedException | MaxVolumeReachedException e) {
            // Si une exception de type MaxWeightReachedException ou MaxVolumeReachedException est levée à la place, cela signifie qu'une exception inattendue a été levée, et le test échoue.
            fail("Une exception inattendue a été levée pour un article null : " + e.getMessage());
        }

        // Cas 3 : Tenter de charger un article dépassant le poids maximum
        try {
            // Création d'un article avec un poids dépassant la capacité maximale du camion.
            Chargeable heavyItem = new Vrac("V002", 15000, 30); // Poids qui dépasse le maxWeight du camion
            // On tente de charger cet article dans le camion, ce qui devrait lancer une MaxWeightReachedException.
            camion.load(heavyItem);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une MaxWeightReachedException aurait dû être levée pour dépassement de poids");
        } catch (MaxWeightReachedException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un dépassement de poids.
            assertEquals("Le poids maximum du camion est atteint.", e.getMessage());
            //System.out.println("Test Load réussi : Une MaxWeightReachedException a été levée comme prévu pour dépassement de poids.");
        } catch (MaxVolumeReachedException e) {
            // Si une exception de type MaxVolumeReachedException est levée à la place,
            // cela signifie qu'une exception inattendue a été levée, et le test échoue.
            fail("Aucune exception de dépassement de volume ne devrait être levée ici : " + e.getMessage());
        }

        // Cas 4 : Tenter de charger un article dépassant le volume maximum
        try {
            // Création d'un article avec un volume dépassant la capacité maximale du camion.
            Chargeable largeItem = new Palette("P002", 10, 60.0); // Volume qui dépasse le maxVolume du camion
            // On tente de charger cet article dans le camion, ce qui devrait lancer une MaxVolumeReachedException.
            camion.load(largeItem);
            // Si aucune exception n'est levée, le test échoue.
            fail("Une MaxVolumeReachedException aurait dû être levée pour dépassement de volume");
        } catch (MaxVolumeReachedException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un dépassement de volume.
            assertEquals("Le volume maximum du camion est atteint.", e.getMessage());
            // Affiche un message indiquant que le test pour dépassement de volume a réussi.
            //System.out.println("Test Load réussi : Une MaxVolumeReachedException a été levée comme prévu pour dépassement de volume.");
        } catch (MaxWeightReachedException e) {
            // Si une exception de type MaxWeightReachedException est levée à la place Cela signifie qu'une exception inattendue a été levée, et le test échoue.
            fail("Aucune exception de dépassement de poids ne devrait être levée ici : " + e.getMessage());
        }
    }



    @Test
    public void testUnload() {
        // Cas 1 : Décharger un article valide
        try {
            loadmaster.addCamion(camion);
            camion.load(palette); // Poids: 5.0, Volume: 10.0

            // Vérifie les poids et volumes avant le déchargement
            assertEquals(5.0, camion.getCurrentWeight(), 0.01);
            assertEquals(10.0, camion.getCurrentVolume(), 0.01);

            // Décharge l'article
            camion.unload(palette);

            // Vérifie que l'article a bien été retiré de la liste des articles chargés
            List<Chargeable> load = camion.getLoadSortedById();
            assertFalse(load.contains(palette));
            // Vérifie que le poids et le volume sont remis à zéro
            assertEquals(0.0, camion.getCurrentWeight(), 0.01);
            assertEquals(0.0, camion.getCurrentVolume(), 0.01);

            //System.out.println("Test unload réussi : Déchargement d'un article valide.");
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du déchargement d'un article valide : " + e.getMessage());
        }

        // Cas 2 : Tenter de décharger un article non chargé
        try {
            // Création d'un article non chargé
            Chargeable notLoadedItem = new Palette("P003", 5, 5.0);

            // Essaye de décharger cet article qui n'est pas dans le camion
            camion.unload(notLoadedItem);

            // Si aucune exception n'est levée, le test échoue
            fail("Une IllegalArgumentException aurait dû être levée pour un article non chargé");
        } catch (IllegalArgumentException e) {
            // Vérifie que le message d'exception correspond à ce qui est attendu pour un article non chargé
            assertEquals("L'article à décharger n'est pas trouvé dans le camion.", e.getMessage());
            //System.out.println("Test unload réussi : IllegalArgumentException levée pour un article non chargé.");
        } catch (Exception e) {
            // Si une autre exception est levée, le test échoue
            fail("Une exception inattendue a été levée lors du déchargement d'un article non chargé : " + e.getMessage());
        }
    }

    @Test
    public void testGetCurrentWeight() {
        try {
            // Chargement des articles dans le camion
            camion.load(palette); // Charge la palette dans le camion
            camion.load(vrac);   // Charge le vrac dans le camion

            // Imprime les poids des articles pour le débogage, utile pour vérifier les valeurs lors des tests
            //System.out.println("Poids de la palette: " + palette.getWeight());
            //System.out.println("Poids du vrac: " + vrac.getWeight());

            // Définition du poids attendu total après le chargement
            double expectedWeight = 15.0; // Poids attendu total des articles chargés (5.0 + 10.0)

            // Obtention du poids actuel du camion après le chargement
            double actualWeight = camion.getCurrentWeight();

            // Vérifie que le poids actuel du camion est correct
            // La marge d'erreur tolérée est de 0.01 pour les comparaisons de nombres flottants
            assertEquals("Test échoué : Le poids actuel du camion n'est pas correct.", expectedWeight, actualWeight, 0.01);

            //System.out.println("Test currentWeight réussi : Le poids actuel du camion est correct.");

        } catch (MaxWeightReachedException e) {
            // Si une exception MaxWeightReachedException est levée, cela indique un problème avec le poids maximum du camion
            fail("Une exception MaxWeightReachedException ne devrait pas être levée : " + e.getMessage());
        } catch (MaxVolumeReachedException e) {
            // Si une exception MaxVolumeReachedException est levée, cela indique un problème avec le volume maximum du camion
            fail("Une exception MaxVolumeReachedException ne devrait pas être levée : " + e.getMessage());
        }
    }



    @Test
    public void testGetCurrentVolume() {
        try {
            // Charge la palette dans le camion
            camion.load(palette); // Palette avec un volume de 10.0

            // Charge le vrac dans le camion
            camion.load(vrac);   // Vrac avec un volume de 20.0

            // Volume total attendu après le chargement : 10.0 (palette) + 20.0 (vrac) = 30.0
            double expectedVolume = 30.0;

            // Obtient le volume actuel du camion après le chargement
            double actualVolume = camion.getCurrentVolume();

            // Vérifie que le volume actuel du camion est correct
            // La marge d'erreur tolérée est de 0.01 pour les comparaisons de nombres flottants
            assertEquals("Test échoué : Le volume actuel du camion n'est pas correct.", expectedVolume, actualVolume, 0.01);

            // Affiche un message de réussite si le test passe
            //System.out.println("Test getCurrentVolume réussi : Le volume actuel du camion est correct.");

        } catch (MaxWeightReachedException e) {
            // Si une exception MaxWeightReachedException est levée, cela indique un problème avec le poids maximum du camion
            fail("Une exception MaxWeightReachedException ne devrait pas être levée : " + e.getMessage());
        } catch (MaxVolumeReachedException e) {
            // Si une exception MaxVolumeReachedException est levée, cela indique un problème avec le volume maximum du camion
            fail("Une exception MaxVolumeReachedException ne devrait pas être levée : " + e.getMessage());
        }
    }



    @Test
    public void testGetId() {
        try {
            // Vérifie que l'ID du camion est correctement retourné par la méthode getId()
            assertEquals("C001", camion.getId());

            // Affiche un message de réussite si le test passe, confirmant que l'ID est correct
            //System.out.println("Test getId réussi : L'ID du camion est correct.");
        } catch (AssertionError e) {
            // En cas d'échec de l'assertion, le test échoue avec un message d'erreur
            fail("Le test a échoué : L'ID du camion n'est pas correct.");
        } catch (Exception e) {
            // En cas d'exception inattendue, le test échoue avec un message d'erreur
            fail("Une exception inattendue a été levée : " + e.getMessage());
        }
    }


    @Test
    public void testGetLoadSortedById() {
        try {
            // Chargement de la palette et du vrac dans le camion
            camion.load(palette);
            camion.load(vrac);

            // Obtention de la liste des articles triés par ID
            List<Chargeable> sortedLoad = camion.getLoadSortedById();

            // Vérifie que le premier élément de la liste triée est la palette
            assertEquals(palette, sortedLoad.get(0));
            // Vérifie que le deuxième élément de la liste triée est le vrac
            assertEquals(vrac, sortedLoad.get(1));

            // Affiche un message de réussite si le test passe, confirmant que le tri par ID est correct
            //System.out.println("Test getLoadSortedById réussi : Les articles sont correctement triés par ID.");
        } catch (Exception e) {
            // En cas d'exception, le test échoue avec un message d'erreur
            fail("Une exception inattendue a été levée lors du tri par ID : " + e.getMessage());
        }
    }

    @Test
    public void testGetLoadSortedByWeight() {
        try {
            // Chargement de la palette et du vrac dans le camion
            camion.load(palette);
            camion.load(vrac);

            // Obtention de la liste des articles triés par poids
            List<Chargeable> sortedLoad = camion.getLoadSortedByWeight();

            // Vérifie que le premier élément de la liste triée est le vrac (poids 10.0)
            assertEquals(vrac, sortedLoad.get(0));
            // Vérifie que le deuxième élément de la liste triée est la palette (poids 5.0)
            assertEquals(palette, sortedLoad.get(1));

            // Affiche un message de réussite si le test passe, confirmant que le tri par poids est correct
            //System.out.println("Test getLoadSortedByWeight réussi : Les articles sont correctement triés par poids.");
        } catch (Exception e) {
            // En cas d'exception, le test échoue avec un message d'erreur
            fail("Une exception inattendue a été levée lors du tri par poids : " + e.getMessage());
        }
    }


    @Test
    public void testGetLoadSortedByVolume() {
        try {
            // Chargement de la palette et du vrac dans le camion
            camion.load(palette);
            camion.load(vrac);

            // Obtention de la liste des articles triés par volume
            List<Chargeable> sortedLoad = camion.getLoadSortedByVolume();

            // Vérifie que le premier élément de la liste triée par volume est le vrac (volume 20.0)
            assertEquals(vrac, sortedLoad.get(0));
            // Vérifie que le deuxième élément de la liste triée par volume est la palette (volume 10.0)
            assertEquals(palette, sortedLoad.get(1));

            // Affiche un message de réussite si le test passe, confirmant que le tri par volume est correct
            //System.out.println("Test getLoadSortedByVolume réussi : Les articles sont correctement triés par volume.");
        } catch (Exception e) {
            // En cas d'exception, le test échoue avec un message d'erreur
            fail("Une exception inattendue a été levée lors du tri par volume : " + e.getMessage());
        }
    }
}

