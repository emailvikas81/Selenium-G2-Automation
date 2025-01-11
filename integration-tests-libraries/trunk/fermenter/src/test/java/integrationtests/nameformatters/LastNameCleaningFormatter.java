package integrationtests.nameformatters;

public class LastNameCleaningFormatter {

	private String lastName;

	public LastNameCleaningFormatter(String lastName) {
		this.lastName = lastName;
	}

	public String getCleanName() {
		return this.lastName.replace("'", "").replace(" jr", "");
	}

}
