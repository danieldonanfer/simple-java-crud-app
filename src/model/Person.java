package model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 4022843652518158651L;

	private static int count = 1;

	private int id;
	private String name;
	private String occupation;
	private String dataNascimento;
	private EmploymentCategory empCat;
	private String taxId;
	private boolean ativo;
	private Gender gender;
	private String email;
	private String telefone;

	public Person(String name, String occupation, String dataNascimento,
			EmploymentCategory empCat, String taxId,
			boolean ativo, Gender gender,
			String email, String telefone) {
		this.name = name;
		this.occupation = occupation;
		this.dataNascimento = dataNascimento;
		this.empCat = empCat;
		this.taxId = taxId;
		this.ativo = ativo;
		this.gender = gender;
		this.email = email;
		this.telefone = telefone;
		this.id = count++;
	}

	public Person(String name, String occupation, String dataNascimento,
			EmploymentCategory empCat, String taxId,
			boolean ativo, Gender gender) {
		this(name, occupation, dataNascimento, empCat, taxId, ativo, gender, "", "");
	}

	public Person(int id, String name, String occupation, String dataNascimento,
			EmploymentCategory empCat, String taxId,
			boolean ativo, Gender gender) {
		this(name, occupation, dataNascimento, empCat, taxId, ativo, gender);
		this.id = id;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getOccupation() { return occupation; }
	public void setOccupation(String occupation) { this.occupation = occupation; }

	public String getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

	public EmploymentCategory getEmpCat() { return empCat; }
	public void setEmpCat(EmploymentCategory empCat) { this.empCat = empCat; }

	public String getTaxId() { return taxId; }
	public void setTaxId(String taxId) { this.taxId = taxId; }

	public boolean isAtivo() { return ativo; }
	public void setAtivo(boolean ativo) { this.ativo = ativo; }

	public Gender getGender() { return gender; }
	public void setGender(Gender gender) { this.gender = gender; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	public String toString() {
		return id + " - " + name;
	}
}
