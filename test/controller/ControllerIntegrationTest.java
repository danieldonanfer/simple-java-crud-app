package controller;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

import model.AgeCategory;
import model.EmploymentCategory;
import model.Gender;
import model.Person;
import gui.FormEvent;

/**
 * Testes de integração entre Controller e Database (em memória).
 * Valida o fluxo completo de adicionar e remover pessoas sem banco de dados.
 */
public class ControllerIntegrationTest {

    private Controller controller;

    @Before
    public void setUp() {
        controller = new Controller();
    }

    private FormEvent buildFormEvent(String name, String occupation,
            int ageCatId, String empCat, boolean isUs,
            String taxId, String gender) {
        FormEvent ev = new FormEvent(new Object(), name, occupation,
                ageCatId, empCat, isUs, taxId, gender);
        return ev;
    }

    // --- Testes de addPerson via FormEvent ---

    @Test
    public void testAddPersonResultsInOnePerson() {
        FormEvent ev = buildFormEvent("Lucas Lima", "Professor",
                0, "yes", true, "999.888.777-66", "yes");
        controller.addPerson(ev);
        assertEquals(1, controller.getPeople().size());
    }

    @Test
    public void testAddPersonPreservesName() {
        FormEvent ev = buildFormEvent("Lucas Lima", "Professor",
                0, "yes", true, "999.888.777-66", "yes");
        controller.addPerson(ev);
        assertEquals("Lucas Lima", controller.getPeople().get(0).getName());
    }

    @Test
    public void testAddTwoPeople() {
        FormEvent ev1 = buildFormEvent("Lucas Lima", "Professor",
                0, "yes", true, "111", "yes");
        FormEvent ev2 = buildFormEvent("Carla Souza", "Designer",
                1, "no", false, "222", "no");
        controller.addPerson(ev1);
        controller.addPerson(ev2);
        assertEquals(2, controller.getPeople().size());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Nissan() {
        FormEvent ev = buildFormEvent("Test", "occ", 0, "yes", true, "1", "yes");
        controller.addPerson(ev);
        assertEquals(AgeCategory.Nissan, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Mazda() {
        FormEvent ev = buildFormEvent("Test", "occ", 1, "yes", true, "1", "yes");
        controller.addPerson(ev);
        assertEquals(AgeCategory.Mazda, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_LandRover() {
        FormEvent ev = buildFormEvent("Test", "occ", 2, "yes", true, "1", "yes");
        controller.addPerson(ev);
        assertEquals(AgeCategory.Land_Rover, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Honda() {
        FormEvent ev = buildFormEvent("Test", "occ", 4, "yes", true, "1", "yes");
        controller.addPerson(ev);
        assertEquals(AgeCategory.Honda, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonGenderYes() {
        FormEvent ev = buildFormEvent("Test", "occ", 0, "yes", true, "1", "yes");
        controller.addPerson(ev);
        assertEquals(Gender.yes, controller.getPeople().get(0).getGender());
    }

    @Test
    public void testAddPersonGenderNo() {
        FormEvent ev = buildFormEvent("Test", "occ", 0, "yes", true, "1", "no");
        controller.addPerson(ev);
        assertEquals(Gender.no, controller.getPeople().get(0).getGender());
    }

    @Test
    public void testAddPersonUsCitizenTrue() {
        FormEvent ev = buildFormEvent("Test", "occ", 0, "yes", true, "1", "yes");
        controller.addPerson(ev);
        assertTrue(controller.getPeople().get(0).isUsCitizen());
    }

    @Test
    public void testAddPersonUsCitizenFalse() {
        FormEvent ev = buildFormEvent("Test", "occ", 0, "yes", false, "1", "yes");
        controller.addPerson(ev);
        assertFalse(controller.getPeople().get(0).isUsCitizen());
    }

    // --- Testes de removePerson ---

    @Test
    public void testRemovePersonReducesCount() {
        FormEvent ev = buildFormEvent("Lucas Lima", "Professor",
                0, "yes", true, "111", "yes");
        controller.addPerson(ev);
        controller.removePerson(0);
        assertEquals(0, controller.getPeople().size());
    }

    @Test
    public void testRemoveCorrectPerson() {
        controller.addPerson(buildFormEvent("Primeiro", "occ", 0, "yes", true, "1", "yes"));
        controller.addPerson(buildFormEvent("Segundo", "occ", 1, "no", false, "2", "no"));
        controller.removePerson(0);
        assertEquals("Segundo", controller.getPeople().get(0).getName());
    }

    // --- Testes de getPeople ---

    @Test
    public void testGetPeopleInitiallyEmpty() {
        assertTrue(controller.getPeople().isEmpty());
    }

    @Test
    public void testGetPeopleReturnsUnmodifiableList() {
        List<Person> people = controller.getPeople();
        try {
            people.add(new Person("X", "Y", AgeCategory.Honda,
                    EmploymentCategory.other, "0", false, Gender.no));
            fail("Lista deveria ser imutável");
        } catch (UnsupportedOperationException e) {
            // Comportamento esperado
        }
    }
}
