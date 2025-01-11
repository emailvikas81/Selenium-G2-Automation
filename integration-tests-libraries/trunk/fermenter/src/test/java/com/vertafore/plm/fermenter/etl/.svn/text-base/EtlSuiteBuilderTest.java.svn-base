package com.vertafore.plm.fermenter.etl;

import static org.junit.Assert.*;

import com.vertafore.plm.fermenter.etl.scanners.EtlSpreadsheetScanner;
import junit.framework.TestSuite;

import org.junit.Test;


public class EtlSuiteBuilderTest {

	@Test
	public void test() throws Exception {
		EtlSuiteBuilder builder = new EtlSuiteBuilder();
		TestSuite suite = builder.buildSuite(new EtlSpreadsheetScanner());
		
		assertEquals(8, suite.countTestCases());
		assertEquals("EtlTestFixtureSetup /job=D_ENTITY_Refresh(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixtureSetup)", suite.testAt(0).toString());
		assertEquals("EtlTestFixture 0(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture)", suite.testAt(1).toString());
		assertEquals("EtlTestFixture 1(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture)", suite.testAt(2).toString());
		assertEquals("EtlTestFixtureCleanup /job=D_ENTITY_Refresh(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixtureCleanup)", suite.testAt(3).toString());
		assertEquals("EtlTestFixtureSetup /job=D_ENTITY_Refresh_temp(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixtureSetup)", suite.testAt(4).toString());
		assertEquals("EtlTestFixture 2(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture)", suite.testAt(5).toString());
		assertEquals("EtlTestFixture 3(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture)", suite.testAt(6).toString());
		assertEquals("EtlTestFixtureCleanup /job=D_ENTITY_Refresh_temp(com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixtureCleanup)", suite.testAt(7).toString());
	}

}
