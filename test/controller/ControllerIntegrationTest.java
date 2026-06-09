package controller;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

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
            String dataNascimento, String empCat, boolean ativo,
            String taxId, String gender, String email, String telefone) {
        return new FormEvent(new Object(), name, occupation,
                dataNascimento, empCat, ativo, taxId, gender, email, telefone);
    }

    @Test
    public void testAddPersonResultsInOnePerson() {
        controller.addPerson(buildFormEvent("Lucas Lima", "Analista", "10/01/1995", "CLT", true, "111", "MASCULINO", "", ""));
        assertEquals(1, controller.getPeople().size());
    }

    @Test
    public void testAddPersonPreservesName() {
        controller.addPerson(buildFormEvent("Lucas Lima", "Analista", "10/01/1995", "CLT", true, "111", "MASCULINO", "", ""));
        assertEquals("Lucas Lima", controller.getPeople().get(0).getName());
    }

    @Test
    public void testAddTwoPeople() {
        controller.addPerson(buildFormEvent("Lucas Lima",  "Analista",  "10/01/1995", "CLT", true,  "111", "MASCULINO", "", ""));
        controller.addPerson(buildFormEvent("Carla Souza", "Designer",  "05/06/1998", "PJ",  false, "222", "FEMININO",  "", ""));
        assertEquals(2, controller.getPeople().size());
    }

    @Test
    public void testAddPersonMapsCLT() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "CLT", true, "1", "MASCULINO", "", ""));
        assertEquals(EmploymentCategory.CLT, controller.getPeople().get(0).getEmpCat());
    }

    @Test
    public void testAddPersonMapsPJ() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "PJ", true, "1", "MASCULINO", "", ""));
        assertEquals(EmploymentCategory.PJ, controller.getPeople().get(0).getEmpCat());
    }

    @Test
    public void testAddPersonMapsEstagiario() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/2003", "Estagiario", true, "1", "MASCULINO", "", ""));
        assertEquals(EmploymentCategory.ESTAGIARIO, controller.getPeople().get(0).getEmpCat());
    }

    @Test
    public void testAddPersonMapsTerceirizado() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "Terceirizado", true, "1", "MASCULINO", "", ""));
        assertEquals(EmploymentCategory.TERCEIRIZADO, controller.getPeople().get(0).getEmpCat());
    }

    @Test
    public void testAddPersonGenderMasculino() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "CLT", true, "1", "MASCULINO", "", ""));
        assertEquals(Gender.MASCULINO, controller.getPeople().get(0).getGender());
    }

    @Test
    public void testAddPersonGenderFeminino() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "CLT", true, "1", "FEMININO", "", ""));
        assertEquals(Gender.FEMININO, controller.getPeople().get(0).getGender());
    }

    @Test
    public void testAddPersonAtivoTrue() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "CLT", true, "1", "MASCULINO", "", ""));
        assertTrue(controller.getPeople().get(0).isAtivo());
    }

    @Test
    public void testAddPersonAtivoFalse() {
        controller.addPerson(buildFormEvent("Test", "occ", "01/01/1990", "CLT", false, "1", "MASCULINO", "", ""));
        assertFalse(controller.getPeople().get(0).isAtivo());
    }

    @Test
    public void testAddPersonPreservesDataNascimento() {
        controller.addPerson(buildFormEvent("Test", "occ", "15/08/1985", "CLT", true, "1", "MASCULINO", "t@t.com", "11999"));
        assertEquals("15/08/1985", controller.getPeople().get(0).getDataNascimento());
    }

    @Test
    public void testAddPersonPreservesEmail() {
        controller.addPerson(buildFormEvent("Test", "occ", "15/08/1985", "CLT", true, "1", "MASCULINO", "rh@empresa.com", ""));
        assertEquals("rh@empresa.com", controller.getPeople().get(0).getEmail());
    }

    @Test
    public void testRemovePersonReducesCount() {
        controller.addPerson(buildFormEvent("Lucas", "Analista", "01/01/1990", "CLT", true, "111", "MASCULINO", "", ""));
        controller.removePerson(0);
        assertEquals(0, controller.getPeople().size());
    }

    @Test
    public void testRemoveCorrectPerson() {
        controller.addPerson(buildFormEvent("Primeiro", "occ", "01/01/1990", "CLT",  true,  "1", "MASCULINO", "", ""));
        controller.addPerson(buildFormEvent("Segundo",  "occ", "02/02/1992", "PJ",   false, "2", "FEMININO",  "", ""));
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
            controller.getPeople().add(new Person("X", "Y", "01/01/1990",
                    EmploymentCategory.TERCEIRIZADO, "0", false, Gender.FEMININO));
            fail("Lista deveria ser imutavel");
        } catch (UnsupportedOperationException e) {}
    }
}
