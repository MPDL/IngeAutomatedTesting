package test.java.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.log4testng.Logger;

public class TableHelper {

	// TODO: The whole data driven tests (Accessing the data/datatabel and comparing
	// the results in the tests) have to be refactored!!!

	private static final Logger log4j = Logger.getLogger(TableHelper.class);

	private Map<String, Integer> headerMap;
	private Map<String, String> randomValueMap;
	private XSSFSheet dataTable;

	public TableHelper() {
		createMap(loadDataTable());
		randomValueMap = new HashMap<>();
	}

	private XSSFSheet loadDataTable() {
		XSSFSheet data = null;
		String filepath = getClass().getResource("/data_table.xlsx").getPath();
		XSSFWorkbook dataTableFile = null;
		try {
			dataTableFile = new XSSFWorkbook(new File(filepath));
			data = dataTableFile.getSheetAt(0); // getting sheet site 1
		} catch (InvalidFormatException | IOException e) {
			log4j.error("Error converting the data.", e);
		} finally {
			if (dataTableFile != null) {
				try {
					dataTableFile.close();
				} catch (IOException e) {
				}
			}
		}
		this.dataTable = data;
		return data;

	}

	/**
	 * Maps each header to its table row for easier access
	 */
	private void createMap(XSSFSheet dataTable) {
		headerMap = new HashMap<>();

		if (dataTable != null) {
			int rowCount = dataTable.getLastRowNum();
			for (int i = 0; i < rowCount; i++) {
				Row currentRow = dataTable.getRow(i);
				String rowHeader = this.getCellValue(currentRow.getCell(0));
				if (rowHeader != "") {
					headerMap.put(rowHeader, i);
				}
			}
		}

	}

	// TODO: Refactor this method
	public String getRandomRowEntry(String category) {
		Integer index = headerMap.get(category);
		if (index == null) {
			throw new NoSuchElementException("Invalid header name: " + category);
		}
		Row row = dataTable.getRow(index);
		int cellCount = row.getLastCellNum();
		String result = null;
		int attempts = 0;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		while ((result == null || ("").equals(result) || ("").equals(result.trim())) && attempts < 50) {
			int i = random.nextInt(1, cellCount);
			result = this.getCellValue(row.getCell(i));
			attempts++;
		}
		log4j.debug("Category: " + category + " - entry: " + result);
		if (!randomValueMap.containsKey(category)) {
			randomValueMap.put(category, result);
		}
		return result;
	}

	private String getCellValue(Cell cell) {
		String cellValue;

		if (cell != null) {
			CellType cellType = cell.getCellTypeEnum();
			String cellAdress = cell.getAddress().formatAsString();

			switch (cellType) {
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case BLANK:
				cellValue = "";
				break;
			default:
				throw new IllegalArgumentException(
						"The cell-type '" + cellType + "' of the cell (" + cellAdress + ") is not supported.");
			}
		} else {
			log4j.error("No cell to get the value from.");
			return null;
		}

		return cellValue;
	}

	public void setRowEntry(String key, String value) {
		randomValueMap.put(key, value);
	}

	public Map<String, String> getMap() {
		return Collections.unmodifiableMap(randomValueMap);
	}

	public void writeContentsToFile(String filepath) {
		File testOutput = new File(filepath);
		try (PrintWriter pw = new PrintWriter(testOutput, "UTF-8")) {
			if (randomValueMap != null) {
				for (String label : randomValueMap.keySet()) {
					pw.write(label + ": " + randomValueMap.get(label) + "\n");
				}
			}
		} catch (IOException exc) {
		}
	}
}
