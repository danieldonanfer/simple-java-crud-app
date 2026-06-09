package model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person("Ana Silva", "Engenheira", "15/03/1990",
                EmploymentCategory.CLT, "123.456.789-00", true, Gender.FEMININO,
                "ana@email.com", "11999999999");
    }

    @Test public void testGetName() { assertEquals("Ana Silva", person.getName()); }
    @Test public void testGetOccupation() { assertEquals("Engenheira", person.getOccupation()); }
    @Test public void testGetDataNascimento() { assertEquals("15/03/1990", person.getDataNascimento()); }
    @Test public void testGetEmpCat() { assertEquals(EmploymentCategory.CLT, person.getEmpCat()); }
    @Test public void testGetTaxId() { assertEquals("123.456.789-00", person.getTaxId()); }
    @Test public void testIsAtivo_true() { assertTrue(person.isAtivo()); }
    @Test public void testGetGender() { assertEquals(Gender.FEMININO, person.getGender()); }
    @Test public void testGetEmail() { assertEquals("ana@email.com", person.getEmail()); }
    @Test public void testGetTelefone() { assertEquals("11999999999", person.getTelefone()); }
    @Test public void testIdIsPositive() { assertTrue(person.getId() > 0); }

    @Test public void testSetName() { person.setName("Joao"); assertEquals("Joao", person.getName()); }
    @Test public void testSetOccupation() { person.setOccupation("Dev"); assertEquals("Dev", person.getOccupation()); }
    @Test public void testSetTaxId() { person.setTaxId("000"); assertEquals("000", person.getTaxId()); }
    @Test public void testSetAtivo_false() { person.setAtivo(false); assertFalse(person.isAtivo()); }
    @Test public void testSetEmail() { person.setEmail("novo@email.com"); assertEquals("novo@email.com", person.getEmail()); }
    @Test public void testSetTelefone() { person.setTelefone("11000000000"); assertEquals("11000000000", person.getTelefone()); }
    @Test public void testSetDataNascimento() { person.setDataNascimento("01/01/2000"); assertEquals("01/01/2000", person.getDataNascimento()); }
    @Test public void testSetGender() { person.setGender(Gender.MASCULINO); assertEquals(Gender.MASCULINO, person.getGender()); }
    @Test public void testSetEmpCat() { person.setEmpCat(EmploymentCategory.PJ); assertEquals(EmploymentCategory.PJ, person.getEmpCat()); }

    @Test
    public void testConstructorWithExplicitId() {
        Person p = new Person(99, "Carlos", "Analista", "10/10/1985",
                EmploymentCategory.TERCEIRIZADO, "111", false, Gender.MASCULINO);
        assertEquals(99, p.getId());
        assertEquals("Carlos", p.getName());
    }

    @Test public void testSetId() { person.setId(42); assertEquals(42, person.getId()); }

    @Test
    public void testToStringContainsName() {
        assertTrue(person.toString().contains("Ana Silva"));
    }

    @Test public void testPersonNotNull() { assertNotNull(person); }

    @Test
    public void testTwoPersonsHaveDifferentIds() {
        Person p1 = new Person("P1", "occ", "01/01/1990", EmploymentCategory.CLT, "1", true, Gender.MASCULINO);
        Person p2 = new Person("P2", "occ", "02/02/1992", EmploymentCategory.PJ,  "2", false, Gender.FEMININO);
        assertNotEquals(p1.getId(), p2.getId());
    }
}
