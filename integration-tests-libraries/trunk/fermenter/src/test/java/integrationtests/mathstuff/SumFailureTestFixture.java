package integrationtests.mathstuff;

import com.vertafore.plm.fermenter.fixtures.TestFixture;
import junit.framework.AssertionFailedError;

public class SumFailureTestFixture extends TestFixture {

	private String first;
	private String second;
	public String sum;

	public SumFailureTestFixture(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		Sum sum = new Sum();
        try {
		    assertEquals(getFormattedDescription(), Integer.parseInt(this.sum), sum.add(Integer.parseInt(first), Integer.parseInt(second)));
            fail("Expected an Assertion Failed error");
        } catch (AssertionFailedError e) {
            assertTrue(e.getMessage().contains("expected:<10> but was:<8>"));
        }
	}
}
