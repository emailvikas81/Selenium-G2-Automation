package integrationtests;

import com.vertafore.plm.fermenter.SuiteBuilder;
import com.vertafore.plm.fermenter.scanners.FileTestsScanner;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

@RunWith(AllTests.class)
public class LastNameTestSuite {

	
	public static TestSuite suite() throws Exception {
		SuiteBuilder builder = new SuiteBuilder();
		
		//return builder.buildSuite(new FileTestsScanner());
		return builder.buildSuite(new FileTestsScanner(".*Name*.*\\.tests"));
	}
	
}
