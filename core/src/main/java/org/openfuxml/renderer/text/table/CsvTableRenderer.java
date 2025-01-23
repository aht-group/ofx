package org.openfuxml.renderer.text.table;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.ObjectUtils;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvTableRenderer extends TextTableRenderer implements OfxTextRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(CsvTableRenderer.class);
		
	public CsvTableRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Table table, Path path) throws OfxAuthoringException
	{
		super.prepareCells(table);

		 try (Writer writer = new FileWriter(path.toFile()); CSVPrinter csv = new CSVPrinter(writer,CSVFormat.EXCEL))
		 {
			 List<String> header = new ArrayList<>();
			 for(OfxTextRenderer r : rendererHeader)
			 {
				 header.add(r.getSingleLine());
			 }
			 csv.printRecord(header);
			 
			 if(ObjectUtils.isNotEmpty(table.getContent().getBody()))
			 {
				 for(Row row : table.getContent().getBody().get(0).getRow())
				 {
					 List<String> csvRow = new ArrayList<>();
					 for(Cell cell : row.getCell())
					 {
						TextCellRenderer r = new TextCellRenderer(cp);
						r.render(cell);
						csvRow.add(r.getSingleLine());
						
					 }
					 csv.printRecord(csvRow);
				 }
			 }
			
			 csv.flush();
			 logger.info("Data written successfully to " + path);

		 }
		 catch (IOException e) {e.printStackTrace();}
	}
}