package com.vertafore.plm.fermenter.etl.fixtures;

import java.util.ArrayList;
import java.util.Collections;

import com.vertafore.plm.fermenter.etl.util.CommandLineRunner;
import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import com.vertafore.plm.fermenter.etl.EtlFixtureDefinition;
import com.vertafore.plm.fermenter.fixtures.TestFixture;
import org.junit.Ignore;

@Ignore
public class EtlTestFixtureSetup extends TestFixture {

	private EtlFixtureDefinition definition;
	private DBUtilities dbUtil;
	private CommandLineRunner runner;
	

	public EtlTestFixtureSetup(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		
		//clean up the data
		if(definition.getCleanup() != null) {
			for(String[] cleanup : definition.getCleanup()) {
				dbUtil.deleteFromTable(cleanup[0], cleanup[1]);
			}
		}
		
		//insert the new data
		if(definition.getDataInserts() != null) {
			for(DataDetail detail : definition.getDataInserts()) {
				
				String insertStatement = "insert into "+detail.getTableName() + " (";
				String values = "";
				
				for(String field : detail.getFields()) {
					insertStatement+=field+", ";
					values+="?, ";
				}
				insertStatement = insertStatement.substring(0, insertStatement.length()-2)+") values ("+values.substring(0, values.length()-2)+")";
				
				
				for(String[] parameters : detail.getDataRows()) {
					ArrayList params = new ArrayList();
					Collections.addAll(params, parameters);
					dbUtil.insertData(detail.getDataSource(), insertStatement,  params);
				}
			}
		}
		
		//execute ETL job
		if(runner != null) {
			ArrayList<String> arguments = new ArrayList<String>();
			arguments.add(definition.getJobLocation());
			arguments.add(definition.getJobName());
			arguments.add(definition.getJobParams());
			runner.executeProcess(arguments);
		}
		
	}

	public void setDBUtilities(DBUtilities dbUtil) {
		this.dbUtil = dbUtil;
	}
	public void setFixtureDefinition(EtlFixtureDefinition definition) {
		this.definition = definition;
	}
	public void setRunner(CommandLineRunner runner) {
		this.runner = runner;
	}
}
