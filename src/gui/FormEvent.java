package gui;

import java.util.EventObject;

public class FormEvent extends EventObject {

	private String name;
	private String occupation;
	private String dataNascimento;
	private String empCat;
	private String taxId;
	private boolean ativo;
	private String gender;
	private String email;
	private String telefone;

	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, String name, String occupation,
			String dataNascimento, String empCat, boolean ativo,
			String taxId, String gender, String email, String telefone) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.dataNascimento = dataNascimento;
		this.empCat = empCat;
		this.ativo = ativo;
		this.taxId = taxId;
		this.gender = gender;
		this.email = email;
		this.telefone = telefone;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getOccupation() { return occupation; }
	public void setOccupation(String occupation) { this.occupation = occupation; }

	public String getDataNascimento() { return dataNascimento; }

	public String getEmploymentCategory() { return empCat; }

	public String getTaxId() { return taxId; }

	public boolean isAtivo() { return ativo; }

	public String getGender() { return gender; }

	public String getEmail() { return email; }
	public String getTelefone() { return telefone; }
}
