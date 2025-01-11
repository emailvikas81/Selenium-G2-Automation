package integrationtests.mathstuff;

import com.vertafore.plm.fermenter.fixtures.TestFixture;

public class SumTestFixture extends TestFixture {

	private String first;
	private String second;
	public String sum;
	
	public SumTestFixture(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		Sum sum = new Sum();
		assertEquals(getFormattedDescription(), Integer.parseInt(this.sum), sum.add(Integer.parseInt(first), Integer.parseInt(second)));
	}
}
