package com.vertafore.plm.fermenter.etl.fixtures;

import java.util.ArrayList;
import java.util.List;

import com.vertafore.plm.fermenter.etl.EtlFixtureDefinition;
import com.vertafore.plm.fermenter.etl.util.CommandLineRunner;
import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EtlTestFixtureSetupTest {

	private static ArrayList<String> cleanUpsPassed = new ArrayList<String>();
	private static ArrayList<String> insertDataPassed = new ArrayList<String>();
	private static List<String> commandArgumentsPassed = new ArrayList<String>();

	@Before
	public void before() {
		cleanUpsPassed = new ArrayList<String>();
		insertDataPassed = new ArrayList<String>();
		commandArgumentsPassed = new ArrayList<String>();
	}
	
	@Test
	public void testCleanUps() throws Exception{
		
		EtlFixtureDefinition definition = new EtlFixtureDefinition();
		ArrayList<String[]> cleanups = new ArrayList<String[]>();
		cleanups.add(new String[]{"ds1", "table1"});
		cleanups.add(new String[]{"ds1", "table2"});
		cleanups.add(new String[]{"ds2", "table3"});
		cleanups.add(new String[]{"ds3", "table4"});
		
		definition.setCleanup(cleanups);
		
		
		EtlTestFixtureSetup setup = new EtlTestFixtureSetup("name");
		setup.setDBUtilities(new DBUtilities() {

			public Object deleteFromTable(String dataSourceName, String tableName) {
				
				cleanUpsPassed.add(dataSourceName+tableName);
				
				return "";
			}
		});
		setup.setFixtureDefinition(definition);
		
		setup.executeTest();
		
		assertEquals(4, cleanUpsPassed.size());
		assertEquals("ds1table1", cleanUpsPassed.get(0));
		assertEquals("ds1table2", cleanUpsPassed.get(1));
		assertEquals("ds2table3", cleanUpsPassed.get(2));
		assertEquals("ds3table4", cleanUpsPassed.get(3));
	}
	
	@Test
	public void testInsertTestData() throws Exception{
		
		EtlFixtureDefinition definition = new EtlFixtureDefinition();
		
		ArrayList<DataDetail> inserts = new ArrayList<DataDetail>();
		DataDetail detail1 = new DataDetail();
		detail1.setDataSource("ds1");
		detail1.setTableName("tb1");
		detail1.setFields(new String[]{"f1", "f2"});
		
		ArrayList<String[]> dataRows1 = new ArrayList<String[]>();
		dataRows1.add(new String[]{"f1d1", "f2d1"});
		dataRows1.add(new String[]{"f1d2", "f2d2"});
		detail1.setDataRows(dataRows1);
		
		inserts.add(detail1);
		
		DataDetail detail2 = new DataDetail();
		detail2.setDataSource("ds2");
		detail2.setTableName("tb2");
		detail2.setFields(new String[]{"f3", "f4"});
		
		ArrayList<String[]> dataRows2 = new ArrayList<String[]>();
		dataRows2.add(new String[]{"f3d1", "f4d1"});
		dataRows2.add(new String[]{"f3d2", "f4d2"});
		detail2.setDataRows(dataRows2);
		
		inserts.add(detail2);
		
		definition.setDataInserts(inserts);
		
		
		EtlTestFixtureSetup setup = new EtlTestFixtureSetup("name");
		setup.setDBUtilities(new DBUtilities() {

			public Object insertData(String dataSourceName, String insertStatement, List parameters) {
				
				insertDataPassed.add(dataSourceName+" - "+insertStatement+" - "+parameters);
				
				return "";
			}
		});
		setup.setFixtureDefinition(definition);
		
		setup.executeTest();
		
		assertEquals(4, insertDataPassed.size());
		assertEquals("ds1 - insert into tb1 (f1, f2) values (?, ?) - [f1d1, f2d1]", insertDataPassed.get(0));
		assertEquals("ds1 - insert into tb1 (f1, f2) values (?, ?) - [f1d2, f2d2]", insertDataPassed.get(1));
		assertEquals("ds2 - insert into tb2 (f3, f4) values (?, ?) - [f3d1, f4d1]", insertDataPassed.get(2));
		assertEquals("ds2 - insert into tb2 (f3, f4) values (?, ?) - [f3d2, f4d2]", insertDataPassed.get(3));
	}
	
	@Test
	public void testExecutesCommand() throws Exception {

		EtlFixtureDefinition definition = new EtlFixtureDefinition();
		definition.setJobLocation("/dir=/DWH/1.Transactions/D_ENTITY");
		definition.setJobName("/job=D_ENTITY_Refresh");
		definition.setJobParams("/param:user.vars.FullLoad=Y");
		
		EtlTestFixtureSetup setup = new EtlTestFixtureSetup("name");
		setup.setFixtureDefinition(definition);
		
		setup.setRunner(new CommandLineRunner() {
			public void executeProcess(List<String> arguments) {
				commandArgumentsPassed = arguments;
			}
		});
		
		setup.executeTest();
		
		assertEquals(3, commandArgumentsPassed.size());
		assertEquals("/dir=/DWH/1.Transactions/D_ENTITY", commandArgumentsPassed.get(0));
		assertEquals("/job=D_ENTITY_Refresh", commandArgumentsPassed.get(1));
		assertEquals("/param:user.vars.FullLoad=Y", commandArgumentsPassed.get(2));
	}
}
