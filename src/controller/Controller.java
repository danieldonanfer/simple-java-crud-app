package controller;

import gui.FormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {
	Database db = new Database();
	
	public List<Person> getPeople() {
		return db.getPeople();
	}
	
	public void save() throws SQLException
	{
		db.save();
	}
	
	public void load() throws SQLException
	{
		db.load();
	}
	public void connect() throws Exception
	{
		db.connect();
	}
	
	public void disconnect()
	{
		db.disconnect();
	}
	
	
	
	public void addPerson(FormEvent ev) {
		String name = ev.getName();
		String occupation = ev.getOccupation();
		int ageCatId = ev.getAgeCategory();
		String empCat = ev.getEmploymentCategory();
		boolean ativo = ev.isAtivo();
		String taxId = ev.getTaxId();
		String gender = ev.getGender();
		String email = ev.getEmail();
		String telefone = ev.getTelefone();
		
		AgeCategory ageCategory = null;
		
		switch(ageCatId) {
		case 0:
			ageCategory = AgeCategory.CRIANCA;
			break;
		case 1:
			ageCategory = AgeCategory.ADOLESCENTE;
			break;
		case 2:
			ageCategory = AgeCategory.ADULTO;
			break;
		case 3:
			ageCategory = AgeCategory.MEIA_IDADE;
			break;
		case 4:
			ageCategory = AgeCategory.IDOSO;
			break;

		}
		
		
		EmploymentCategory empCategory;
		
		if(empCat.equals("Empregado")) {
			empCategory = EmploymentCategory.yes;
		}
		else if(empCat.equals("Desempregado")) {
			empCategory = EmploymentCategory.no;
		}
		else if(empCat.equals("Autonomo")) {
			empCategory = EmploymentCategory.sklad;
		}
		else {
			empCategory = EmploymentCategory.other;
		}
		
		Gender genderCat;
		
		if(gender.equals("MASCULINO")) {
			genderCat = Gender.MASCULINO;
		}
		else {
			genderCat = Gender.FEMININO;
		}
		
		Person person = new Person(name, occupation, ageCategory, empCategory,
				taxId, ativo, genderCat, email, telefone);
		
		db.addPerson(person);
	}
	public void removePerson(int index)
	{
		db.removePerson(index);
	}
	
	public void saveToFile(File file) throws IOException
	{
		db.saveToFile(file);
	}
	public void loadFromFile(File file) throws IOException
	{
		db.loadFromFile(file);
	}
	
}
