package test.java.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.log4testng.Logger;

public class TableHelper {
	
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
		}
		catch (InvalidFormatException | IOException e) {
		  e.printStackTrace();
		}
		finally {
			if (dataTableFile != null) {
				try { dataTableFile.close(); } catch (IOException e) { }
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
		ThreadLocalRandom random = ThreadLocalRandom.current();
		while ((result == null || ("").equals(result) || ("").equals(result.trim())) && attempts < 50) {
			int i = random.nextInt(1, cellCount);
			result = row.getCell(i).getStringCellValue();
			attempts++;
		}
		log4j.debug("Category: " + category + " - entry: " + result);
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
