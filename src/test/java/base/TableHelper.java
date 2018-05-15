package test.java.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TableHelper {
	
	private Map<String, Integer> headerMap;
	private XSSFSheet dataTable;
	private Map<String, String> randomValueMap;
	
	public TableHelper() {
		loadDataTable();
		createMap(dataTable);
		randomValueMap = new HashMap<>();
	}
	
	private void loadDataTable() {
		loadDataTable(0);
	}
	
	private void loadDataTable(int sheetNr) {
		String filepath = getClass().getResource("/data_table.xlsx").getPath();
		XSSFWorkbook dataTableFile = null;
		try {
			dataTableFile = new XSSFWorkbook(new File(filepath));
			dataTable = dataTableFile.getSheetAt(sheetNr);
		}
		catch (InvalidFormatException | IOException exc) {
			// TODO: handle exceptions
		}
		finally {
			if (dataTableFile != null) {
				try { dataTableFile.close(); } catch (IOException e) { }
			}
		}
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
				String rowHeader = currentRow.getCell(0).getStringCellValue();
				if (rowHeader != "") {
					headerMap.put(rowHeader, i);
				}
			}
		}
		
	}
	
	public String getRandomRowEntry(String category) {
		Integer index = headerMap.get(category);
		if (index == null) {
			throw new NoSuchElementException("Invalid header name: " + category);
		}
		Row row = dataTable.getRow(index);
		int cellCount = row.getLastCellNum();
		String result = null;
		int attempts = 0;
		Random gen = new Random();
		while ((result == null || result.equals("")) && attempts < 15) {
			// get a random row index (add 1 as cell 0 is the category)
			int i = gen.nextInt(cellCount - 1) + 1;
			result = row.getCell(i).getStringCellValue();
			attempts++;
		}
		if (!randomValueMap.containsKey(category)) {
			randomValueMap.put(category, result);
		}
		return result;
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
		}
		catch (IOException exc) {}
	}
}
