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

public class ControllerIntegrationTest {

    private Controller controller;

    @Before
    public void setUp() {
        controller = new Controller();
    }

    private FormEvent buildFormEvent(String name, String occupation,
            int ageCatId, String empCat, boolean ativo,
            String taxId, String gender) {
        return new FormEvent(new Object(), name, occupation,
                ageCatId, empCat, ativo, taxId, gender);
    }

    @Test
    public void testAddPersonResultsInOnePerson() {
        controller.addPerson(buildFormEvent("Lucas Lima", "Professor", 0, "Empregado", true, "999", "MASCULINO"));
        assertEquals(1, controller.getPeople().size());
    }

    @Test
    public void testAddPersonPreservesName() {
        controller.addPerson(buildFormEvent("Lucas Lima", "Professor", 0, "Empregado", true, "999", "MASCULINO"));
        assertEquals("Lucas Lima", controller.getPeople().get(0).getName());
    }

    @Test
    public void testAddTwoPeople() {
        controller.addPerson(buildFormEvent("Lucas Lima", "Professor", 0, "Empregado", true, "111", "MASCULINO"));
        controller.addPerson(buildFormEvent("Carla Souza", "Designer", 1, "Desempregado", false, "222", "FEMININO"));
        assertEquals(2, controller.getPeople().size());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Crianca() {
        controller.addPerson(buildFormEvent("Test", "occ", 0, "Empregado", true, "1", "MASCULINO"));
        assertEquals(AgeCategory.CRIANCA, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Adolescente() {
        controller.addPerson(buildFormEvent("Test", "occ", 1, "Empregado", true, "1", "MASCULINO"));
        assertEquals(AgeCategory.ADOLESCENTE, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Adulto() {
        controller.addPerson(buildFormEvent("Test", "occ", 2, "Empregado", true, "1", "MASCULINO"));
        assertEquals(AgeCategory.ADULTO, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonMapsAgeCategoryCorrectly_Idoso() {
        controller.addPerson(buildFormEvent("Test", "occ", 4, "Empregado", true, "1", "MASCULINO"));
        assertEquals(AgeCategory.IDOSO, controller.getPeople().get(0).getAgeCategory());
    }

    @Test
    public void testAddPersonGenderMasculino() {
        controller.addPerson(buildFormEvent("Test", "occ", 0, "Empregado", true, "1", "MASCULINO"));
        assertEquals(Gender.MASCULINO, controller.getPeople().get(0).getGender());
    }

    @Test
    public void testAddPersonGenderFeminino() {
        controller.addPerson(buildFormEvent("Test", "occ", 0, "Empregado", true, "1", "FEMININO"));
        assertEquals(Gender.FEMININO, controller.getPeople().get(0).getGender());
    }

    @Test
    public void testAddPersonAtivoTrue() {
        controller.addPerson(buildFormEvent("Test", "occ", 0, "Empregado", true, "1", "MASCULINO"));
        assertTrue(controller.getPeople().get(0).isAtivo());
    }

    @Test
    public void testAddPersonAtivoFalse() {
        controller.addPerson(buildFormEvent("Test", "occ", 0, "Empregado", false, "1", "MASCULINO"));
        assertFalse(controller.getPeople().get(0).isAtivo());
    }

    @Test
    public void testRemovePersonReducesCount() {
        controller.addPerson(buildFormEvent("Lucas Lima", "Professor", 0, "Empregado", true, "111", "MASCULINO"));
        controller.removePerson(0);
        assertEquals(0, controller.getPeople().size());
    }

    @Test
    public void testRemoveCorrectPerson() {
        controller.addPerson(buildFormEvent("Primeiro", "occ", 0, "Empregado", true, "1", "MASCULINO"));
        controller.addPerson(buildFormEvent("Segundo", "occ", 1, "Desempregado", false, "2", "FEMININO"));
        controller.removePerson(0);
        assertEquals("Segundo", controller.getPeople().get(0).getName());
    }

    @Test
    public void testGetPeopleInitiallyEmpty() {
        assertTrue(controller.getPeople().isEmpty());
    }

    @Test
    public void testGetPeopleReturnsUnmodifiableList() {
        try {
            controller.getPeople().add(new Person("X", "Y", AgeCategory.IDOSO,
                    EmploymentCategory.other, "0", false, Gender.FEMININO));
            fail("Lista deveria ser imutavel");
        } catch (UnsupportedOperationException e) {}
    }
}
