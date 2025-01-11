package com.vertafore.plm.fermenter.etl.fixtures;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import org.junit.Before;
import org.junit.Test;

public class EtlTestFixtureTest {

	private static ArrayList<String> getRowsDataPassed = new ArrayList<String>();
	private static ArrayList<Map<String, Object>> rowData = null;
	
	@Before
	public void before() {
		getRowsDataPassed = new ArrayList<String>();
		rowData = null;
	}
	
	@Test
	public void testNoDataReturnedSoAssertFailures() throws Exception {

		EtlTestFixture fixture = new EtlTestFixture("name");
		fixture.setDbUtil(new DBUtilities() {
			public List<Map<String,Object>> getRows(String dataSourceName, String sqlStatement, List parameters) {
				
				getRowsDataPassed.add(dataSourceName+" - "+sqlStatement+" - "+parameters);
				
				return rowData;
			}
		});
		
		DataDetail detail1 = new DataDetail();
		detail1.setWhereClause("bob = jeff");
		detail1.setOrderBy("x, y");
		detail1.setDataSource("ds1");
		detail1.setTableName("tb1");
		detail1.setFields(new String[]{"f1", "f2"});
		
		ArrayList<String[]> dataRows1 = new ArrayList<String[]>();
		dataRows1.add(new String[]{"f1d1", "f2d1"});
		dataRows1.add(new String[]{"f1d2", "f2d2"});
		detail1.setDataRows(dataRows1);
		
		fixture.setDataDetail(detail1);
		boolean threw = false;
		try {
			fixture.executeTest();
			threw = false;
		} catch(Throwable t) {
			threw = true;
			assertEquals(1, getRowsDataPassed.size());
			assertEquals("ds1 - select f1, f2 from ds1.tb1 where bob = jeff order by x, y - []", getRowsDataPassed.get(0));
		}
		assertTrue("Should have thrown an exception because no data returned so asserts should fail", threw);
	}	
	
	@Test
	public void testMismatchDataThrowsAssertionFailure() throws Exception {

		EtlTestFixture fixture = new EtlTestFixture("name");
		fixture.setDbUtil(new DBUtilities() {
			public List<Map<String,Object>> getRows(String dataSourceName, String sqlStatement, List parameters) {
				
				getRowsDataPassed.add(dataSourceName+" - "+sqlStatement+" - "+parameters);
				
				return rowData;
			}
		});
		
		DataDetail detail1 = new DataDetail();
		detail1.setDataSource("ds1");
		detail1.setTableName("tb1");
		detail1.setFields(new String[]{"f1", "f2"});
		
		ArrayList<String[]> dataRows1 = new ArrayList<String[]>();
		dataRows1.add(new String[]{"f1d1", "f2d1"});
		dataRows1.add(new String[]{"f1d2", "f2d3"});//notice f2d3, but data returned below is f2d2
		detail1.setDataRows(dataRows1);
		
		fixture.setDataDetail(detail1);
		
		rowData = new ArrayList<Map<String, Object>>();
		Map rowMap1 = new HashMap<String, Object>();
		rowMap1.put("f1", "f1d1");
		rowMap1.put("f2", "f2d1");
		rowData.add(rowMap1);
		Map rowMap2 = new HashMap<String, Object>();
		rowMap2.put("f1", "f1d2");
		rowMap2.put("f2", "f2d2");
		rowData.add(rowMap2);
		
		boolean threw = false;
		try {
			fixture.executeTest();
			threw = false;
		} catch(Throwable t) {
			threw = true;

			assertEquals(1, getRowsDataPassed.size());
			assertEquals("ds1 - select f1, f2 from ds1.tb1 - []", getRowsDataPassed.get(0));
			
			assertEquals("expected:<f2d3> but was:<f2d2>", t.getMessage());
		}
		assertTrue("Should have thrown a failure from the test as data did not match expected", threw);
	}	
	
	@Test
	public void testMissingDataThrowsAssertionFailure() throws Exception {

		EtlTestFixture fixture = new EtlTestFixture("name");
		fixture.setDbUtil(new DBUtilities() {
			public List<Map<String,Object>> getRows(String dataSourceName, String sqlStatement, List parameters) {
				
				getRowsDataPassed.add(dataSourceName+" - "+sqlStatement+" - "+parameters);
				
				return rowData;
			}
		});
		
		DataDetail detail1 = new DataDetail();
		detail1.setDataSource("ds1");
		detail1.setTableName("tb1");
		detail1.setFields(new String[]{"f1", "f2"});
		
		ArrayList<String[]> dataRows1 = new ArrayList<String[]>();
		dataRows1.add(new String[]{"f1d1", "f2d1"});
		dataRows1.add(new String[]{"f1d2", "f2d2"});//notice this row is missing from data returned below
		detail1.setDataRows(dataRows1);
		
		fixture.setDataDetail(detail1);
		
		rowData = new ArrayList<Map<String, Object>>();
		Map rowMap1 = new HashMap<String, Object>();
		rowMap1.put("f1", "f1d1");
		rowMap1.put("f2", "f2d1");
		rowData.add(rowMap1);
		Map rowMap2 = new HashMap<String, Object>();
		rowMap2.put("f1", "f1d2");
		rowData.add(rowMap2);
		
		boolean threw = false;
		try {
			fixture.executeTest();
			threw = false;
		} catch(Throwable t) {
			threw = true;

			assertEquals(1, getRowsDataPassed.size());
			assertEquals("ds1 - select f1, f2 from ds1.tb1 - []", getRowsDataPassed.get(0));
			
			assertEquals("expected:<f2d2> but was:<null>", t.getMessage());
		}
		assertTrue("Should have thrown a failure from the test as data did not match expected", threw);
	}	

	@Test
	public void testDataPassedInToDbUtilAssertionsPass() throws Exception {

		EtlTestFixture fixture = new EtlTestFixture("name");
		fixture.setDbUtil(new DBUtilities() {
			public List<Map<String,Object>> getRows(String dataSourceName, String sqlStatement, List parameters) {
				
				getRowsDataPassed.add(dataSourceName+" - "+sqlStatement+" - "+parameters);
				
				return rowData;
			}
		});
		
		DataDetail detail1 = new DataDetail();
		detail1.setDataSource("ds1");
		detail1.setTableName("tb1");
		detail1.setFields(new String[]{"f1", "f2"});
		
		ArrayList<String[]> dataRows1 = new ArrayList<String[]>();
		dataRows1.add(new String[]{"f1d1", "f2d1"});
		dataRows1.add(new String[]{"f1d2", "f2d2"});
		detail1.setDataRows(dataRows1);
		
		fixture.setDataDetail(detail1);
		
		rowData = new ArrayList<Map<String, Object>>();
		Map rowMap1 = new HashMap<String, Object>();
		rowMap1.put("f1", "f1d1");
		rowMap1.put("f2", "f2d1");
		rowData.add(rowMap1);
		Map rowMap2 = new HashMap<String, Object>();
		rowMap2.put("f1", "f1d2");
		rowMap2.put("f2", "f2d2");
		rowData.add(rowMap2);
		
		boolean threw = false;
		try {
			fixture.executeTest();
			assertEquals(1, getRowsDataPassed.size());
			assertEquals("ds1 - select f1, f2 from ds1.tb1 - []", getRowsDataPassed.get(0));
			threw = false;
		} catch(Throwable t) {
			threw = true;
		}
		assertFalse("Should not have thrown a failure from the test as data should have been there to satisfy asserts", threw);
	}	
	
}
