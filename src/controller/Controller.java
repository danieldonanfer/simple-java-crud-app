package controller;

import gui.FormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {
	Database db = new Database();

	public List<Person> getPeople() {
		return db.getPeople();
	}

	public void save() throws SQLException {
		db.save();
	}

	public void load() throws SQLException {
		db.load();
	}

	public void connect() throws Exception {
		db.connect();
	}

	public void disconnect() {
		db.disconnect();
	}

	public void addPerson(FormEvent ev) {
		String name           = ev.getName();
		String occupation     = ev.getOccupation();
		String dataNascimento = ev.getDataNascimento();
		String empCat         = ev.getEmploymentCategory();
		boolean ativo         = ev.isAtivo();
		String taxId          = ev.getTaxId();
		String gender         = ev.getGender();
		String email          = ev.getEmail();
		String telefone       = ev.getTelefone();

		EmploymentCategory empCategory;
		switch (empCat) {
			case "Umowa o prace": empCategory = EmploymentCategory.CLT;          break;
			case "B2B":           empCategory = EmploymentCategory.PJ;           break;
			case "Staztysta":     empCategory = EmploymentCategory.ESTAGIARIO;   break;
			default:              empCategory = EmploymentCategory.TERCEIRIZADO;
		}

		Gender genderCat = gender.equals("MASCULINO") ? Gender.MASCULINO : Gender.FEMININO;

		Person person = new Person(name, occupation, dataNascimento,
				empCategory, taxId, ativo, genderCat, email, telefone);

		db.addPerson(person);
	}

	public void removePerson(int index) {
		db.removePerson(index);
	}

	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
}
