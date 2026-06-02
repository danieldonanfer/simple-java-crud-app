package model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Testes unitários para a classe Database.
 * Cobre operações em memória e persistência em arquivo.
 * Não requer conexão MySQL (testa apenas camada em memória e arquivo).
 */
public class DatabaseTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private Database db;
    private Person p1;
    private Person p2;

    @Before
    public void setUp() {
        db = new Database();
        p1 = new Person("Maria Oliveira", "Médica", AgeCategory.Nissan,
                EmploymentCategory.yes, "111.222.333-44", true, Gender.yes);
        p2 = new Person("Pedro Costa", "Advogado", AgeCategory.Mazda,
                EmploymentCategory.no, "555.666.777-88", false, Gender.no);
    }

    // --- Testes de addPerson ---

    @Test
    public void testAddPersonIncreasesCount() {
        db.addPerson(p1);
        assertEquals(1, db.getPeople().size());
    }

    @Test
    public void testAddMultiplePeople() {
        db.addPerson(p1);
        db.addPerson(p2);
        assertEquals(2, db.getPeople().size());
    }

    @Test
    public void testAddPersonPreservesName() {
        db.addPerson(p1);
        assertEquals("Maria Oliveira", db.getPeople().get(0).getName());
    }

    // --- Testes de removePerson ---

    @Test
    public void testRemovePersonDecreasesCount() {
        db.addPerson(p1);
        db.addPerson(p2);
        db.removePerson(0);
        assertEquals(1, db.getPeople().size());
    }

    @Test
    public void testRemovePersonCorrectElement() {
        db.addPerson(p1);
        db.addPerson(p2);
        db.removePerson(0);
        assertEquals("Pedro Costa", db.getPeople().get(0).getName());
    }

    // --- Testes de getPeople ---

    @Test
    public void testGetPeopleReturnsEmptyInitially() {
        assertTrue(db.getPeople().isEmpty());
    }

    @Test
    public void testGetPeopleIsUnmodifiable() {
        db.addPerson(p1);
        List<Person> people = db.getPeople();
        try {
            people.add(p2);
            fail("Lista deveria ser imutável");
        } catch (UnsupportedOperationException e) {
            // Comportamento esperado
        }
    }

    @Test
    public void testGetPeopleNotNull() {
        assertNotNull(db.getPeople());
    }

    // --- Testes de persistência em arquivo ---

    @Test
    public void testSaveAndLoadFromFile() throws IOException {
        db.addPerson(p1);
        db.addPerson(p2);

        File file = tempFolder.newFile("people.dat");
        db.saveToFile(file);

        Database db2 = new Database();
        db2.loadFromFile(file);

        assertEquals(2, db2.getPeople().size());
        assertEquals("Maria Oliveira", db2.getPeople().get(0).getName());
    }

    @Test
    public void testSaveToFileCreatesFile() throws IOException {
        db.addPerson(p1);
        File file = tempFolder.newFile("output.dat");
        db.saveToFile(file);
        assertTrue("Arquivo deve existir após save", file.exists());
        assertTrue("Arquivo não deve estar vazio", file.length() > 0);
    }

    @Test
    public void testLoadFromFileClearsPreviousPeople() throws IOException {
        db.addPerson(p1);
        File file = tempFolder.newFile("people2.dat");
        db.saveToFile(file);

        // Adiciona mais uma pessoa DEPOIS do save
        db.addPerson(p2);

        Database db2 = new Database();
        db2.addPerson(new Person("Extra", "Extra", AgeCategory.Honda,
                EmploymentCategory.other, "000", false, Gender.no));
        db2.loadFromFile(file);

        assertEquals(1, db2.getPeople().size());
        assertEquals("Maria Oliveira", db2.getPeople().get(0).getName());
    }

    @Test
    public void testSaveAndLoadPreservesAllFields() throws IOException {
        db.addPerson(p1);
        File file = tempFolder.newFile("fields.dat");
        db.saveToFile(file);

        Database db2 = new Database();
        db2.loadFromFile(file);

        Person loaded = db2.getPeople().get(0);
        assertEquals(p1.getName(), loaded.getName());
        assertEquals(p1.getOccupation(), loaded.getOccupation());
        assertEquals(p1.getAgeCategory(), loaded.getAgeCategory());
        assertEquals(p1.getTaxId(), loaded.getTaxId());
        assertEquals(p1.isUsCitizen(), loaded.isUsCitizen());
        assertEquals(p1.getGender(), loaded.getGender());
    }
}
