package com.vertafore.plm.fermenter.etl.scanners;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.vertafore.plm.fermenter.etl.EtlFixtureDefinition;
import com.vertafore.plm.fermenter.etl.fixtures.DataDetail;
import org.junit.Test;

public class EtlSpreadsheetScannerTest {

	@Test
	public void testGetName() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = getTests();
		assertEquals("com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture", tests.get(0).fixture);
	}

	@Test
	public void testGetConfiguration() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = getTests();
		assertEquals("/dir=/DWH/1.Transactions/D_ENTITY", tests.get(0).getJobLocation());
		assertEquals("/job=D_ENTITY_Refresh", tests.get(0).getJobName());
		assertEquals("/param:user.vars.FullLoad=Y", tests.get(0).getJobParams());
	}
	
	@Test
	public void testGetCleanup() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = getTests();
		ArrayList<String[]> cleanup = tests.get(0).getCleanup();
		assertEquals(39, cleanup.size());
		assertEquals("VF_DWH", cleanup.get(0)[0]);
		assertEquals("D_ENTITY", cleanup.get(0)[1]);
		assertEquals("HST_SFS", cleanup.get(38)[0]);
		assertEquals("PIN_TRNS", cleanup.get(38)[1]);
	}
	
	@Test
	public void testGetPostCleanup() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = getTests();
		ArrayList<String[]> cleanup = tests.get(0).getPostCleanup();
		assertEquals(39, cleanup.size());
		assertEquals("VF_DWH", cleanup.get(0)[0]);
		assertEquals("D_ENTITY", cleanup.get(0)[1]);
        assertEquals("HST_SFS", cleanup.get(38)[0]);
        assertEquals("PIN_TRNS", cleanup.get(38)[1]);
	}
	
	@Test
	public void testGetDataInserts() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = getTests();
		ArrayList<DataDetail> dataInserts = tests.get(0).getDataInserts();
		assertEquals(30, dataInserts.size());
	
		assertEquals("STG_ETL", dataInserts.get(0).getDataSource());
		assertEquals("C_SOURCE_SYSTEMS", dataInserts.get(0).getTableName());
		
		assertEquals(3, dataInserts.get(0).getFields().length);
		assertEquals("c_source_systems_sk", dataInserts.get(0).getFields()[0]);
		assertEquals("trans_system", dataInserts.get(0).getFields()[1]);
		assertEquals("trans_source", dataInserts.get(0).getFields()[2]);
		
		assertEquals(7, dataInserts.get(0).getDataRows().size());
		assertEquals("1", dataInserts.get(0).getDataRows().get(0)[0]);
		assertEquals("AE", dataInserts.get(0).getDataRows().get(0)[1]);
		assertEquals("AE_DB1", dataInserts.get(0).getDataRows().get(0)[2]);
		
		assertEquals("7", dataInserts.get(0).getDataRows().get(6)[0]);
		assertEquals("SFS", dataInserts.get(0).getDataRows().get(6)[1]);
		assertEquals("SFS_DB1", dataInserts.get(0).getDataRows().get(6)[2]);
		
		assertEquals("VF_DWH", dataInserts.get(1).getDataSource());
		assertEquals("R_SRC_SYS", dataInserts.get(1).getTableName());
		
		assertEquals(5, dataInserts.get(1).getFields().length);
		assertEquals("src_sys_sk", dataInserts.get(1).getFields()[0]);
		assertEquals("code", dataInserts.get(1).getFields()[1]);
		assertEquals("dscr", dataInserts.get(1).getFields()[2]);
		assertEquals("audit_begin_tmsp", dataInserts.get(1).getFields()[3]);
		assertEquals("audit_end_tmsp", dataInserts.get(1).getFields()[4]);
		
		assertEquals(8, dataInserts.get(1).getDataRows().size());
		assertEquals("0", dataInserts.get(1).getDataRows().get(0)[0]);
		assertEquals(" ", dataInserts.get(1).getDataRows().get(0)[1]);
		assertEquals(" ", dataInserts.get(1).getDataRows().get(0)[2]);
		assertEquals("01-Jan-2013", dataInserts.get(1).getDataRows().get(0)[3]);
		assertEquals("31-Dec-2099", dataInserts.get(1).getDataRows().get(0)[4]);

		assertEquals("107", dataInserts.get(1).getDataRows().get(7)[0]);
		assertEquals("SFS", dataInserts.get(1).getDataRows().get(7)[1]);
		assertEquals("SIRCON FOR STATES", dataInserts.get(1).getDataRows().get(7)[2]);
		assertEquals("01-Jan-2013", dataInserts.get(1).getDataRows().get(7)[3]);
		assertEquals("31-Dec-2099", dataInserts.get(1).getDataRows().get(7)[4]);

        assertEquals("VF_DWH", dataInserts.get(4).getDataSource());
        assertEquals("M_TIN", dataInserts.get(4).getTableName());
        assertEquals("100000001", dataInserts.get(4).getDataRows().get(1)[2]);

	}
	
	@Test
	public void testGetValidations() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = getTests();
		ArrayList<DataDetail> validations = tests.get(0).getValidations();
		assertEquals(2, validations.size());
	
		assertEquals("VF_DWH", validations.get(0).getDataSource());
		assertEquals("D_ENTITY", validations.get(0).getTableName());
		assertNull(validations.get(0).getWhereClause());
		assertEquals("DATA_CNTXT_SK",validations.get(0).getOrderBy());
		
		assertEquals(9, validations.get(0).getFields().length);
		assertEquals("entity_sk", validations.get(0).getFields()[0]);
		assertEquals("data_cntxt_sk", validations.get(0).getFields()[1]);
		assertEquals("tin_sk", validations.get(0).getFields()[2]);
		assertEquals("name_sk", validations.get(0).getFields()[3]);
		assertEquals("src_pk_ref", validations.get(0).getFields()[4]);
		assertEquals("src_table_name_ref", validations.get(0).getFields()[5]);
		assertEquals("a_iteration_segment_sk", validations.get(0).getFields()[6]);
		assertEquals("audit_begin_tmsp", validations.get(0).getFields()[7]);
		assertEquals("audit_end_tmsp", validations.get(0).getFields()[8]);
		
		assertEquals(23, validations.get(0).getDataRows().size());
		assertEquals("ENTITY_SK IS NOT NULL", validations.get(0).getDataRows().get(0)[0]);
		assertEquals("1111111", validations.get(0).getDataRows().get(0)[1]);
		assertEquals("77701", validations.get(0).getDataRows().get(0)[2]);
		assertEquals("1111", validations.get(0).getDataRows().get(0)[3]);
		assertEquals("8881", validations.get(0).getDataRows().get(0)[4]);
		assertEquals("AGY.AGENCY", validations.get(0).getDataRows().get(0)[5]);
		assertEquals("A_ITERATION_SEGMENT_SK IS NOT NULL", validations.get(0).getDataRows().get(0)[6]);
		assertEquals("AUDIT_BEGIN_TMSP IS NOT NULL", validations.get(0).getDataRows().get(0)[7]);
		assertEquals("2099-12-31 00:00:00.0", validations.get(0).getDataRows().get(0)[8]);

		assertEquals("ENTITY_SK IS NOT NULL", validations.get(0).getDataRows().get(1)[0]);
		assertEquals("1111112", validations.get(0).getDataRows().get(1)[1]);
		assertEquals("77702", validations.get(0).getDataRows().get(1)[2]);
		assertEquals("1112", validations.get(0).getDataRows().get(1)[3]);
		assertEquals("8882", validations.get(0).getDataRows().get(1)[4]);
		assertEquals("INCON.SIRCON_RQST_INDV", validations.get(0).getDataRows().get(1)[5]);
		assertEquals("A_ITERATION_SEGMENT_SK IS NOT NULL", validations.get(0).getDataRows().get(1)[6]);
		assertEquals("AUDIT_BEGIN_TMSP IS NOT NULL", validations.get(0).getDataRows().get(1)[7]);
		assertEquals("2099-12-31 00:00:00.0", validations.get(0).getDataRows().get(1)[8]);
		
		assertEquals("STG_ETL", validations.get(1).getDataSource());
		assertEquals("A_DWH_ITERATION_SEGMENT", validations.get(1).getTableName());
		assertEquals("EXEC_END_TMSP > TRUNC(SYSDATE-1) AND A_ITERATION_SK IN (SELECT A_ITERATION_SK FROM STG_ETL.A_DWH_ITERATION WHERE TARGET_TABLE = 'D_ENTITY')", validations.get(1).getWhereClause());
		assertEquals("trans_system, source_name", validations.get(1).getOrderBy());
		
		assertEquals(3, validations.get(1).getFields().length);
		assertEquals("trans_system", validations.get(1).getFields()[0]);
		assertEquals("source_name", validations.get(1).getFields()[1]);
		assertEquals("status", validations.get(1).getFields()[2]);
		
		assertEquals(25, validations.get(1).getDataRows().size());
		assertEquals("AE", validations.get(1).getDataRows().get(0)[0]);
		assertEquals("AGENCY", validations.get(1).getDataRows().get(0)[1]);
		assertEquals("SUCCESS", validations.get(1).getDataRows().get(0)[2]);

		assertEquals("AE", validations.get(1).getDataRows().get(1)[0]);
		assertEquals("PENDING_AGENCY_DETAILS (LKP)", validations.get(1).getDataRows().get(1)[1]);
		assertEquals("SUCCESS", validations.get(1).getDataRows().get(1)[2]);
	}
	@Test
	public void testGetMultipleFiles() throws Exception {
		EtlSpreadsheetScanner scanner = new EtlSpreadsheetScanner();
		ArrayList<EtlFixtureDefinition> tests = scanner.getFixtureTestDefinitions();
		assertNotNull(tests);
		assertEquals(2,tests.size());

		assertEquals("/dir=/DWH/1.Transactions/D_ENTITY", tests.get(0).getJobLocation());
		assertEquals("/job=D_ENTITY_Refresh", tests.get(0).getJobName());
		assertEquals("/param:user.vars.FullLoad=Y", tests.get(0).getJobParams());
		
		assertEquals("/dir=/DWH/1.Transactions/D_ENTITY_temp", tests.get(1).getJobLocation());
		assertEquals("/job=D_ENTITY_Refresh_temp", tests.get(1).getJobName());
		assertEquals("/param:user.vars.FullLoad=Y", tests.get(1).getJobParams());
	}
	
	private ArrayList<EtlFixtureDefinition> getTests() throws Exception {
		EtlSpreadsheetScanner scanner = new EtlSpreadsheetScanner(".*ENTITY.xlsx");
		ArrayList<EtlFixtureDefinition> tests = scanner.getFixtureTestDefinitions();
		assertNotNull(tests);
		assertEquals(1,tests.size());
		return tests;
	}
	
	
}
