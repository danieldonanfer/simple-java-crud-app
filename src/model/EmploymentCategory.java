package model;

public enum EmploymentCategory {
	CLT("CLT"),
	PJ("PJ"),
	ESTAGIARIO("Estagiario"),
	TERCEIRIZADO("Terceirizado");

	private String text;

	private EmploymentCategory(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
