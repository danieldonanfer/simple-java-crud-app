package model;

public enum EmploymentCategory {
	yes("Empregado"),
	no("Desempregado"),
	sklad("Autonomo"),
	other("Outro");

	private String text;
	private EmploymentCategory(String text)
	{
		this.text = text;

	}
	@Override
	public String toString() {
		return text;
	}
}
