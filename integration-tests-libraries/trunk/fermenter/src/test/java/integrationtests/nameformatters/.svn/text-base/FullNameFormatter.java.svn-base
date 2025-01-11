package integrationtests.nameformatters;

public class FullNameFormatter {
	private String firstName;
	private String lastName;
	
	public FullNameFormatter(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFullName() {
		String fullName = "";
		if(lastName != null && !"".equals(lastName)) {
			fullName+=lastName;

			if(firstName != null && !"".equals(firstName)) {
				fullName+=", ";	
			} else {
				fullName = "Mr. "+fullName;
			}
		}
		
		fullName+=firstName;
		
		return fullName;
	}
}
