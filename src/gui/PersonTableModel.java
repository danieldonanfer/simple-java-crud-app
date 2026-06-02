package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.EmploymentCategory;
import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;

	private String[] colNames = {
		"ID", "Nome", "CPF", "Nascimento", "Genero",
		"Cargo", "Contrato", "Ativo", "E-mail", "Telefone"
	};

	public PersonTableModel() {}

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return db == null ? 0 : db.size();
	}

	@Override
	public Class<?> getColumnClass(int col) {
		switch (col) {
			case 0: return Integer.class;
			case 6: return EmploymentCategory.class;
			case 7: return Boolean.class;
			default: return String.class;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		// ID, Nascimento e Genero nao sao editaveis direto na tabela
		switch (col) {
			case 0: case 3: case 4: return false;
			default: return true;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (db == null) return;
		Person p = db.get(row);
		switch (col) {
			case 1: p.setName((String) value);                   break;
			case 2: p.setTaxId((String) value);                  break;
			case 5: p.setOccupation((String) value);             break;
			case 6: p.setEmpCat((EmploymentCategory) value);     break;
			case 7: p.setAtivo((Boolean) value);                 break;
			case 8: p.setEmail((String) value);                  break;
			case 9: p.setTelefone((String) value);               break;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		Person p = db.get(row);
		switch (col) {
			case 0: return p.getId();
			case 1: return p.getName();
			case 2: return p.getTaxId();
			case 3: return p.getDataNascimento();
			case 4: return p.getGender();
			case 5: return p.getOccupation();
			case 6: return p.getEmpCat();
			case 7: return p.isAtivo();
			case 8: return p.getEmail();
			case 9: return p.getTelefone();
			default: return null;
		}
	}
}
