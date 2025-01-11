package com.vertafore.plm.fermenter.etl;


import java.util.ArrayList;

import com.vertafore.plm.fermenter.etl.fixtures.DataDetail;
import com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture;
import com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixtureCleanup;
import com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixtureSetup;
import com.vertafore.plm.fermenter.etl.scanners.EtlSpreadsheetScanner;
import com.vertafore.plm.fermenter.etl.util.CommandLineRunner;
import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import junit.framework.TestSuite;

public class EtlSuiteBuilder {

	public TestSuite buildSuite(EtlSpreadsheetScanner scanner) throws Exception {
		ArrayList<EtlFixtureDefinition> fds = scanner.getFixtureTestDefinitions();

		TestSuite suite = new TestSuite();

		int testCount = 0;
		for (EtlFixtureDefinition fd : fds) {
			EtlTestFixtureSetup etlTestFixtureSetup = new EtlTestFixtureSetup("EtlTestFixtureSetup "+fd.getJobName());
			etlTestFixtureSetup.setFixtureDefinition(fd);
			etlTestFixtureSetup.setDBUtilities(new DBUtilities());
			etlTestFixtureSetup.setRunner(new CommandLineRunner());
			suite.addTest((junit.framework.Test) etlTestFixtureSetup);

			for(DataDetail detail : fd.getValidations()) {
				EtlTestFixture fixture = new EtlTestFixture("EtlTestFixture "+ testCount++);
				fixture.setDataDetail(detail);
				fixture.setDbUtil(new DBUtilities());
				
				suite.addTest((junit.framework.Test) fixture);	
			}

			EtlTestFixtureCleanup etlTestFixtureCleanup = new EtlTestFixtureCleanup("EtlTestFixtureCleanup "+fd.getJobName());
			etlTestFixtureCleanup.setFixtureDefinition(fd);
			etlTestFixtureCleanup.setDBUtilities(new DBUtilities());
			suite.addTest((junit.framework.Test) etlTestFixtureCleanup);
			
		}

		return suite;
	}

}

