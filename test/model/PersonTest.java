package model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Testes unitários para a classe Person.
 * Cobre criação, getters, setters e comportamento do ID.
 */
public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person("Ana Silva", "Engenheira", AgeCategory.ADULTO,
                EmploymentCategory.yes, "123.456.789-00", true, Gender.yes);
    }

    // --- Testes de criação e getters ---

    @Test
    public void testGetName() {
        assertEquals("Ana Silva", person.getName());
    }

    @Test
    public void testGetOccupation() {
        assertEquals("Engenheira", person.getOccupation());
    }

    @Test
    public void testGetAgeCategory() {
        assertEquals(AgeCategory.Nissan, person.getAgeCategory());
    }

    @Test
    public void testGetEmpCat() {
        assertEquals(EmploymentCategory.yes, person.getEmpCat());
    }

    @Test
    public void testGetTaxId() {
        assertEquals("123.456.789-00", person.getTaxId());
    }

    @Test
    public void testIsUsCitizen_true() {
        assertTrue(person.isUsCitizen());
    }

    @Test
    public void testGetGender() {
        assertEquals(Gender.yes, person.getGender());
    }

    @Test
    public void testIdIsPositive() {
        assertTrue("ID deve ser positivo", person.getId() > 0);
    }

    // --- Testes de setters ---

    @Test
    public void testSetName() {
        person.setName("João Santos");
        assertEquals("João Santos", person.getName());
    }

    @Test
    public void testSetOccupation() {
        person.setOccupation("Desenvolvedor");
        assertEquals("Desenvolvedor", person.getOccupation());
    }

    @Test
    public void testSetTaxId() {
        person.setTaxId("987.654.321-00");
        assertEquals("987.654.321-00", person.getTaxId());
    }

    @Test
    public void testSetUsCitizen_false() {
        person.setUsCitizen(false);
        assertFalse(person.isUsCitizen());
    }

    @Test
    public void testSetAgeCategory() {
        person.setAgeCategory(AgeCategory.ADOLESCENTE);
        assertEquals(AgeCategory.ADOLESCENTE, person.getAgeCategory());
    }

    @Test
    public void testSetGender() {
        person.setGender(Gender.no);
        assertEquals(Gender.no, person.getGender());
    }

    @Test
    public void testSetEmpCat() {
        person.setEmpCat(EmploymentCategory.no);
        assertEquals(EmploymentCategory.no, person.getEmpCat());
    }

    // --- Testes de construtor com ID explícito ---

    @Test
    public void testConstructorWithExplicitId() {
        Person p = new Person(99, "Carlos", "Analista", AgeCategory.IDOSO,
                EmploymentCategory.sklad, "111", false, Gender.no);
        assertEquals(99, p.getId());
        assertEquals("Carlos", p.getName());
    }

    @Test
    public void testSetId() {
        person.setId(42);
        assertEquals(42, person.getId());
    }

    // --- Teste de toString ---

    @Test
    public void testToStringContainsName() {
        String result = person.toString();
        assertTrue("toString deve conter o nome", result.contains("Ana Silva"));
    }

    // --- Teste de não-nulidade ---

    @Test
    public void testPersonNotNull() {
        assertNotNull(person);
    }

    @Test
    public void testTwoPersonsHaveDifferentIds() {
        Person p1 = new Person("P1", "occ", AgeCategory.CRIANCA,
                EmploymentCategory.yes, "1", true, Gender.yes);
        Person p2 = new Person("P2", "occ", AgeCategory.ADOLESCENTE,
                EmploymentCategory.no, "2", false, Gender.no);
        assertNotEquals("Dois Person distintos não devem ter o mesmo ID", p1.getId(), p2.getId());
    }
}
