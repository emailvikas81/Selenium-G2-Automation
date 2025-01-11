package integrationtests;


import com.vertafore.plm.fermenter.etl.EtlSuiteBuilder;
import com.vertafore.plm.fermenter.etl.scanners.EtlSpreadsheetScanner;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;


@RunWith(AllTests.class)
public class AllEtlTestsSuite {

	
	public static TestSuite suite() throws Exception {
		EtlSuiteBuilder builder = new EtlSuiteBuilder();
		
		return builder.buildSuite(new EtlSpreadsheetScanner(".*D_ENTITY.xlsx"));
//		return builder.buildSuite(new EtlSpreadsheetScanner("/Users/emecham/Documents/workspace/EtlTestFramework/bin/D_ENTITY.xlsx"));
	}
	
}
