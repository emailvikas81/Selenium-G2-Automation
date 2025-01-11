package integrationtests.nameformatters;

import com.vertafore.plm.fermenter.fixtures.TestFixture;

public class FullNameTestFixture extends TestFixture {

	private String firstName;
	private String lastName;
	public String fullName;

	public FullNameTestFixture(String name) {
		super(name);
	}
	@Override
	public void executeTest() throws Exception {
		FullNameFormatter formatter = new FullNameFormatter(firstName, lastName);
		assertEquals(fullName, formatter.getFullName());
	}
}
