package com.vertafore.plm.fermenter.etl.fixtures;

import java.util.List;
import java.util.Map;

import com.vertafore.plm.fermenter.etl.util.DBUtilities;
import com.vertafore.plm.fermenter.fixtures.TestFixture;
import org.junit.Ignore;

@Ignore
public class EtlTestFixture extends TestFixture {

	private DataDetail detail;
	private DBUtilities dbUtil;
	
	public void setDbUtil(DBUtilities dbUtil) {
		this.dbUtil = dbUtil;
	}

	public EtlTestFixture(String name) {
		super(name);
	}

	@Override
	public void executeTest() throws Exception {
		
		if(detail != null) {
			String selectStatement = "select ";
			
			for(String field : detail.getFields()) {
				selectStatement+=field+", ";
			}
			selectStatement = selectStatement.substring(0, selectStatement.length()-2) + " from " + detail.getDataSource() + "." + detail.getTableName();
			
			if(detail.getWhereClause() != null && !"".equals(detail.getWhereClause())) {
				selectStatement += " where "+detail.getWhereClause();
			}
			if(detail.getOrderBy() != null && !"".equals(detail.getOrderBy())) {
				selectStatement += " order by "+detail.getOrderBy();
			}
			
			List<Map<String, Object>> data = dbUtil.getRows(detail.getDataSource(), selectStatement);
			
			assertEquals(detail.getDataRows().size(), data.size());
			int rowCounter = 0;
			for(String[] rowData : detail.getDataRows()) {
				assertEquals(rowData[rowCounter], data.get(rowCounter).get(detail.getFields()[rowCounter]));
				rowCounter++;
			}
		}
	}

	public void setDataDetail(DataDetail detail) {
		this.detail = detail;
		
	}
}
