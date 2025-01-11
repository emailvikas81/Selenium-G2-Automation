package com.vertafore.plm.fermenter.etl.fixtures;

import java.util.ArrayList;

import com.vertafore.plm.fermenter.etl.EtlFixtureDefinition;
import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EtlTestFixtureCleanupTest {

	private static ArrayList<String> cleanUpsPassed = new ArrayList<String>();

	@Before
	public void before() {
		cleanUpsPassed = new ArrayList<String>();
	}
	
	@Test
	public void testCleanUps() throws Exception{
		
		EtlFixtureDefinition definition = new EtlFixtureDefinition();
		ArrayList<String[]> cleanups = new ArrayList<String[]>();
		cleanups.add(new String[]{"ds1", "table1"});
		cleanups.add(new String[]{"ds1", "table2"});
		cleanups.add(new String[]{"ds2", "table3"});
		cleanups.add(new String[]{"ds3", "table4"});
		
		definition.setPostCleanup(cleanups);
		
		
		EtlTestFixtureCleanup setup = new EtlTestFixtureCleanup("name");
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
}
