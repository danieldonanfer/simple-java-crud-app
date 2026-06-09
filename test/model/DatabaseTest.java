package model;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DatabaseTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private Database db;
    private Person p1;
    private Person p2;

    @Before
    public void setUp() {
        db = new Database();
        p1 = new Person("Maria Oliveira", "Medica", "10/05/1985",
                EmploymentCategory.CLT, "111.222.333-44", true, Gender.FEMININO,
                "maria@email.com", "11900000001");
        p2 = new Person("Pedro Costa", "Advogado", "22/08/1990",
                EmploymentCategory.PJ, "555.666.777-88", false, Gender.MASCULINO,
                "pedro@email.com", "11900000002");
    }

    @Test public void testAddPersonIncreasesCount() { db.addPerson(p1); assertEquals(1, db.getPeople().size()); }
    @Test public void testAddMultiplePeople() { db.addPerson(p1); db.addPerson(p2); assertEquals(2, db.getPeople().size()); }
    @Test public void testAddPersonPreservesName() { db.addPerson(p1); assertEquals("Maria Oliveira", db.getPeople().get(0).getName()); }

    @Test
    public void testRemovePersonDecreasesCount() {
        db.addPerson(p1); db.addPerson(p2);
        db.removePerson(0);
        assertEquals(1, db.getPeople().size());
    }

    @Test
    public void testRemovePersonCorrectElement() {
        db.addPerson(p1); db.addPerson(p2);
        db.removePerson(0);
        assertEquals("Pedro Costa", db.getPeople().get(0).getName());
    }

    @Test public void testGetPeopleReturnsEmptyInitially() { assertTrue(db.getPeople().isEmpty()); }

    @Test
    public void testGetPeopleIsUnmodifiable() {
        db.addPerson(p1);
        try {
            db.getPeople().add(p2);
            fail("Lista deveria ser imutavel");
        } catch (UnsupportedOperationException e) {}
    }

    @Test public void testGetPeopleNotNull() { assertNotNull(db.getPeople()); }

    @Test
    public void testSaveAndLoadFromFile() throws IOException {
        db.addPerson(p1); db.addPerson(p2);
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
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void testLoadFromFileClearsPreviousPeople() throws IOException {
        db.addPerson(p1);
        File file = tempFolder.newFile("people2.dat");
        db.saveToFile(file);
        Database db2 = new Database();
        db2.addPerson(new Person("Extra", "Extra", "01/01/2000",
                EmploymentCategory.ESTAGIARIO, "000", false, Gender.MASCULINO));
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
        assertEquals(p1.getName(),            loaded.getName());
        assertEquals(p1.getOccupation(),      loaded.getOccupation());
        assertEquals(p1.getDataNascimento(),  loaded.getDataNascimento());
        assertEquals(p1.getTaxId(),           loaded.getTaxId());
        assertEquals(p1.isAtivo(),            loaded.isAtivo());
        assertEquals(p1.getGender(),          loaded.getGender());
        assertEquals(p1.getEmail(),           loaded.getEmail());
        assertEquals(p1.getTelefone(),        loaded.getTelefone());
    }
}
