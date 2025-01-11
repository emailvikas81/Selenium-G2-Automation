package com.vertafore.plm.fermenter.etl.fixtures;

import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import com.vertafore.plm.fermenter.etl.EtlFixtureDefinition;
import com.vertafore.plm.fermenter.fixtures.TestFixture;
import org.junit.Ignore;

@Ignore
public class EtlTestFixtureCleanup extends TestFixture {

	private EtlFixtureDefinition definition;
	private DBUtilities dbUtil;
	
	public EtlTestFixtureCleanup(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		
		//post clean up the data
		if(definition.getPostCleanup() != null) {
			for(String[] cleanup : definition.getPostCleanup()) {
				dbUtil.deleteFromTable(cleanup[0], cleanup[1]);
			}
		}
	}

	public void setDBUtilities(DBUtilities dbUtil) {
		this.dbUtil = dbUtil;
	}
	public void setFixtureDefinition(EtlFixtureDefinition definition) {
		this.definition = definition;
		
	}
}
