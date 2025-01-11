package com.vertafore.plm.fermenter.etl.scanners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.vertafore.plm.fermenter.etl.fixtures.DataDetail;
import com.vertafore.plm.fermenter.etl.EtlFixtureDefinition;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EtlSpreadsheetScanner {
	
	private String regExPattern;
	
	public EtlSpreadsheetScanner() {
		this(".*\\.xlsx");
	}
	public EtlSpreadsheetScanner(String regExPattern){
		this.regExPattern = regExPattern;
	}
	
	public ArrayList<EtlFixtureDefinition> getFixtureTestDefinitions() throws Exception {
		ArrayList<EtlFixtureDefinition> tests = new ArrayList<EtlFixtureDefinition>();
		
	    ArrayList<String> fixtureFiles = findFixtureTestFiles();
		for (String file : fixtureFiles) {
		    tests.add(loadSingleSpreadsheet(file));
		}
	    
	    return tests;
	}
	
	private ArrayList<String> findFixtureTestFiles() {
		Pattern pattern = Pattern.compile(regExPattern);
		ArrayList<String> retval = new ArrayList<String>();
		String classPath = System.getProperty("java.class.path", ".");
        String[] classPathElements = classPath.split(System.getProperty("path.separator","."));
		for (String element : classPathElements) {
			File file = new File(element);
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			}
		}

		return retval;
	}

	private Collection<String> getResourcesFromDirectory(File directory, Pattern pattern) {
		ArrayList<String> retval = new ArrayList<String>();
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			} else {
				try {
					String fileName = file.getCanonicalPath();
					boolean accept = pattern.matcher(fileName).matches();
					if (accept) {
						retval.add(fileName);
					}
				} catch (IOException e) {
					throw new Error(e);
				}
			}
		}
		return retval;
	}

	private Sheet sheet;
	private int currentRowNum;
	private EtlFixtureDefinition definition;
	private EtlFixtureDefinition loadSingleSpreadsheet(String path) throws IOException,	FileNotFoundException {
		definition = new EtlFixtureDefinition();
		definition.fixture = "com.vertafore.plm.fermenter.etl.fixtures.EtlTestFixture";
		
		Workbook wb = new XSSFWorkbook(new FileInputStream(path));
	    sheet = wb.getSheet("Tests");
	    
	    int rowStart = sheet.getFirstRowNum();
	    int rowEnd = sheet.getLastRowNum();

	    for (currentRowNum = rowStart; currentRowNum < rowEnd; currentRowNum++) {
			Row row = sheet.getRow(currentRowNum);
			if (!blankRow(row) && "Section".equalsIgnoreCase(row.getCell(0).toString())) {
				if ("Config".equalsIgnoreCase(row.getCell(1).toString())) {
					loadConfigSection();
				} else if ("Cleanup".equalsIgnoreCase(row.getCell(1).toString())) {
					loadCleanupSection();
				} else if ("Insert".equalsIgnoreCase(row.getCell(1).toString())) {
					loadInsertSection();
				} else if ("Validate".equalsIgnoreCase(row.getCell(1).toString())) {
					loadValidateSection();
				} else if ("PostCleanup".equalsIgnoreCase(row.getCell(1).toString())) {
					loadPostCleanupSection();
				}
			}
		}
	    
	    return definition;
	}
	

	private void loadInsertSection() {
		definition.setDataInserts(loadDataDetailSection());
	}
	private void loadValidateSection() {
		definition.setValidations(loadDataDetailSection());
	}
	private ArrayList<DataDetail> loadDataDetailSection() {
		ArrayList<DataDetail> dataDetails = new ArrayList<DataDetail>();
		
		while (currentRowNum <= sheet.getLastRowNum()) {
			Row row = sheet.getRow(++currentRowNum);
			
			if(blankRow(row) || !"Datasource".equalsIgnoreCase(row.getCell(0).toString())) {
				currentRowNum--;//move on to another section
				break;
			}
			
			DataDetail insert = new DataDetail();
			
			insert.setDataSource(row.getCell(1).toString());
			row = sheet.getRow(++currentRowNum);
			insert.setTableName(row.getCell(1).toString());
			if("WhereClause".equalsIgnoreCase(sheet.getRow(currentRowNum+1).getCell(0).toString())) {
				insert.setWhereClause(sheet.getRow(++currentRowNum).getCell(1).toString());
			}
			if("OrderBy".equalsIgnoreCase(sheet.getRow(currentRowNum+1).getCell(0).toString())) {
				insert.setOrderBy(sheet.getRow(++currentRowNum).getCell(1).toString());
			}

			row = sheet.getRow(++currentRowNum);
			insert.setFields(populateFromRow(row));
			
			ArrayList<String[]> dataRows = new ArrayList<String[]>();

		    while (currentRowNum <= sheet.getLastRowNum()) {

		    	row = sheet.getRow(++currentRowNum);
		    	
				if(blankRow(row)) {
					break;
				} else {
					dataRows.add(populateFromRow(row));
				}
			}
		    
		    insert.setDataRows(dataRows);
		    dataDetails.add(insert);
		}
		
		return dataDetails;
	}

	private void loadPostCleanupSection() {
		definition.setPostCleanup(loadEitherCleanupSection());
	}

	private void loadCleanupSection() {
		definition.setCleanup(loadEitherCleanupSection());
	}
	
	private ArrayList<String[]> loadEitherCleanupSection() {
		ArrayList<String[]> cleanup = new ArrayList<String[]>();
		
		Row row = sheet.getRow(++currentRowNum);

	    while (currentRowNum <= sheet.getLastRowNum()) {

	    	row = sheet.getRow(++currentRowNum);
			if(blankRow(row)) {
				break;
			} else {
				cleanup.add(new String[]{row.getCell(0).toString(), row.getCell(1).toString()});
			}
			
		}
	    
	    return cleanup;
	}

	private void loadConfigSection() {
		Row row = sheet.getRow(++currentRowNum);
		definition.setJobLocation(row.getCell(1).toString());
		row = sheet.getRow(++currentRowNum);
		definition.setJobName(row.getCell(1).toString());
		row = sheet.getRow(++currentRowNum);
		definition.setJobParams(row.getCell(1).toString());
	}
	
	
	
	private boolean blankRow(Row row) {
		return row == null || row.getCell(0) == null || row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK || "".equals(row.getCell(0).toString().trim());
	}
	
	private String[] populateFromRow(Row row) {
		String[] values = new String[row.getPhysicalNumberOfCells()];

		Iterator cells = row.iterator();
		int i=0;
        String cellValue = "";
        Cell currentCell;
        int cellType;
		while(cells.hasNext()) {
            currentCell = (Cell)cells.next();
            try{
                cellType = currentCell.getCellType();
                switch(cellType) {
                    case Cell.CELL_TYPE_NUMERIC:
                        cellValue = getValueFromNumericCell(currentCell);
                        break;
                    case Cell.CELL_TYPE_STRING:
                    case Cell.CELL_TYPE_BLANK:
                        cellValue =currentCell.getStringCellValue();
                        break;
                    default:
                        throw new Exception("Unexpected cell type");
                }
			    values[i++] = cellValue;
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
		}
		
		return values;
	}

    private String getValueFromNumericCell(Cell cell) {
        String value;
        short dataFormat = cell.getCellStyle().getDataFormat();
        switch (dataFormat){
            case 15:
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                value = formatter.format(cell.getDateCellValue());
                break;
            default:
                value = new HSSFDataFormatter().formatCellValue(cell);
        }

        return value;
    }
}
