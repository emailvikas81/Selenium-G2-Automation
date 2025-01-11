package com.vertafore.plm.fermenter.etl.fixtures;

import java.util.ArrayList;



public class DataDetail {

	private String dataSource;
	private String tableName;
	private String whereClause;
	private String[] fields;
	private ArrayList<String[]> dataRows;
	private String orderBy;
	
	
	
	public String getWhereClause() {
		return whereClause;
	}
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String[] getFields() {
		return fields;
	}
	public void setFields(String[] fields) {
		this.fields = fields;
	}
	public ArrayList<String[]> getDataRows() {
		return dataRows;
	}
	public void setDataRows(ArrayList<String[]> dataRows) {
		this.dataRows = dataRows;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
