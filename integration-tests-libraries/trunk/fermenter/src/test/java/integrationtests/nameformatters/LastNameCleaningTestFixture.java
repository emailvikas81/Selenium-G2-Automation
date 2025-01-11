package integrationtests.nameformatters;

import com.vertafore.plm.fermenter.fixtures.TestFixture;

public class LastNameCleaningTestFixture extends TestFixture {

	public String lastName;
	public String cleanLastName;

	public LastNameCleaningTestFixture(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		LastNameCleaningFormatter formatter = new LastNameCleaningFormatter(lastName);
		assertEquals(cleanLastName, formatter.getCleanName());
	}
}
