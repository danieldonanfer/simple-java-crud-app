package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.EmploymentCategory;
import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;

	private String[] colNames = {"ID", "Nome", "Ocupacao", "Faixa Etaria", "Emprego", "Ativo", "CPF", "E-mail", "Telefone"};

	private int colLength = colNames.length;

	public PersonTableModel() {}

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		switch (col) {
			case 0: return Integer.class;
			case 5: return Boolean.class;
			case 4: return EmploymentCategory.class;
			default: return String.class;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (db == null) return;
		Person person = db.get(row);
		switch (col) {
			case 1: person.setName((String) value); break;
			case 2: person.setOccupation((String) value); break;
			case 4: person.setEmpCat((EmploymentCategory) value); break;
			case 5: person.setAtivo((Boolean) value); break;
			case 7: person.setEmail((String) value); break;
			case 8: person.setTelefone((String) value); break;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
			case 1: case 2: case 4: case 5: case 7: case 8: return true;
			default: return false;
		}
	}

	@Override
	public int getColumnCount() { return colLength; }

	@Override
	public int getRowCount() { return db == null ? 0 : db.size(); }

	@Override
	public Object getValueAt(int row, int col) {
		Person person = db.get(row);
		switch (col) {
			case 0: return person.getId();
			case 1: return person.getName();
			case 2: return person.getOccupation();
			case 3: return person.getAgeCategory();
			case 4: return person.getEmpCat();
			case 5: return person.isAtivo();
			case 6: return person.getTaxId();
			case 7: return person.getEmail();
			case 8: return person.getTelefone();
			default: return null;
		}
	}
}
