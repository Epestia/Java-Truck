package test.be.ipam.menegon.model.loadmaster;

import be.ipam.menegon.model.exceptions.MaxVolumeReachedException;
import be.ipam.menegon.model.exceptions.MaxWeightReachedException;
import be.ipam.menegon.model.loadmaster.Loadmaster;
import be.ipam.menegon.model.truck.Camion;
import be.ipam.menegon.model.truck.Chargeable;
import be.ipam.menegon.model.truck.Palette;
import be.ipam.menegon.model.truck.Vrac;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class LoadMasterTest extends TestCase {
    private Loadmaster loadmaster;
    private Camion camion;
    private Camion camion1;
    private Camion camion2;
    private Chargeable palette;
    private Chargeable vrac;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        loadmaster = new Loadmaster("Loadmaster");

        // Utilisation de IDs uniques pour éviter les conflits
        camion = new Camion("C001", 10000, 50.0, loadmaster);
        camion1 = new Camion("C002", 1000, 50.0, loadmaster);
        camion2 = new Camion("C003", 2000, 100.0, loadmaster);

        palette = new Palette("P001", 5, 10);
        vrac = new Vrac("V001", 10, 5);
    }



    @Test
    public void testConstructor() {
        try {
            Loadmaster lm = new Loadmaster("Loadmaster");
            assertFalse("Le nom du Loadmaster ne doit pas être vide", lm.getNom().trim().isEmpty());
        } catch (IllegalArgumentException e) {
            fail("Une exception ne devrait pas être levée pour un nom valide : " + e.getMessage());
        }

        try {
            new Loadmaster("");
            fail("Une exception IllegalArgumentException devrait être levée pour un nom vide");
        } catch (IllegalArgumentException e) {
            assertEquals("Le message de l'exception n'est pas correct", "Le nom du Loadmaster ne peut pas être vide.", e.getMessage());
        }

        try {
            new Loadmaster(null);
            fail("Une exception IllegalArgumentException devrait être levée pour un nom null");
        } catch (IllegalArgumentException e) {
            assertEquals("Le message de l'exception n'est pas correct", "Le nom du Loadmaster ne peut pas être vide.", e.getMessage());
        }
    }

    @Test
    public void testAddCamion() {

            Loadmaster localLoadmaster = new Loadmaster("LocalLoadmaster");
            Camion localCamion = new Camion("C006", 10000, 50.0, localLoadmaster);

            // Test 1: Ajouter un camion null
            try {
                localLoadmaster.addCamion(null);
                fail("Une exception devrait être levée lorsqu'on essaie d'ajouter un camion nul.");
            } catch (IllegalArgumentException e) {
                assertEquals("Le message de l'exception n'est pas correct", "Camion ne peut pas être nul.", e.getMessage());
            }

            // Test 2: Ajouter un camion valide
            try {
                localLoadmaster.addCamion(localCamion);
                Map<String, Camion> camionMap = localLoadmaster.getCamions();
                assertTrue("Le camion devrait être dans la liste", camionMap.containsKey(localCamion.getId()));
            } catch (Exception e) {
                fail("Une exception inattendue a été levée lors de l'ajout d'un camion valide : " + e.getMessage());
            }

            // Test 3: Ajouter un camion déjà existant
            try {
                localLoadmaster.addCamion(localCamion);
                fail("Une exception devrait être levée lorsqu'on essaie d'ajouter un camion déjà existant.");
            } catch (IllegalArgumentException e) {
                assertEquals("Le message de l'exception n'est pas correct", "Le camion existe déjà dans ce Loadmaster.", e.getMessage());
            }
        }



        @Test
    public void testLoadItem() {
        // Test 1: Ajouter le camion au Loadmaster
        try {
            loadmaster.addCamion(camion);
            //System.out.println("Test loadItem 1 réussi : Le camion a été ajouté au Loadmaster.");
        } catch (Exception e) {
            fail("Une exception a été levée lors de l'ajout du camion au Loadmaster : " + e.getMessage());
        }

        // Test 2: Charger la palette dans le camion
        try {
            camion.load(palette); // Charge Palette
            //System.out.println("Poids du camion après chargement de la palette: " + camion.getCurrentWeight());
            // Vérifie le poids après le chargement de la palette
            assertEquals("Le poids après chargement de la palette n'est pas correct", 5.0, camion.getCurrentWeight(), 0.01);
            //System.out.println("Test loadItem 2 réussi  : Poids après chargement de la palette est correct.");
        } catch (MaxWeightReachedException e) {
            fail("Une exception MaxWeightReachedException a été levée lors du chargement de la palette : " + e.getMessage());
        } catch (MaxVolumeReachedException e) {
            fail("Une exception MaxVolumeReachedException a été levée lors du chargement de la palette : " + e.getMessage());
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du chargement de la palette : " + e.getMessage());
        }

        // Test 3: Charger le vrac dans le camion
        try {
            camion.load(vrac);    // Charge Vrac
            //System.out.println("Poids du camion après chargement du vrac: " + camion.getCurrentWeight());
            // Vérifie que les éléments ont été correctement chargés
            assertEquals("Le poids actuel du camion n'est pas correct", 15.0, camion.getCurrentWeight(), 0.01);
            assertEquals("Le volume actuel du camion n'est pas correct", 15.0, camion.getCurrentVolume(), 0.01);
            //System.out.println("Test loadItem 3 réussi  : Poids et volume après chargement du vrac sont corrects.");
    } catch (MaxWeightReachedException e) {
            fail("Une exception MaxWeightReachedException a été levée lors du chargement du vrac : " + e.getMessage());
        } catch (MaxVolumeReachedException e) {
            fail("Une exception MaxVolumeReachedException a été levée lors du chargement du vrac : " + e.getMessage());
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du chargement du vrac : " + e.getMessage());
        }

        // Test 4: Vérifier que les éléments sont bien dans la liste du chargement
        try {
            List<Chargeable> load = camion.getLoadSortedById();
            assertTrue("La palette devrait être chargée", load.contains(palette));
            assertTrue("Le vrac devrait être chargé", load.contains(vrac));
            //System.out.println("Test loadItem 4 réussi : Les éléments chargés sont correctement présents dans la liste du chargement.");
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors de la vérification des éléments chargés : " + e.getMessage());
        }
    }


    @Test
    public void testUnloadItem() {
        // Créer un Loadmaster local pour ce test
        Loadmaster localLoadmaster = new Loadmaster("LocalLoadmaster");

        // Créer un nouveau Camion pour ce test afin d'éviter les conflits avec d'autres tests
        Camion localCamion = new Camion("C004", 10000, 50.0, localLoadmaster);

        try {
            // Ajouter le camion au Loadmaster local
            localLoadmaster.addCamion(localCamion);

            // Charger la palette
            localCamion.load(palette); // Poids: 5.0, Volume: 10.0

            // Vérifier le poids après chargement de la palette
            assertEquals("Le poids actuel du camion après chargement de la palette n'est pas correct", 5.0, localCamion.getCurrentWeight(), 0.01);

            // Charger le vrac
            localCamion.load(vrac);    // Poids: 10.0, Volume: 5.0

            // Vérifier le poids après chargement du vrac
            assertEquals("Le poids actuel du camion après chargement du vrac n'est pas correct", 15.0, localCamion.getCurrentWeight(), 0.01);

            // Décharger la palette
            localLoadmaster.unloadItem(localCamion.getId(), palette);

            // Vérifier que la palette est bien déchargée
            List<Chargeable> load = localCamion.getLoadSortedById();
            assertFalse("La palette devrait être déchargée", load.contains(palette));

            // Vérifier le poids après déchargement de la palette
            assertEquals("Le poids actuel du camion n'est pas correct après déchargement", 10.0, localCamion.getCurrentWeight(), 0.01);

            // Vérifier le volume après déchargement de la palette
            assertEquals("Le volume actuel du camion n'est pas correct après déchargement", 5.0, localCamion.getCurrentVolume(), 0.01);

        } catch (MaxWeightReachedException e) {
            fail("Une exception MaxWeightReachedException a été levée lors du chargement des articles : " + e.getMessage());
        } catch (MaxVolumeReachedException e) {
            fail("Une exception MaxVolumeReachedException a été levée lors du chargement des articles : " + e.getMessage());
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du déchargement : " + e.getMessage());
        }
    }


    @Test
    public void testGetCamions() {
        // Création d'un Loadmaster local pour éviter les interférences avec d'autres tests
        Loadmaster localLoadmaster = new Loadmaster("LocalLoadmaster");
        Camion localCamion1 = new Camion("C007", 10000, 50.0, localLoadmaster);
        Camion localCamion2 = new Camion("C008", 1000, 50.0, localLoadmaster);

        try {
            // Ajoutez les camions au Loadmaster
            localLoadmaster.addCamion(localCamion1);
            localLoadmaster.addCamion(localCamion2);

            // Obtenez la carte des camions
            Map<String, Camion> camions = localLoadmaster.getCamions();

            // Vérifiez que la carte des camions n'est pas nulle
            assertNotNull("La carte des camions ne devrait pas être nulle", camions);

            // Vérifiez que la carte des camions contient les camions ajoutés
            assertNotNull("Le camion1 ne devrait pas être nul", camions.get(localCamion1.getId()));
            assertNotNull("Le camion2 ne devrait pas être nul", camions.get(localCamion2.getId()));

            // Vérifiez que la carte des camions correspond aux instances ajoutées
            assertEquals("Le camion1 devrait correspondre à l'instance ajoutée", localCamion1, camions.get(localCamion1.getId()));
            assertEquals("Le camion2 devrait correspondre à l'instance ajoutée", localCamion2, camions.get(localCamion2.getId()));

            // Vérifiez que la carte des camions a la taille correcte
            assertEquals("La taille de la carte des camions n'est pas correcte", 2, camions.size());

        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du test getCamions : " + e.getMessage());
        }
    }


    @Test
    public void testGetNom() {
        try {
            // Vérifier que le nom du LoadMaster est correct
            assertEquals("Le nom du LoadMaster n'est pas correct", "LOADMASTER", loadmaster.getNom());

            //System.out.println("Test getNom réussi : Le nom du LoadMaster est correct.");
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du test getNom : " + e.getMessage());
        }
    }

    @Test
    public void testRemoveCamion() {
        Loadmaster localLoadmaster = new Loadmaster("LocalLoadmaster");
        Camion localCamion = new Camion("C003", 10000, 50.0, localLoadmaster);

        try {
            localLoadmaster.addCamion(localCamion);
            Map<String, Camion> camions = localLoadmaster.getCamions();
            assertTrue("Le camion devrait être présent avant la suppression", camions.containsKey(localCamion.getId()));

            localLoadmaster.removeCamion(localCamion.getId());
            camions = localLoadmaster.getCamions();
            assertFalse("Le camion devrait être absent après la suppression", camions.containsKey(localCamion.getId()));

            try {
                localLoadmaster.removeCamion("NonExistentCamionId");
                fail("Une exception IllegalArgumentException devrait être levée pour un camion inexistant.");
            } catch (IllegalArgumentException e) {
                assertEquals("Le message de l'exception n'est pas correct", "Le camion avec cet identifiant n'existe pas.", e.getMessage());
            }

            try {
                localLoadmaster.removeCamion(null);
                fail("Une exception IllegalArgumentException devrait être levée pour un identifiant null.");
            } catch (IllegalArgumentException e) {
                assertEquals("Le message de l'exception n'est pas correct", "L'identifiant du camion ne peut pas être vide.", e.getMessage());
            }

            try {
                localLoadmaster.removeCamion("");
                fail("Une exception IllegalArgumentException devrait être levée pour un identifiant vide.");
            } catch (IllegalArgumentException e) {
                assertEquals("Le message de l'exception n'est pas correct", "L'identifiant du camion ne peut pas être vide.", e.getMessage());
            }
        } catch (Exception e) {
            fail("Une exception inattendue a été levée lors du test de suppression de camion : " + e.getMessage());
        }
    }
}








